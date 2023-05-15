package com.example.syncarquitetura;

import java.math.BigDecimal;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode
@Entity
@Table(name = "vendas")
public class Venda {

    @Getter
    @Id
	@Column(columnDefinition = "char(36)")
	@Type(type = "org.hibernate.type.UUIDCharType")
    private UUID correlationId;
    private BigDecimal valor;
    private Estado estado;

    static final BigDecimal CEM_REAIS = new BigDecimal("100.0");

    public Venda(UUID correlationId, BigDecimal valor) {
        this.correlationId = correlationId;
        this.valor = valor;
        this.estado = Estado.PENDENTE;
    }

    public boolean deValorMenorOuIgualACemReais() {
        return valor == null ? true : valor.compareTo(CEM_REAIS) <= 0;
    }

    public Estado statusAtual() {
        return estado;
    }

    public VendaAprovadaEvent marcarComoAprovada() {
        this.estado = Estado.APROVADA;
        return (VendaAprovadaEvent) this.estado.toEvent(correlationId);
    }

    public VendaReprovadaEvent marcarComoReprovada() {
        this.estado = Estado.REPROVADA;
        return (VendaReprovadaEvent) this.estado.toEvent(correlationId);
    }
}
