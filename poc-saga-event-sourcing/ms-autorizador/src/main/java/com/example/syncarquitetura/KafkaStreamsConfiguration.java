package com.example.syncarquitetura;

import static java.time.Duration.ofMinutes;
import static org.apache.kafka.common.serialization.Serdes.String;
import static org.apache.kafka.common.serialization.Serdes.serdeFrom;
import static org.apache.kafka.streams.StreamsConfig.APPLICATION_ID_CONFIG;
import static org.apache.kafka.streams.StreamsConfig.BOOTSTRAP_SERVERS_CONFIG;
import static org.apache.kafka.streams.kstream.Consumed.with;

import java.util.Properties;

import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.JoinWindows;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Produced;
import org.apache.kafka.streams.kstream.StreamJoined;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.support.serializer.JsonSerializer;

@Configuration
public class KafkaStreamsConfiguration {

	@Value("${spring.kafka.bootstrap-servers}")
	private String kafkaServerUrl;

	@Value("${topico.comandas.nome}")
	private String topicoDeComandas;

	@Value("${topico.consumidores.nome}")
	private String topicoDeConsumidores;

	@Value("${topico.validacoes.nome}")
	private String topicoDeVendasValidadas;

	@Value("${application.id.config}")
	private String idDaAplicacao;

	@Autowired
	private KafkaObjects kafkaObjects;

	@Bean
	KafkaStreams kafkaStreams(){
		KafkaStreams streams = new KafkaStreams(getStream().build(), getStreamsConfiguration());

		streams.start();

		// Add shutdown hook to respond to SIGTERM and gracefully close the Streams application.
		Runtime.getRuntime().addShutdownHook(new Thread(streams::close));

		return streams;
	}

	private StreamsBuilder getStream() {
		StreamsBuilder builder = new StreamsBuilder();

		Serde<ComandaAprovadaEvent> serdeComandaAprovadaEvent =
				serdeFrom(new JsonSerializer<>(), kafkaObjects.jsonDeserializer(ComandaAprovadaEvent.class));
		Serde<ConsumidorVerificadoEvent> serdeConsumidorVerificadoEvent =
				serdeFrom(new JsonSerializer<>(), kafkaObjects.jsonDeserializer(ConsumidorVerificadoEvent.class));
		Serde<VendaValidadaEvent> serdeVendaValidadaEvent =
				serdeFrom(new JsonSerializer<>(), kafkaObjects.jsonDeserializer(VendaValidadaEvent.class));

		KStream<String, ComandaAprovadaEvent> streamComandaAprovadaEvent = 
				builder.stream(topicoDeComandas, with(String(), serdeComandaAprovadaEvent));

		KStream<String, ConsumidorVerificadoEvent> streamConsumidorVerificadoEvent = 
				builder.stream(topicoDeConsumidores, with(String(), serdeConsumidorVerificadoEvent));

		streamComandaAprovadaEvent
				.join(streamConsumidorVerificadoEvent,
						(comandaAprovada, consumidorVerificado) -> new VendaValidadaEvent(comandaAprovada.getCorrelationId())
						, JoinWindows.of(ofMinutes(5))
						, StreamJoined.with(String()
								, serdeComandaAprovadaEvent
								, serdeConsumidorVerificadoEvent)
				)
				.to(topicoDeVendasValidadas,
						Produced.with(String(), serdeVendaValidadaEvent));

		return builder;
	}

	private Properties getStreamsConfiguration() {
		Properties streamsConfiguration = new Properties();
		streamsConfiguration.put(APPLICATION_ID_CONFIG, idDaAplicacao);
		streamsConfiguration.put(BOOTSTRAP_SERVERS_CONFIG, kafkaServerUrl);
		return streamsConfiguration;
	}
}
