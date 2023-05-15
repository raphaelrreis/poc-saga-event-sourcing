package com.example.syncarquitetura;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class PedidoDeReservaDeEstoque {

    @JsonProperty("correlation_id")
    private UUID correlationId;

	public PedidoDeCompra toPedidoDeCompra() {
		return new PedidoDeCompra(correlationId);
	}
}
