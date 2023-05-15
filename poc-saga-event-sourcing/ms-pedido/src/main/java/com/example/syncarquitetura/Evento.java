package com.example.syncarquitetura;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@ToString
abstract class Evento {

    @JsonProperty("correlation_id")
    @Getter
    protected UUID correlationId;
}
