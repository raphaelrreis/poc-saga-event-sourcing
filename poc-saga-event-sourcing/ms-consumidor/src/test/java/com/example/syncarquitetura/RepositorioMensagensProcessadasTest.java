package com.example.syncarquitetura;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

@DataMongoTest
//@ImportAutoConfiguration(exclude = EmbeddedMongoAutoConfiguration.class) // para rodar numa base de dados real
class RepositorioMensagensProcessadasTest {

	private MensagemProcessada mensagemProcessada;
	private UUID idDaMensagem;
	
	@Autowired
	private RepositorioMensagensProcessadas repositorio;
	
	@BeforeEach
	void setUp() {
		idDaMensagem = UUID.randomUUID();
		mensagemProcessada = new MensagemProcessada(idDaMensagem.toString());
		repositorio.save(mensagemProcessada);
	}
	
	@Test
	void persistir() {
		assertThat(repositorio.existsById(idDaMensagem.toString())).isTrue();
	}
}
