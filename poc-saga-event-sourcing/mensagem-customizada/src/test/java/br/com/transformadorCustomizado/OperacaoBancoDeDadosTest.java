package br.com.transformadorCustomizado;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OperacaoBancoDeDadosTest {

	@Test
	void operacaoEhDeInsercao() {
		OperacoesBancoDeDados operacaoBD = OperacoesMongoDB.de("c");
		assertThat(operacaoBD.insercao()).isTrue();
	}

	@Test
	void operacaoEhOutra(){
		OperacoesBancoDeDados operacaoBD = OperacoesMongoDB.de("outras");
		assertThat(operacaoBD.insercao()).isFalse();
	}
}
