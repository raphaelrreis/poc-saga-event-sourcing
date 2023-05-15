package com.example.syncarquitetura;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.math.BigDecimal;
import java.util.UUID;

import static java.util.UUID.randomUUID;
import static org.springframework.test.util.ReflectionTestUtils.setField;

class ObjetosParaTestes {

    static final PedidoSolicitadoEvent pedidoSolicitadoEvent(){
        return pedidoSolicitadoEvent(randomUUID());
    }
    
	static final PedidoAprovadoEvent pedidoAprovadoEvent() {
        return pedidoAprovadoEvent(randomUUID());
	}
	
	static final PedidoReprovadoEvent pedidoReprovadoEvent() {
        return new PedidoReprovadoEvent(randomUUID());
	}
	
	static final VendaAprovadaEvent vendaAprovadaEvent() {
		return new VendaAprovadaEvent(randomUUID());
	}
	
	static final VendaReprovadaEvent vendaReprovadaEvent() {
		return new VendaReprovadaEvent(randomUUID());
	}

    static final PedidoSolicitadoEvent pedidoSolicitadoEvent(UUID correlationId){
        return new PedidoSolicitadoEvent(correlationId, new BigDecimal("12.34"));
    }

    static final PedidoAprovadoEvent pedidoAprovadoEvent(UUID correlationId){
        return new PedidoAprovadoEvent(correlationId);
    }

    static String toJson(Object obj){
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
    
    static final InformacoesDoPedido informacoesDoPedido(BigDecimal valor) {
    	return informacoesDoPedido(UUID.randomUUID(), valor);
    }

    static final InformacoesDoPedido informacoesDoPedido(UUID correlationId, BigDecimal valor) {
    	InformacoesDoPedido informacoesDoPedido = new InformacoesDoPedido();
    	setField(informacoesDoPedido, "correlationId", correlationId);
    	setField(informacoesDoPedido, "total", valor);
    	return informacoesDoPedido;
    }
}
