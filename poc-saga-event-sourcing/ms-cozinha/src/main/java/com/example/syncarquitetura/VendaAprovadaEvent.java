package com.example.syncarquitetura;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@ToString
@Entity
@DiscriminatorValue("2")
class VendaAprovadaEvent extends Event {

    VendaAprovadaEvent(UUID correlationId){
        super(correlationId);
    }

    PedidoDeReservaDeEstoque toPedidoDeReservaDeEstoque(){
        return new PedidoDeReservaDeEstoque(correlationId);
    }
}
