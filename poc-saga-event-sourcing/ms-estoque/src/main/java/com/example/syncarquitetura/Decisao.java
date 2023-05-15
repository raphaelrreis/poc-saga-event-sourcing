package com.example.syncarquitetura;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpServerErrorException;

public enum Decisao {
	ERRO_GERAL {
		@Override
		ResponseEntity executar() {
			System.out.println("ERRO GERAL NO MS-ESTOQUE");
			throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR,
					"Exceção disparada pelo Spring em caso de problemas na aplicação");
		}
	},
	SUCESSO {
		@Override
		ResponseEntity executar() {
			System.out.println("INGREDIENTES SELECIONADOS");
			return ResponseEntity.ok().build();
		}
	},
	SUCESSO_TIMEOUT {
		@Override
		ResponseEntity executar() {
			System.out.println("MS-ESTOQUE SELECIONANDO INGREDIENTES LENTAMENTE");
			esperar(5000);
			return SUCESSO.executar();
		}

		private void esperar(long milisegundos) {
			try {
				Thread.sleep(milisegundos);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	};

	abstract ResponseEntity executar();
}
