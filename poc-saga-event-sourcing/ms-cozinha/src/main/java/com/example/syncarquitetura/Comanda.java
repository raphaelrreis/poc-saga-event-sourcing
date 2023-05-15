package com.example.syncarquitetura;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.kafka.common.header.Headers;
import org.hibernate.annotations.Type;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "comandas")
public class Comanda {
	
	@Id
	@Column(columnDefinition = "char(36)")
	@Type(type = "org.hibernate.type.UUIDCharType")
	@Getter(AccessLevel.PACKAGE)
	@GeneratedValue
	private UUID id;

	@Column(columnDefinition = "char(36)")
	@Type(type = "org.hibernate.type.UUIDCharType")
	private UUID correlationId;
	private EstadoComanda estado;
	
	Comanda(UUID correlationId){
		this.correlationId = correlationId;
		estado = EstadoComanda.APROVADA;
	}
	
    ComandaAprovadaEvent toComandaAprovadaEvent(Headers headers) {
        return new ComandaAprovadaEvent(correlationId, headers);
    }

	void marcarEmPreparacao() {
		this.estado = EstadoComanda.EM_PREPARACAO;
	}

	boolean emPreparacao() {
		return estado == EstadoComanda.EM_PREPARACAO;
	}

	void marcarCancelada() {
		this.estado = EstadoComanda.REPROVADA;
	}

}
