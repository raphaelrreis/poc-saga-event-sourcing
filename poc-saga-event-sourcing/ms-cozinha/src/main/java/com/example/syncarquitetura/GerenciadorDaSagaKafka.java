package com.example.syncarquitetura;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.Headers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
class GerenciadorDaSagaKafka implements GerenciadorDaSaga {

    @Autowired
    private KafkaTemplate<String, ComandaAprovadaEvent> kafkaTemplate;

    @Value("${topico.comandas.nome}")
    private String nomeDoTopico;

    @Override
    public void comandaAprovada(ComandaAprovadaEvent event) {
        ProducerRecord<String, ComandaAprovadaEvent> producerRecord = 
        		new ProducerRecord<String, ComandaAprovadaEvent>(nomeDoTopico, event.getCorrelationId().toString(), event);
        Headers headers = event.getHeaders();
        headers.forEach(header -> producerRecord.headers().add(header));
        
        kafkaTemplate.send(producerRecord);
    }
}
