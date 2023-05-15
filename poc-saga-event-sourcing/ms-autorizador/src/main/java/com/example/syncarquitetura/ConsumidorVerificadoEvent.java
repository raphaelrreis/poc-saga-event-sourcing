package com.example.syncarquitetura;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@EqualsAndHashCode
@NoArgsConstructor
@ToString
public class ConsumidorVerificadoEvent {

	@Getter
    @JsonProperty("correlation_id")
    private UUID correlationId;

    public ConsumidorVerificadoEvent(UUID correlationId) {
        this.correlationId = correlationId;
    }
}
