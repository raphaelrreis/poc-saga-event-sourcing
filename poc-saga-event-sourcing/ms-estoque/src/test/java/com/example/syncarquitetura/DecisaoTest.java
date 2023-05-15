package com.example.syncarquitetura;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpServerErrorException;

class DecisaoTest {
	
	@Test
	void executarErroGeral() {
		assertThatThrownBy(() -> {
			Decisao.ERRO_GERAL.executar();
		}).isInstanceOf(HttpServerErrorException.class);
	}

	@Test
	void executarSucesso(){
		ResponseEntity executar = Decisao.SUCESSO.executar();
		assertThat(executar).isEqualTo(ResponseEntity.ok().build());
	}

	@Test
	void executarSucessoComTimeout(){
		ResponseEntity executar = Decisao.SUCESSO_TIMEOUT.executar();
		assertThat(executar).isEqualTo(ResponseEntity.ok().build());
	}
}
