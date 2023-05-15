package com.example.syncarquitetura.teste;

import java.math.BigDecimal;
import java.util.UUID;

import com.example.syncarquitetura.Venda;
import com.example.syncarquitetura.VendaEvent;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor(access=AccessLevel.PRIVATE)
@ToString
public class PedidoSolicitadoEvent extends VendaEvent {

    @JsonProperty
    private BigDecimal valor;

    public PedidoSolicitadoEvent(UUID correlationId) {
        super(correlationId);
        this.valor = BigDecimal.ZERO;
    }

    public Venda toVenda() {
        return new Venda(correlationId, valor);
    }
}
