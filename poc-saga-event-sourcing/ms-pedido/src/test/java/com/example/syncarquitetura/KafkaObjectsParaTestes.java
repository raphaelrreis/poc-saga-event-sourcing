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

    @Value("${topico.pedidos.nome}")
    private String nomeDoTopico;
    
    @Autowired
    private KafkaObjects kafkaObjects;

	@Bean
	KafkaTemplate<String, VendaAprovadaEvent> aprovadoKafkaTemplate() {
		return new KafkaTemplate<>(new DefaultKafkaProducerFactory<>(kafkaObjects.produtorConfigs()));
	}
	
	@Bean
	KafkaTemplate<String, VendaReprovadaEvent> reprovadoKafkaTemplate() {
		return new KafkaTemplate<>(new DefaultKafkaProducerFactory<>(kafkaObjects.produtorConfigs()));
	}
    
    @Bean
    KafkaConsumer<String, PedidoSolicitadoEvent> pedidoSolicitadoKafkaConsumer(){
        KafkaConsumer<String, PedidoSolicitadoEvent> consumidor = 
        		new KafkaConsumer<>(kafkaObjects.consumerConfigs(PedidoSolicitadoEvent.class));
        consumidor.subscribe(singletonList(nomeDoTopico));
        return consumidor;
    }
}
