package com.example.syncarquitetura;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
        name = "msAtacadista",
        url = "${ms-atacadista.url}")
public interface Atacadista {

	@PostMapping
	void realizarCompra(@RequestBody PedidoDeCompra pedidoDeCompra);
}
