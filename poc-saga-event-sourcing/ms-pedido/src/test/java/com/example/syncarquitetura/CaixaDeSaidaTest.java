package com.example.syncarquitetura;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import java.util.Optional;
import java.util.Properties;

import static java.math.BigDecimal.TEN;
import static java.util.UUID.randomUUID;
import static org.assertj.core.api.Assertions.assertThat;

@DataMongoTest
//@Transactional
//@SpringBootTest("spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration")
class CaixaDeSaidaTest {

	@Autowired
	private CaixaDeSaida caixaDeSaida;
	
	@Test
	void persistir() {
		CaixaDeSaidaEvent evento = new CaixaDeSaidaEvent(randomUUID(), TEN, new Properties());
		
		caixaDeSaida.save(evento);
		
		Optional<CaixaDeSaidaEvent> eventoPersistido = caixaDeSaida.findById(evento.getId());
		
		assertThat(eventoPersistido).isNotEmpty();
		assertThat(eventoPersistido.get()).isEqualTo(evento);
	}
}
