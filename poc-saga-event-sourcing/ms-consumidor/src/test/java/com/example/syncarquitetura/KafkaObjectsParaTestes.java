package com.example.syncarquitetura;

import static java.util.Collections.singletonList;

import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;

@Configuration
public class KafkaObjectsParaTestes {
	
	@Value("${spring.kafka.bootstrap-servers}")
	private String kafkaServerUrl;
	
	@Value("${spring.kafka.group_id}")
	private String groupId;
	
	@Value("${spring.kafka.auto-offset-reset}")
	private String autoOffsetReset;

	@Value("${topico.consumidores.nome}")
	private String nomeDoTopico;

	@Autowired
	private KafkaObjects kafkaObjects;

	@Bean
	KafkaTemplate<String, PedidoSolicitadoEvent> pedidoKafkaTemplate() {
		return new KafkaTemplate<>(new DefaultKafkaProducerFactory<>(kafkaObjects.produtorConfigs()));
	}
	
	@Bean
	KafkaConsumer<String, ConsumidorVerificadoEvent> consumidorVerificadoKafkaConsumer(){
		KafkaConsumer<String, ConsumidorVerificadoEvent> consumidor = new KafkaConsumer<>(kafkaObjects.consumerConfigs());
		consumidor.subscribe(singletonList(nomeDoTopico));
		return consumidor;
	}
}
