package com.example.syncarquitetura;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

class LeitorDeArquivoDeDecisaoTest {

	private final Path arquivo = Paths.get("src/test/resources/decisao.txt");
	private final LeitorDeArquivoDeDecisao leitor = new LeitorDeArquivoDeDecisao(arquivo);
	
	@Test
	void informarQueAplicacaoDeveRetornarDecisaoDescritaNoArquivo() {
		Arrays.stream(Decisao.values()).forEach(decisaoEsperada ->{
			escreverDecisao(decisaoEsperada);
			Decisao decisaoObtida = leitor.obterDecisaoTomada();
			assertThat(decisaoObtida).isEqualTo(decisaoEsperada);
		});
	}
		
	private void escreverDecisao(Decisao decisao){
		try {
			Files
			.write(arquivo, decisao.toString()
					.getBytes(StandardCharsets.UTF_8));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
