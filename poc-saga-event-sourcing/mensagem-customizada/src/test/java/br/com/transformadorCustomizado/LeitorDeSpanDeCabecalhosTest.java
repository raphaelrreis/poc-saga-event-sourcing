package br.com.transformadorCustomizado;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;

import org.apache.kafka.connect.header.Headers;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

class LeitorDeSpanDeCabecalhosTest {

	@Test
	void converterParaHeaders() throws IOException {
		JsonNode cabecalhosOriginais = new ObjectMapper().readTree("{\"x-request-id\": \"1122334455\", \"content-length\": \"87\"}");
		Headers cabecalhos = LeitorDeSpanDeCabecalhos.obterCabecalhos(cabecalhosOriginais);
		
		assertThat(cabecalhos.lastWithName("x-request-id").value()).isEqualTo("1122334455");
		assertThat(cabecalhos.lastWithName("content-length").value()).isEqualTo("87");
	}
}
