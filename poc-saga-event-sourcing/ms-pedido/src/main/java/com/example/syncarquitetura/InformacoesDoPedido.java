package com.example.syncarquitetura;

import java.math.BigDecimal;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class InformacoesDoPedido {
	
	@JsonProperty
	private BigDecimal total;
	
	@JsonProperty("correlation_id")
	private UUID correlationId;

	public PedidoSolicitadoEvent toPedidoSolicitadoEvent() {
		return new PedidoSolicitadoEvent(correlationId, total);
	}
}
