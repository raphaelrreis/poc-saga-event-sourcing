package com.example.syncarquitetura;

import static java.util.Arrays.stream;
import static java.util.stream.Collectors.joining;

import java.util.HashMap;
import java.util.Map;

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
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

@Configuration
class KafkaObjects {

	@Value("${spring.kafka.bootstrap-servers}")
	private String kafkaServerUrl;

    @Value("${spring.kafka.group_id}")
    private String groupId;

    @Value("${spring.kafka.auto-offset-reset}")
    private String autoOffsetReset;
	
	/*******************************************
	 * Produtores
	 *******************************************/

	@Bean
	KafkaTemplate<String, PedidoSolicitadoEvent> kafkaTemplate() {
		return new KafkaTemplate<>(producerFactory());
	}
	
	Map<String, Object> produtorConfigs() {
		Map<String, Object> props = new HashMap<>();
		props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaServerUrl);
		props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);

		return props;
	}

	// convertido para bean pois o zipkin precisa dos beans de produtor e consumidor do kafka,
	// para poder fazer o distributed tracing
	@Bean
	ProducerFactory<String, PedidoSolicitadoEvent> producerFactory(){
		return new DefaultKafkaProducerFactory<>(produtorConfigs());
	}
	
	/*******************************************
	 * Consumidores
	 *******************************************/
	
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
	ConsumerFactory<String, VendaReprovadaEvent> vendaReprovadaConsumerFactory(){
		return new DefaultKafkaConsumerFactory<>(
				consumerConfigs(VendaReprovadaEvent.class),
				new StringDeserializer(),
				jsonDeserializer(VendaReprovadaEvent.class));	
	}
	
	private <T> JsonDeserializer<T> jsonDeserializer(Class<T> clazz){
		return new JsonDeserializer<T>(clazz, false);
	}
	
    public Map<String, Object> consumerConfigs(Class<?> clazz) {
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
                .map(clazz -> clazz.getSimpleName().toLowerCase() + ":" + clazz.getName())
                .collect(joining(","));
    }
}
