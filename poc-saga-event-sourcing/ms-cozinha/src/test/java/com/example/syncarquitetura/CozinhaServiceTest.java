package com.example.syncarquitetura;

import org.apache.kafka.common.header.internals.RecordHeaders;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.UUID;

import static com.example.syncarquitetura.ObjetosParaTestes.*;
import static java.util.UUID.randomUUID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

class CozinhaServiceTest {

	@Mock
	private RepositorioEventosDeVenda repositorioEventosDePedido;
	
	@Mock
	private RepositorioComandas repositorioComandas;
	
    @Mock
    private GerenciadorDaSaga saga;

    @Mock
    private Estoque estoque;

    @InjectMocks
    private CozinhaService service;

    @BeforeEach
    public void init() {
        openMocks(this);
    }

    @Test
    void receberPedidoSolicitado(){
        PedidoSolicitadoEvent evento = pedidoSolicitadoEvent();
        service.receberPedidoSolicitado(evento);

        verify(repositorioEventosDePedido).save(evento);
        verify(repositorioComandas).save(evento.toComanda());
        verify(saga).comandaAprovada(evento.toComanda().toComandaAprovadaEvent(new RecordHeaders()));
    }

    @Test
    void receberVendaAprovada(){
        UUID correlationId = randomUUID();
        VendaAprovadaEvent evento = vendaAprovadaEvent(correlationId);
        Comanda comanda = new Comanda(correlationId);
        PedidoDeReservaDeEstoque pedido = evento.toPedidoDeReservaDeEstoque();
        when(repositorioComandas.findByCorrelationId(correlationId)).thenReturn(comanda);

        service.receberVendaAprovada(evento);

        verify(repositorioEventosDePedido).save(evento);
        verify(repositorioComandas).save(comanda);
        verify(estoque).separarIngredientes(pedido);
        assertThat(comanda.emPreparacao()).isTrue();
    }
    
    @Test
    void receberVendaReprovada() {
        UUID correlationId = randomUUID();
        VendaReprovadaEvent evento = vendaReprovadaEvent(correlationId);
        Comanda comanda = new Comanda(correlationId);
        when(repositorioComandas.findByCorrelationId(correlationId)).thenReturn(comanda);

        service.receberVendaReprovada(evento);

        verify(repositorioEventosDePedido).save(evento);
        verify(repositorioComandas).save(comanda);
        assertThat(comanda.emPreparacao()).isFalse();
    }
}
