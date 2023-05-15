package com.example.syncarquitetura;

import java.util.UUID;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor(access = AccessLevel.PACKAGE)
@EqualsAndHashCode(callSuper = true)
@ToString
class PedidoReprovadoEvent extends PedidoEvent {
	
	PedidoReprovadoEvent(UUID correlationId) {
        super(correlationId);
    }
}
