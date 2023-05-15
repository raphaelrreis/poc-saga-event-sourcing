package com.example.syncarquitetura;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;

import java.util.UUID;

@AllArgsConstructor
@EqualsAndHashCode
public class PedidoDeReservaDeEstoque {

    @JsonProperty
    private UUID correlationId;
}
