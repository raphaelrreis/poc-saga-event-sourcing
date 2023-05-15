package com.example.syncarquitetura;

import static com.example.syncarquitetura.ObjetosParaTestes.consumidorVerificadoEvent;
import static java.util.UUID.randomUUID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.awaitility.core.ThrowingRunnable;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;

@DirtiesContext
@EmbeddedKafka
@SpringBootTest(properties = "spring.kafka.bootstrap-servers=${spring.embedded.kafka.brokers}")
//@SpringBootTest
class GerenciadorDaSagaKafkaTest {

    @Autowired
    private GerenciadorDaSaga saga;
    
    @Autowired
    private KafkaConsumer<String, ConsumidorVerificadoEvent> consumidor;

    @Value("${topico.consumidores.nome}")
    private String nomeDoTopico;

    @Value("${spring.kafka.bootstrap-servers}")
    private String urlKafkaServer;

    @Value("${spring.kafka.group_id}")
    private String groupId;

    @Test
    void enviarPedidoAoKafka() throws InterruptedException {
        ConsumidorVerificadoEvent evento = consumidorVerificadoEvent(randomUUID());

        saga.consumidorVerificado(evento);

        esperarUmTempoAte(() -> 
        	assertThat(mensagensDoTopico(consumidor))
        		.containsEntry(evento.getCorrelationId().toString(), evento));
    }

    private Map<String, ConsumidorVerificadoEvent> mensagensDoTopico(KafkaConsumer<String, ConsumidorVerificadoEvent> consumidor){
        Map<String, ConsumidorVerificadoEvent> eventos = new HashMap<>();

        consumidor.poll(Duration.ofMillis(1000)).forEach(consumerRecord ->
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