package com.example.syncarquitetura;

import static com.example.syncarquitetura.ObjetosParaTestes.pedidoSolicitadoEvent;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.UUID;

import org.junit.jupiter.api.Test;

class PedidoSolicitadoEventTest {

    @Test
    void converterParaConsumidorVerificadoEvent(){
        UUID correlationId = UUID.randomUUID();
        PedidoSolicitadoEvent pedidoSolicitadoEvent = pedidoSolicitadoEvent(correlationId);

        ConsumidorVerificadoEvent consumidorVerificadoEvent = pedidoSolicitadoEvent.toConsumidorVerificadoEvent();

        ConsumidorVerificadoEvent consumidorVerificadoEsperado = ObjetosParaTestes.consumidorVerificadoEvent(correlationId);

        assertThat(consumidorVerificadoEvent).isEqualTo(consumidorVerificadoEsperado);
    }
    
    @Test
    void converterParaMensagemProcessada() {
        UUID correlationId = UUID.randomUUID();
        PedidoSolicitadoEvent pedidoSolicitadoEvent = pedidoSolicitadoEvent(correlationId);

        MensagemProcessada mensagemProcessada = pedidoSolicitadoEvent.toMensagemProcessada();

        MensagemProcessada mensagemEsperada = new MensagemProcessada(correlationId.toString());

        assertThat(mensagemProcessada).isEqualTo(mensagemEsperada);
    }
}
