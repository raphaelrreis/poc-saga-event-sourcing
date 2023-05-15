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
@RequestMapping("/atacadista")
class AtacadistaController {
	
	private HttpServletRequest request;
	
	@Autowired
	AtacadistaController(HttpServletRequest request){
		this.request = request;
	}

	@PostMapping
	ResponseEntity<Void> solicitarCompra(@RequestBody PedidoDeCompra pedido) {
		log.info("Solicitando compra de ingredientes: " + pedido);
		
		System.out.println("ms-atacadista: Headers encontrados:");
		Enumeration<String> headers = request.getHeaderNames();
		while(headers.hasMoreElements()) {
			String header = headers.nextElement();
			System.out.printf("%s = %s \n", header, request.getHeader(header));
		}
		
		return ResponseEntity.ok().build();
	}
}
