package com.example.syncarquitetura;

import static org.awaitility.Awaitility.await;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.awaitility.core.ThrowingRunnable;
import org.springframework.beans.factory.annotation.Autowired;

abstract class GerenciadorDaSagaAbsTest {
	
    @Autowired
    protected GerenciadorDaSaga saga;
	
	protected List<VendaEvent> mensagensDoTopico(KafkaConsumer<String, ? extends VendaEvent> consumidor){
        List<VendaEvent> eventos = new ArrayList<>();

        consumidor.poll(Duration.ofMillis(1000)).forEach(consumerRecord ->
            eventos.add(consumerRecord.value())
        );

        return eventos;
    }

    protected void esperarUmTempoAte(ThrowingRunnable runnable){
        await()
                .atMost(Duration.ofSeconds(10))
                .untilAsserted(runnable);
    }
}
