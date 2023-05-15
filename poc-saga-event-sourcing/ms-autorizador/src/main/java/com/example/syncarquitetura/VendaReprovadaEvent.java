package com.example.syncarquitetura;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor(access=AccessLevel.PRIVATE)
@EqualsAndHashCode(callSuper = true)
@ToString
class VendaReprovadaEvent extends VendaEvent {
	  
    @JsonProperty
    private String motivo;
    
    VendaReprovadaEvent(UUID correlationId){
    	super(correlationId);
    	this.motivo = "Valor não suportado para o pedido";
    }
}
