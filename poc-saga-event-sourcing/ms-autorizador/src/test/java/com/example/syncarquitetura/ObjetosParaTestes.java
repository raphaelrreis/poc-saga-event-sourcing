package com.example.syncarquitetura;

import static java.util.UUID.randomUUID;
import static org.springframework.test.util.ReflectionTestUtils.setField;

import java.math.BigDecimal;
import java.util.UUID;

import com.example.syncarquitetura.teste.PedidoSolicitadoEvent;

public class ObjetosParaTestes {

    public static final ConsumidorVerificadoEvent consumidorVerificadoEvent() {
        return new ConsumidorVerificadoEvent(randomUUID());
    }
    
    public static final VendaValidadaEvent vendaValidadaEvent() {
    	return new VendaValidadaEvent(randomUUID());
    }

    public static final VendaValidadaEvent vendaValidadaEvent(UUID correlationId){
        return new VendaValidadaEvent(correlationId);
    }

    public static final PedidoSolicitadoEvent pedidoSolicitadoEvent(){
        return new PedidoSolicitadoEvent(randomUUID());
    }

    public static final PedidoSolicitadoEvent pedidoSolicitadoEvent(BigDecimal valor){
       return pedidoSolicitadoEvent(randomUUID(), valor);
    }

    public static final PedidoSolicitadoEvent pedidoSolicitadoEvent(UUID correlationId, BigDecimal valor){
        PedidoSolicitadoEvent evento = new PedidoSolicitadoEvent(correlationId);
        setField(evento, "valor", valor);
        return evento;
    }

    public static final Venda vendaDeValor(BigDecimal valor){
        return new Venda(randomUUID(), valor);
    }

    public static final VendaAprovadaEvent vendaAprovadaEvent() {
        return new VendaAprovadaEvent(randomUUID());
    }
    
    public static final VendaReprovadaEvent vendaReprovadaEvent() {
    	return new VendaReprovadaEvent(randomUUID());
    }

    public static final ComandaAprovadaEvent comandaAprovadaEvent(UUID correlationId){
        return new ComandaAprovadaEvent(correlationId);
    }

    public static final ConsumidorVerificadoEvent consumidorVerificadoEvent(UUID correlationId){
        return new ConsumidorVerificadoEvent(correlationId);
    }
}
