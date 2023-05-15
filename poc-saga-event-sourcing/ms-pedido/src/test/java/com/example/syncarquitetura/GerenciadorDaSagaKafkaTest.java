package com.example.syncarquitetura;

import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.awaitility.core.ThrowingRunnable;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import static com.example.syncarquitetura.ObjetosParaTestes.pedidoSolicitadoEvent;
import static java.util.UUID.randomUUID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;

@DirtiesContext
@EmbeddedKafka
@SpringBootTest(properties = "spring.kafka.bootstrap-servers=${spring.embedded.kafka.brokers}")
// comente as linhas acima e descomente as abaixo caso queira realizar um teste integrado
//@SpringBootTest
class GerenciadorDaSagaKafkaTest {

    @Autowired
    @Qualifier("gerenciadorDaSagaKafka")
    private GerenciadorDaSaga saga;
    
    @Autowired
    private KafkaConsumer<String, PedidoSolicitadoEvent> consumidor;

    @Value("${topico.pedidos.nome}")
    private String nomeDoTopico;

    @Value("${spring.kafka.bootstrap-servers}")
    private String urlKafkaServer;

    @Value("${spring.kafka.group_id}")
    private String groupId;

    @Test
    void enviarPedidoSolicitadoAoKafka() {
        PedidoSolicitadoEvent evento = pedidoSolicitadoEvent(randomUUID());

        saga.criarPedido(evento);

        esperarUmTempoAte(() -> 
        	assertThat(
        			mensagensDoTopico(consumidor))
        				.containsEntry(evento.getCorrelationId().toString(), evento));
    }

    private Map<String, PedidoSolicitadoEvent> mensagensDoTopico(KafkaConsumer<String, PedidoSolicitadoEvent> consumidor){
        Map<String, PedidoSolicitadoEvent> eventos = new HashMap<>();

        consumidor.poll(Duration.ofMillis(20000)).forEach(consumerRecord ->
            eventos.put(consumerRecord.key(), consumerRecord.value())
        );

        return eventos;
    }

    private void esperarUmTempoAte(ThrowingRunnable runnable){
        await()
                .atMost(Duration.ofSeconds(10))
                .untilAsserted(runnable);
    }
}