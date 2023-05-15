package br.com.transformadorCustomizado;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Evento {

	@JsonProperty
	private String correlationId;
	
	@JsonProperty
	private String valor;

	public Evento(String correlationId, String valor) {
		this();
		this.correlationId = correlationId;
		this.valor = valor;
	}
	
	public Evento() {}
}
