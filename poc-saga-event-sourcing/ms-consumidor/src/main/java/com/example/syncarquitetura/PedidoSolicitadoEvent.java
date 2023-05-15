package com.example.syncarquitetura;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@EqualsAndHashCode
@ToString
class PedidoSolicitadoEvent {

	@Getter
    @JsonProperty("correlation_id")
    private UUID correlationId;
    
    ConsumidorVerificadoEvent toConsumidorVerificadoEvent() {
        return new ConsumidorVerificadoEvent(correlationId);
    }

	public MensagemProcessada toMensagemProcessada() {
		return new MensagemProcessada(correlationId.toString());
	}
}
