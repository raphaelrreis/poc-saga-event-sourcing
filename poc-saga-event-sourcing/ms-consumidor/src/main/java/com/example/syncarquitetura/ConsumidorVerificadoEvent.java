package com.example.syncarquitetura;

import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@EqualsAndHashCode
@NoArgsConstructor
@ToString
@Document("eventos_consumidores")
public class ConsumidorVerificadoEvent {

    @JsonProperty("correlation_id")
    @Getter
    @Id
    private UUID correlationId;

    public ConsumidorVerificadoEvent(UUID correlationId) {
        this.correlationId = correlationId;
    }
}
