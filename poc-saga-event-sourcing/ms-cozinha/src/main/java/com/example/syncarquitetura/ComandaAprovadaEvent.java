package com.example.syncarquitetura;

import java.util.UUID;

import org.apache.kafka.common.header.Headers;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@EqualsAndHashCode(exclude = "headers")
@NoArgsConstructor
@ToString
public class ComandaAprovadaEvent {

	@Getter
    @JsonProperty("correlation_id")
    private UUID correlationId;
	
	@Getter
	@JsonIgnore
	private Headers headers;

    public ComandaAprovadaEvent(UUID correlationId, Headers headers) {
        this.correlationId = correlationId;
        this.headers = headers;
    }
}
