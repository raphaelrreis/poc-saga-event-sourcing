package com.example.syncarquitetura;

import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;

import static java.util.Collections.singletonList;

@Configuration
public class KafkaObjectsParaTestes {

    @Autowired
    private KafkaObjects kafkaObjects;

    @Value("${topico.comandas.nome}")
    private String nomeDoTopico;

    @Bean
    KafkaTemplate<String, PedidoSolicitadoEvent> pedidoKafkaTemplate() {
        return new KafkaTemplate<>(new DefaultKafkaProducerFactory<>(kafkaObjects.produtorConfigs()));
    }

    @Bean
    KafkaTemplate<String, VendaAprovadaEvent> vendaAprovadaKafkaTemplate() {
        return new KafkaTemplate<>(new DefaultKafkaProducerFactory<>(kafkaObjects.produtorConfigs()));
    }
    
    @Bean
    KafkaTemplate<String, VendaReprovadaEvent> vendaReprovadaKafkaTemplate() {
        return new KafkaTemplate<>(new DefaultKafkaProducerFactory<>(kafkaObjects.produtorConfigs()));
    }

    @Bean
    KafkaConsumer<String, ComandaAprovadaEvent> comandaAprovadaKafkaConsumer(){
        KafkaConsumer<String, ComandaAprovadaEvent> consumidor = new KafkaConsumer<>(kafkaObjects.consumerConfigs(ComandaAprovadaEvent.class));
        consumidor.subscribe(singletonList(nomeDoTopico));
        return consumidor;
    }
}
