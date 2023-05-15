package com.example.syncarquitetura;

import static java.util.Collections.singletonList;

import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;

import com.example.syncarquitetura.teste.PedidoSolicitadoEvent;

@Configuration
public class KafkaObjectsParaTestes {

    @Value("${topico.validacoes.nome}")
    private String topicoVendasValidadas;

    @Value("${topico.vendas.nome}")
    private String topicoVendasAprovadas;
    
    @Value("${topico.vendas_reprovadas.nome}")
    private String topicoVendasReprovadas;

    @Autowired
    private KafkaObjects kafkaObjects;

    @Bean
    KafkaTemplate<String, PedidoSolicitadoEvent> pedidoKafkaTemplate() {
        return new KafkaTemplate<>(new DefaultKafkaProducerFactory<>(kafkaObjects.produtorConfigs()));
    }

    @Bean
    KafkaTemplate<String, ComandaAprovadaEvent> comandaKafkaTemplate() {
        return new KafkaTemplate<>(new DefaultKafkaProducerFactory<>(kafkaObjects.produtorConfigs()));
    }

    @Bean
    KafkaTemplate<String, ConsumidorVerificadoEvent> consumidorKafkaTemplate() {
        return new KafkaTemplate<>(new DefaultKafkaProducerFactory<>(kafkaObjects.produtorConfigs()));
    }
    
    @Bean
    KafkaTemplate<String, VendaValidadaEvent> vendaValidadaKafkaTemplate() {
        return new KafkaTemplate<>(new DefaultKafkaProducerFactory<>(kafkaObjects.produtorConfigs()));
    }

    @Bean
    KafkaConsumer<String, VendaValidadaEvent> vendaValidadaKafkaConsumer(){
        KafkaConsumer<String, VendaValidadaEvent> consumidor =
                new KafkaConsumer<>(kafkaObjects.consumerConfigs(VendaValidadaEvent.class));
        consumidor.subscribe(singletonList(topicoVendasValidadas));
        return consumidor;
    }

    @Bean
    KafkaConsumer<String, VendaAprovadaEvent> vendaAprovadaKafkaConsumer(){
        KafkaConsumer<String, VendaAprovadaEvent> consumidor = 
        		new KafkaConsumer<>(kafkaObjects.consumerConfigs(VendaAprovadaEvent.class));
        consumidor.subscribe(singletonList(topicoVendasAprovadas));
        return consumidor;
    }
    
    @Bean
    KafkaConsumer<String, VendaReprovadaEvent> vendaReprovadaKafkaConsumer(){
        KafkaConsumer<String, VendaReprovadaEvent> consumidor = 
        		new KafkaConsumer<>(kafkaObjects.consumerConfigs(VendaReprovadaEvent.class));
        consumidor.subscribe(singletonList(topicoVendasReprovadas));
        return consumidor;
    }
}
