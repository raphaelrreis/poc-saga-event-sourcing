package com.example.syncarquitetura;

import java.util.UUID;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor(access=AccessLevel.PRIVATE)
@EqualsAndHashCode(callSuper = true)
@ToString
class VendaAprovadaEvent extends VendaEvent {

    VendaAprovadaEvent(UUID correlationId) {
        super(correlationId);
    }
}
