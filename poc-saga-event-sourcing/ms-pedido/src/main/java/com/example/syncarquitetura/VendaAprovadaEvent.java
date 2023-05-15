package com.example.syncarquitetura;

import java.util.UUID;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
class VendaAprovadaEvent extends Evento {

	VendaAprovadaEvent(UUID correlationId) {
		super(correlationId);
	}

	PedidoAprovadoEvent toPedidoAprovadoEvent() {
		return new PedidoAprovadoEvent(correlationId);
	}

}
