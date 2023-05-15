package com.example.syncarquitetura;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
class LeitorDeArquivoDeDecisao {

	private final Path arquivoDeDecisao;

	@Autowired
	LeitorDeArquivoDeDecisao() throws URISyntaxException {
		this(Path.of(ClassLoader.getSystemResource("decisao.txt").toURI()));
	}

	LeitorDeArquivoDeDecisao(Path arquivo) {
		this.arquivoDeDecisao = arquivo;
	}

	public Decisao obterDecisaoTomada() {
		String nomeDecisao = null;
		try {
			nomeDecisao = Files.readString(arquivoDeDecisao);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Decisao.valueOf(nomeDecisao);
	}

}
