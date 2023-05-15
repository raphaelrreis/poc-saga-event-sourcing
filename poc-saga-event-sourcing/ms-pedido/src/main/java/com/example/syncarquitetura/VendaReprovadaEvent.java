package com.example.syncarquitetura;

import java.util.UUID;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
class VendaReprovadaEvent extends Evento {

    VendaReprovadaEvent(UUID correlationId) {
		super(correlationId);
	}

	PedidoReprovadoEvent toPedidoReprovadoEvent() {
        return new PedidoReprovadoEvent(correlationId);
    }
}
