package com.example.syncarquitetura;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.math.BigDecimal;
import java.util.UUID;

import static com.example.syncarquitetura.ObjetosParaTestes.informacoesDoPedido;
import static org.assertj.core.api.Assertions.assertThat;

class InformacoesDoPedidoTest {

	@Test
	void gerarPedidoSolicitadoEvent() {
		UUID correlationId = UUID.randomUUID();
		InformacoesDoPedido infosPedido = informacoesDoPedido(correlationId, BigDecimal.TEN);
		PedidoSolicitadoEvent evento = infosPedido.toPedidoSolicitadoEvent();
		
		assertThat(evento.getCorrelationId()).isEqualTo(correlationId);
		assertThat(evento.getValor()).isEqualTo(BigDecimal.TEN);
	}
	
	@Test
	void converterJsonParaObjeto() throws JsonMappingException, JsonProcessingException {
		UUID correlationId = UUID.randomUUID();
		String valor = "10.00";
		String json = String.format("{\"correlation_id\": \"%s\", \"total\": %s}", correlationId, valor);
		
		InformacoesDoPedido informacoesEsperadas = informacoesDoPedido(correlationId, new BigDecimal(valor));
		InformacoesDoPedido objeto = new ObjectMapper().readValue(json, InformacoesDoPedido.class);
		
		assertThat(objeto).isEqualTo(informacoesEsperadas);
	}
}
