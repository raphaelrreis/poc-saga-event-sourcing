package com.example.syncarquitetura;

import static com.example.syncarquitetura.ObjetosParaTestes.pedidoAprovadoEvent;
import static com.example.syncarquitetura.ObjetosParaTestes.pedidoSolicitadoEvent;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

@DataMongoTest
//@SpringBootTest // para rodar numa base de dados real
//@ImportAutoConfiguration(exclude = EmbeddedMongoAutoConfiguration.class) // para rodar numa base de dados real
//@Transactional // para rodar numa base de dados real. Garante rollback dos dados persistidos nos testes
class RepositorioEventosDePedidoTest {
	
	@Autowired
	private RepositorioEventosDePedido repositorioEventosDePedido;
	
	@Test
	void persistirUmEventoDePedido() {
		PedidoSolicitadoEvent evento = pedidoSolicitadoEvent();
		
		repositorioEventosDePedido.save(evento);

		Optional<PedidoEvent> byId = repositorioEventosDePedido.findById(evento.getId());

		assertThat(byId).isNotEmpty();

		PedidoEvent pedidoEvent = byId.get();
		assertThat(pedidoEvent).isEqualTo(evento);
	}
	
	@Test
	void persistirEventosDeUmMesmoPedido() {
		UUID correlationId = UUID.randomUUID();
		PedidoSolicitadoEvent pedidoSolicitadoEvent = pedidoSolicitadoEvent(correlationId);
		PedidoAprovadoEvent pedidoAprovadoEvent = pedidoAprovadoEvent(correlationId);

		repositorioEventosDePedido.save(pedidoSolicitadoEvent);
		repositorioEventosDePedido.save(pedidoAprovadoEvent);

		List<PedidoEvent> all = repositorioEventosDePedido.findAll();

		assertThat(all).containsSequence(pedidoSolicitadoEvent, pedidoAprovadoEvent);
	}
}
