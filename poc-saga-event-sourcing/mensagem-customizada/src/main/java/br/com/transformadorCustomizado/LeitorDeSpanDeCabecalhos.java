package br.com.transformadorCustomizado;

import java.util.Iterator;

import org.apache.kafka.connect.header.ConnectHeaders;
import org.apache.kafka.connect.header.Headers;

import com.fasterxml.jackson.databind.JsonNode;

class LeitorDeSpanDeCabecalhos {

	public static Headers obterCabecalhos(JsonNode cabecalhosOriginais) {
		Headers cabecalhos = new ConnectHeaders();
		Iterator<String> nomesCabecalhos = cabecalhosOriginais.fieldNames();
		while(nomesCabecalhos.hasNext()) {
			String nomeCabecalho = nomesCabecalhos.next();
			cabecalhos.addString(nomeCabecalho, cabecalhosOriginais.get(nomeCabecalho).asText());
		}
		return cabecalhos;
	}

}
