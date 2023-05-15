package com.example.syncarquitetura;

import java.math.BigDecimal;
import java.util.Properties;
import java.util.UUID;

import org.apache.kafka.common.header.Headers;

import static java.util.UUID.randomUUID;
import static org.springframework.test.util.ReflectionTestUtils.setField;

class ObjetosParaTestes {

    static final PedidoSolicitadoEvent pedidoSolicitadoEvent(){
        return pedidoSolicitadoEvent(randomUUID());
    }

    static final VendaAprovadaEvent vendaAprovadaEvent(UUID correlationId){
        return new VendaAprovadaEvent(correlationId);
    }
    
    static final VendaReprovadaEvent vendaReprovadaEvent() {
    	return vendaReprovadaEvent(randomUUID());
    }

    static final VendaReprovadaEvent vendaReprovadaEvent(UUID correlationId) {
    	return new VendaReprovadaEvent(correlationId);
    }

    static final PedidoSolicitadoEvent pedidoSolicitadoEvent(UUID correlationId){
        PedidoSolicitadoEvent event = new PedidoSolicitadoEvent();
        setField(event, "correlationId", correlationId);
        setField(event, "valor", BigDecimal.TEN);
        return event;
    }

    static ComandaAprovadaEvent comandaAprovadaEvent(UUID correlationId) {
        ComandaAprovadaEvent event = new ComandaAprovadaEvent();
        setField(event, "correlationId", correlationId);
        return event;
    }
    
    static ComandaAprovadaEvent comandaAprovadaEvent(UUID correlationId, Headers headers) {
    	ComandaAprovadaEvent event = comandaAprovadaEvent(correlationId);
    	setField(event, "headers", headers);
    	return event;
    }

    static final Properties headers() {
        Properties headers = new Properties();
        headers.put("chave", "valor");
        return headers;
    }
}
