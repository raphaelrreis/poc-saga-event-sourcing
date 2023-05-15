package com.example.syncarquitetura;

import static com.example.syncarquitetura.ObjetosParaTestes.pedidoSolicitadoEvent;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Properties;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.data.mongodb.MongoTransactionException;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@ImportAutoConfiguration(exclude = EmbeddedMongoAutoConfiguration.class) // para rodar numa base de dados real
class PedidoServiceIntegrationTest {

	@Autowired
	private PedidoService pedidoService;

	@Autowired
	private MongoTemplate mongoTemplate; 

	@SpyBean
	private RepositorioEventosDePedido repositorioEventosDePedido;

	@SpyBean
	@Qualifier("gerenciadorDaSagaOutboxPattern")
	//@Qualifier("gerenciadorDaSagaKafka")
	private GerenciadorDaSaga gerenciadorDaSaga;
	
	private InformacoesDoPedido informacoesDoPedido;
	private PedidoSolicitadoEvent evento;

	@BeforeEach
	void setUp() {
		informacoesDoPedido = mock(InformacoesDoPedido.class);
		evento = pedidoSolicitadoEvent();
		when(informacoesDoPedido.toPedidoSolicitadoEvent()).thenReturn(evento);
	}

	@Test
	void erroNaPersistenciaDoEventoDuranteACriacaoDoPedidoImpedeEnvioAoGerenciadorDaSaga() {
		doThrow(MongoTransactionException.class).when(repositorioEventosDePedido).save(evento);

		try {
			pedidoService.criarPedido(informacoesDoPedido);
			fail("Deveria ter lançado exceção!");
		}catch(MongoTransactionException e) {
			assertThat(mongoTemplate.findAll(PedidoSolicitadoEvent.class)).doesNotContain(evento);
			verify(gerenciadorDaSaga, never()).criarPedido(evento);
		}
	}

	@Test
	void erroNoEnvioAoGerenciadorDaSagaGaranteRollbackNoBanco() {
		doThrow(RuntimeException.class).when(gerenciadorDaSaga).criarPedido(evento);

		try {
			pedidoService.criarPedido(informacoesDoPedido);
			fail("Deveria ter lançado exceção!");
		}catch(RuntimeException e) {
			assertThat(mongoTemplate.findAll(PedidoSolicitadoEvent.class)).doesNotContain(evento);
			assertThat(mongoTemplate.findAll(CaixaDeSaidaEvent.class)).doesNotContain(evento.toCaixaDeSaidaEvent(new Properties()));
		}
	}
	
	@Test
	@Transactional
	void persistirOPedidoSolicitadoEAMensagemNaCaixaDeSaida() {
		pedidoService.criarPedido(informacoesDoPedido);
		
		assertThat(mongoTemplate.findAll(PedidoSolicitadoEvent.class)).contains(evento);
		assertThat(mongoTemplate.findAll(CaixaDeSaidaEvent.class)).contains(evento.toCaixaDeSaidaEvent(new Properties()));
	}
}
