package com.example.syncarquitetura;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

import static java.util.Arrays.stream;
import static java.util.stream.Collectors.joining;

@Configuration
public class KafkaObjects {

	@Value("${spring.kafka.bootstrap-servers}")
	private String kafkaServerUrl;
	
	@Value("${spring.kafka.group_id}")
	private String groupId;
	
	@Value("${spring.kafka.auto-offset-reset}")
	private String autoOffsetReset;

	@Value("${topico.comandas.nome}")
	private String nomeDoTopico;
	
	/*******************************************
	 * Produtores 
	 *******************************************/

	@Bean
	KafkaTemplate<String, ComandaAprovadaEvent> comandaKafkaTemplate() {
		return new KafkaTemplate<>(producerFactory());
	}

	// convertido para bean pois o zipkin precisa dos beans de produtor e consumidor do kafka,
	// para poder fazer o distributed tracing
	@Bean
	DefaultKafkaProducerFactory<String, ComandaAprovadaEvent> producerFactory() {
		return new DefaultKafkaProducerFactory<>(produtorConfigs());
	}
	
	public Map<String, Object> produtorConfigs() {
		Map<String, Object> props = new HashMap<>();
		props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaServerUrl);
		props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
		return props;
	}

	/*******************************************
	 * Consumidores 
	 *******************************************/
	
	@Bean
	KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, PedidoSolicitadoEvent>> pedidoSolicitadoKafkaListener() {
		ConcurrentKafkaListenerContainerFactory<String, PedidoSolicitadoEvent> factory =
				new ConcurrentKafkaListenerContainerFactory<>();
		factory.setConsumerFactory(pedidoSolicitadoConsumerFactory());
		return factory;
	}

	@Bean
	KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, VendaAprovadaEvent>> vendaAprovadaKafkaListener() {
		ConcurrentKafkaListenerContainerFactory<String, VendaAprovadaEvent> factory =
				new ConcurrentKafkaListenerContainerFactory<>();
		factory.setConsumerFactory(vendaAprovadaConsumerFactory());
		return factory;
	}
	
	@Bean
	KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, VendaReprovadaEvent>> vendaReprovadaKafkaListener() {
		ConcurrentKafkaListenerContainerFactory<String, VendaReprovadaEvent> factory =
				new ConcurrentKafkaListenerContainerFactory<>();
		factory.setConsumerFactory(vendaReprovadaConsumerFactory());
		return factory;
	}
	
	@Bean
	KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, Event>> vendaKafkaListener() {
		ConcurrentKafkaListenerContainerFactory<String, Event> factory =
				new ConcurrentKafkaListenerContainerFactory<>();
		factory.setConsumerFactory(vendaConsumerFactory());
		return factory;
	}
	
	// convertido para bean pois o zipkin precisa dos beans de produtor e consumidor do kafka,
	// para poder fazer o distributed tracing
	@Bean
	ConsumerFactory<String, Event> vendaConsumerFactory() {
		JsonDeserializer<Event> deserializer = new JsonDeserializer<>(Event.class, false);
		return new DefaultKafkaConsumerFactory<>(
				consumerConfigs(VendaAprovadaEvent.class, VendaReprovadaEvent.class),
				new StringDeserializer(),
				deserializer);
	}
	
	// convertido para bean pois o zipkin precisa dos beans de produtor e consumidor do kafka,
	// para poder fazer o distributed tracing
	@Bean
	ConsumerFactory<String, PedidoSolicitadoEvent> pedidoSolicitadoConsumerFactory() {
		return new DefaultKafkaConsumerFactory<>(
				consumerConfigs(PedidoSolicitadoEvent.class),
				new StringDeserializer(),
				jsonDeserializer(PedidoSolicitadoEvent.class));
	}
	
	// convertido para bean pois o zipkin precisa dos beans de produtor e consumidor do kafka,
	// para poder fazer o distributed tracing
	@Bean
	ConsumerFactory<String, VendaAprovadaEvent> vendaAprovadaConsumerFactory() {
		return new DefaultKafkaConsumerFactory<>(
				consumerConfigs(VendaAprovadaEvent.class),
				new StringDeserializer(),
				jsonDeserializer(VendaAprovadaEvent.class));
	}
	
	// convertido para bean pois o zipkin precisa dos beans de produtor e consumidor do kafka,
	// para poder fazer o distributed tracing
	@Bean
	ConsumerFactory<String, VendaReprovadaEvent> vendaReprovadaConsumerFactory() {
		return new DefaultKafkaConsumerFactory<>(
				consumerConfigs(VendaReprovadaEvent.class),
				new StringDeserializer(),
				jsonDeserializer(VendaReprovadaEvent.class));
	}
	
	private <T> JsonDeserializer<T> jsonDeserializer(Class<T> clazz){
		return new JsonDeserializer<T>(clazz, false);
	}
	
	public Map<String, Object> consumerConfigs(Class<?> ... clazz) {
		Map<String, Object> props = new HashMap<>();
		props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
		props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, autoOffsetReset);
		props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaServerUrl);

		props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
		props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class.getName());
		props.put(JsonDeserializer.TYPE_MAPPINGS, typeMappings(clazz));

		return props;
	}

	private String typeMappings(Class<?> ...classes) {
		return stream(classes)
				.map(clazz -> clazz.getSimpleName().toLowerCase() + ":" + clazz.getName()
				).collect(joining(","));
	}
}
