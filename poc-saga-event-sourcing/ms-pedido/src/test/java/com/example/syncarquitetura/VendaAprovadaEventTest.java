package com.example.syncarquitetura;

import java.util.UUID;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class VendaAprovadaEventTest {
	
	@Test
	void toPedidoAprovadoEvent() {
		UUID correlationId = UUID.randomUUID();
		VendaAprovadaEvent vendaAprovada = new VendaAprovadaEvent(correlationId);
		
		PedidoAprovadoEvent pedidoAprovado = vendaAprovada.toPedidoAprovadoEvent();

		PedidoAprovadoEvent pedidoAprovadoEsperado = new PedidoAprovadoEvent(correlationId);
		assertThat(pedidoAprovado).isEqualTo(pedidoAprovadoEsperado);
	}
}
