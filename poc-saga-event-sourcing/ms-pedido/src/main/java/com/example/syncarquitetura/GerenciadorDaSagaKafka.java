package com.example.syncarquitetura;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component("gerenciadorDaSagaKafka")
class GerenciadorDaSagaKafka implements GerenciadorDaSaga{

	@Autowired
	private KafkaTemplate<String, PedidoSolicitadoEvent> kafkaTemplate;

	@Value("${topico.pedidos.nome}")
	private String nomeDoTopico;

	@Override
	public void criarPedido(PedidoSolicitadoEvent event) {
		kafkaTemplate.send(nomeDoTopico, event.getCorrelationId().toString(), event);
	}

}
