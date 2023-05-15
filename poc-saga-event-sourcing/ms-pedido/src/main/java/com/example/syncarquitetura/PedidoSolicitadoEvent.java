package com.example.syncarquitetura;

import java.math.BigDecimal;
import java.util.Properties;
import java.util.UUID;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
class PedidoSolicitadoEvent extends PedidoEvent {

    @Getter
    private BigDecimal valor;

    public PedidoSolicitadoEvent(UUID correlationId, BigDecimal valor) {
        super(correlationId);
        this.valor = valor;
    }

	public CaixaDeSaidaEvent toCaixaDeSaidaEvent(Properties cabecalhosDaRequisicao) {
		return new CaixaDeSaidaEvent(correlationId, valor, cabecalhosDaRequisicao);
	}
}
