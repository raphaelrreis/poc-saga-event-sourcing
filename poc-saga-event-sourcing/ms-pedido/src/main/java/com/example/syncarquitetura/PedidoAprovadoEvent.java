package com.example.syncarquitetura;

import java.util.UUID;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor(access = AccessLevel.PACKAGE)
@EqualsAndHashCode(callSuper = true)
@ToString
class PedidoAprovadoEvent extends PedidoEvent {

    PedidoAprovadoEvent(UUID correlationId) {
        super(correlationId);
    }
}

