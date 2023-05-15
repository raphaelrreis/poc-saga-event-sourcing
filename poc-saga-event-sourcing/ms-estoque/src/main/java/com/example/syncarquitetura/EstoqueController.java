package com.example.syncarquitetura;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/estoque")
class EstoqueController {
	
	private final LeitorDeArquivoDeDecisao leitor;
	private final Atacadista atacadista;
	private HttpServletRequest request;
	
	@Autowired
	EstoqueController(LeitorDeArquivoDeDecisao leitor, Atacadista atacadista, HttpServletRequest request){
		this.leitor = leitor;
		this.atacadista = atacadista;
		this.request = request;
	}

	@PostMapping
	ResponseEntity<?> reservarEstoque(@RequestBody PedidoDeReservaDeEstoque pedido) {
		System.out.println("ms-estoque: Headers encontrados:");
		Enumeration<String> headers = request.getHeaderNames();
		while(headers.hasMoreElements()) {
			String header = headers.nextElement();
			System.out.printf("%s = %s \n", header, request.getHeader(header));
		}
		
		log.info("Reservando estoque para o pedido:" + pedido);
		atacadista.realizarCompra(pedido.toPedidoDeCompra());
		return leitor.obterDecisaoTomada().executar();
	}
}
