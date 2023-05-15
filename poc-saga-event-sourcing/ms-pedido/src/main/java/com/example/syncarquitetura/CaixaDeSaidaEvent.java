package com.example.syncarquitetura;

import java.math.BigDecimal;
import java.util.Properties;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode(exclude = "id")
@Document("outbox")
class CaixaDeSaidaEvent {
	
    @Id
    @Getter
    private String id;
    
	private final UUID correlationId;
    
	private final BigDecimal valor;
    
    private final Properties cabecalhosDaRequisicao;
    
    CaixaDeSaidaEvent(UUID correlationId, BigDecimal valor, Properties cabecalhosDaRequisicao){
    	this.correlationId = correlationId;
    	this.valor = valor;
    	this.cabecalhosDaRequisicao = cabecalhosDaRequisicao;
    }
}
