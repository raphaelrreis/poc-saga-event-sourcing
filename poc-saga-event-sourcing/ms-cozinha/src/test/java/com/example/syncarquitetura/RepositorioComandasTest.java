package com.example.syncarquitetura;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.UUID;

import static java.util.UUID.randomUUID;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
//@Transactional
//@SpringBootTest("spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration")
class RepositorioComandasTest {
	
	@Autowired
	private RepositorioComandas repositorio;

	private Comanda comandaEsperada;
	private UUID correlationId;

	@BeforeEach
	void setUp(){
		correlationId = randomUUID();
		comandaEsperada = new Comanda(correlationId);
		repositorio.save(comandaEsperada);
	}

	@Test
	void persistirUmaComanda() {
		Comanda comandaObtida = repositorio.findById(comandaEsperada.getId()).get();
		
		assertThat(comandaObtida).isEqualTo(comandaEsperada);
	}

	@Test
	void buscarComandaPorCorrelationId(){
		Comanda comandaObtida = repositorio.findByCorrelationId(correlationId);

		assertThat(comandaObtida).isEqualTo(comandaEsperada);
	}
}
