package com.example.syncarquitetura;

import java.math.BigDecimal;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Transient;

import org.apache.kafka.common.header.Headers;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@EqualsAndHashCode(callSuper = true, exclude="headers")
@ToString
@Entity
@DiscriminatorValue("1")
class PedidoSolicitadoEvent extends Event {

    @JsonProperty
    private BigDecimal valor;
    
    @Setter
    @Getter
    @JsonIgnore
    @Transient
    private Headers headers;

	public Comanda toComanda() {
		return new Comanda(correlationId);
	}
}
