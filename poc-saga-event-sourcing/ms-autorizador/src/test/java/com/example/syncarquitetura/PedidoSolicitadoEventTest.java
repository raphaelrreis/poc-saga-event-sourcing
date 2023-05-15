package com.example.syncarquitetura;

import static com.example.syncarquitetura.ObjetosParaTestes.pedidoSolicitadoEvent;
import static java.math.BigDecimal.TEN;
import static java.util.UUID.randomUUID;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.UUID;

import org.junit.jupiter.api.Test;

import com.example.syncarquitetura.teste.PedidoSolicitadoEvent;

class PedidoSolicitadoEventTest {

    @Test
    void converterParaVenda(){
        UUID correlationId = randomUUID();
        PedidoSolicitadoEvent evento = pedidoSolicitadoEvent(correlationId, TEN);
        Venda vendaEsperada = new Venda(correlationId, TEN);

        assertThat(evento.toVenda()).isEqualTo(vendaEsperada);
    }
}
