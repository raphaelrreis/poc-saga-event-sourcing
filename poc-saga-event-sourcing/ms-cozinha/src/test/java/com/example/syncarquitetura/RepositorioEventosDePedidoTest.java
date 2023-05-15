package com.example.syncarquitetura;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static com.example.syncarquitetura.ObjetosParaTestes.*;
import static java.util.Arrays.asList;
import static java.util.UUID.randomUUID;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class RepositorioEventosDePedidoTest {
	
	@Autowired
	private RepositorioEventosDeVenda repositorioEventosDePedido;

	private PedidoSolicitadoEvent eventoDePedidoSolicitado;
	private VendaAprovadaEvent eventoDeVendaAprovada;
	private VendaReprovadaEvent eventoDeVendaReprovada;

	@BeforeEach
	void setUp(){
		eventoDePedidoSolicitado = pedidoSolicitadoEvent();
		eventoDeVendaAprovada = vendaAprovadaEvent(randomUUID());
		eventoDeVendaReprovada = vendaReprovadaEvent();

		repositorioEventosDePedido.save(eventoDePedidoSolicitado);
		repositorioEventosDePedido.save(eventoDeVendaAprovada);
		repositorioEventosDePedido.save(eventoDeVendaReprovada);
	}
	
	@Test
	void persistirEventoDePedido() {
		asList(eventoDePedidoSolicitado, eventoDeVendaAprovada, eventoDeVendaReprovada)
				.stream()
				.forEach(evento -> assertThat(repositorioEventosDePedido.findById(evento.getId())).contains(evento));
	}
}
