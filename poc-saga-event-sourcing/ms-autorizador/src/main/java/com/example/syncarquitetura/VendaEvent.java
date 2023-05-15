package com.example.syncarquitetura;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor(access=AccessLevel.PROTECTED)
@EqualsAndHashCode
@ToString
public abstract class VendaEvent {

    @JsonProperty("correlation_id")
    @Getter
    protected UUID correlationId;

    public VendaEvent(UUID correlationId){
        this.correlationId = correlationId;
    }
}
