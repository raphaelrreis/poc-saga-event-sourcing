package com.example.syncarquitetura;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;

@AllArgsConstructor
@EqualsAndHashCode
@Document("mensagens_processadas")
public class MensagemProcessada {

	@Id
	private final String id;
}
