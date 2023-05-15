package com.example.syncarquitetura;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.util.UUID;

import org.junit.jupiter.api.Test;

public class VendaReprovadaEventTest {
	
	@Test
	void toPedidoReprovadoEvent() {
		UUID correlationId = UUID.randomUUID();
		VendaReprovadaEvent vendaReprovada = new VendaReprovadaEvent(correlationId);
		
		PedidoReprovadoEvent pedidoReprovado = vendaReprovada.toPedidoReprovadoEvent();

		PedidoReprovadoEvent pedidoReprovadoEsperado = new PedidoReprovadoEvent(correlationId);
		assertThat(pedidoReprovado).isEqualTo(pedidoReprovadoEsperado);
	}
}
