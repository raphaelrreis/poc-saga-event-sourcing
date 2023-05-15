package com.example.syncarquitetura;

import static org.springframework.test.util.ReflectionTestUtils.setField;

import java.util.UUID;

class ObjetosParaTestes {

    static final PedidoSolicitadoEvent pedidoSolicitadoEvent(){
        return pedidoSolicitadoEvent(UUID.randomUUID());
    }

    static final PedidoSolicitadoEvent pedidoSolicitadoEvent(UUID correlationId){
        PedidoSolicitadoEvent event = new PedidoSolicitadoEvent();
        setField(event, "correlationId", correlationId);
        return event;
    }

    static ConsumidorVerificadoEvent consumidorVerificadoEvent(UUID correlationId) {
        ConsumidorVerificadoEvent event = new ConsumidorVerificadoEvent();
        setField(event, "correlationId", correlationId);
        return event;
    }
}
