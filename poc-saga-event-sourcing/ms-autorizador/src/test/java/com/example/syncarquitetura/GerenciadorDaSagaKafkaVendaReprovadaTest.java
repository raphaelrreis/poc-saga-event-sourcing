package com.example.syncarquitetura;

import static com.example.syncarquitetura.ObjetosParaTestes.vendaReprovadaEvent;
import static org.assertj.core.api.Assertions.assertThat;

import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;

@DirtiesContext
@EmbeddedKafka
@SpringBootTest(properties = "spring.kafka.bootstrap-servers=${spring.embedded.kafka.brokers}")
//@SpringBootTest
class GerenciadorDaSagaKafkaVendaReprovadaTest extends GerenciadorDaSagaAbsTest {

    @Autowired
    private KafkaConsumer<String, VendaReprovadaEvent> consumidorVendasReprovadas;
	
    @Test
    void enviarVendaReprovadaAoKafka() {
    	VendaReprovadaEvent evento = vendaReprovadaEvent();
    	
    	saga.vendaReprovada(evento);
        
    	esperarUmTempoAte(() -> assertThat(mensagensDoTopico(consumidorVendasReprovadas)).containsOnlyOnce(evento));	
    }
}
