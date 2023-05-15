package com.example.syncarquitetura;

import static com.example.syncarquitetura.ObjetosParaTestes.comandaAprovadaEvent;
import static java.util.UUID.randomUUID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import java.util.UUID;

import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.header.Header;
import org.apache.kafka.common.header.Headers;
import org.apache.kafka.common.header.internals.RecordHeaders;
import org.awaitility.core.ThrowingRunnable;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;

import lombok.AllArgsConstructor;

@DirtiesContext
@EmbeddedKafka
@SpringBootTest(properties = {"spring.kafka.bootstrap-servers=${spring.embedded.kafka.brokers}"})
// comente as linhas acima e descomente as abaixo caso queira realizar um teste integrado
//@SpringBootTest
class GerenciadorDaSagaKafkaTest {

    @Autowired
    private GerenciadorDaSagaKafka saga;
    
    @Autowired
    private KafkaConsumer<String, ComandaAprovadaEvent> consumidor;

    @Value("${topico.comandas.nome}")
    private String nomeDoTopico;

    @Value("${spring.kafka.bootstrap-servers}")
    private String urlKafkaServer;

    @Value("${spring.kafka.group_id}")
    private String groupId;

    @Test
    void enviarComandaAprovadaAoKafka() throws InterruptedException {
    	Headers headers = EventoRecebido.headersEsperados();
    	UUID correlationId = randomUUID();
        ComandaAprovadaEvent evento = comandaAprovadaEvent(correlationId, headers);
        
        EventoRecebido eventoEsperado = new EventoRecebido(correlationId.toString(), evento, headers);

        saga.comandaAprovada(evento);
        
        esperarUmTempoAte(() -> 
    		assertThat(
    			mensagensDoTopico(consumidor))
    				.contains(eventoEsperado));
    }
    
    private Collection<EventoRecebido> mensagensDoTopico(KafkaConsumer<String, ComandaAprovadaEvent> consumidor){
        Collection<EventoRecebido> eventos = new ArrayList<GerenciadorDaSagaKafkaTest.EventoRecebido>();

      consumidor.poll(Duration.ofMillis(20000)).forEach(consumerRecord ->
      	eventos.add(new EventoRecebido(consumerRecord.key(), consumerRecord.value(), consumerRecord.headers()))
      );
      
      return eventos;
    }

    private void esperarUmTempoAte(ThrowingRunnable runnable){
        await()
                .atMost(Duration.ofSeconds(10))
                .untilAsserted(runnable);
    }
 
    @AllArgsConstructor
    private static class EventoRecebido {
    	
		private String chave;
    	private ComandaAprovadaEvent valor;
    	private Headers headers;
    	
    	public static final Headers headersEsperados() {
        	Headers headers = new RecordHeaders();
        	headers.add("chave", "valor".getBytes());
        	headers.add("outraChave", "outroValor".getBytes());
        	return headers;
    	}
    	
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			EventoRecebido other = (EventoRecebido) obj;
			return Objects.equals(chave, other.chave)
					&& Objects.equals(valor, other.valor)
					&& eventoContemHeadersEsperados(headers);
		}
		
		private boolean eventoContemHeadersEsperados(Headers headersObtidos) {
			for(Header headerEsperado : headersEsperados()) {
				Header headerParaAnalisar = headersObtidos.lastHeader(headerEsperado.key());
				if(!new String(headerParaAnalisar.value()).equals(new String(headerEsperado.value()))) {
					return false;
				}
			}
			return true;
		}
    }
}