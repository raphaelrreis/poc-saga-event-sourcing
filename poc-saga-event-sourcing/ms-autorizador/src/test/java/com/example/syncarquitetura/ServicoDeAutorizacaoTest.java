package com.example.syncarquitetura;

import static com.example.syncarquitetura.Estado.APROVADA;
import static com.example.syncarquitetura.Estado.REPROVADA;
import static com.example.syncarquitetura.ObjetosParaTestes.pedidoSolicitadoEvent;
import static com.example.syncarquitetura.ObjetosParaTestes.vendaValidadaEvent;
import static java.util.UUID.randomUUID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import com.example.syncarquitetura.teste.PedidoSolicitadoEvent;

class ServicoDeAutorizacaoTest {

    @Mock
    private RepositorioVendas repositorioVendas;

    @Mock
    private Autorizador autorizador;
    
    @Mock
    private GerenciadorDaSaga saga;

    @InjectMocks
    private ServicoDeAutorizacao servicoDeAutorizacao;

    private UUID correlationId;
    private VendaValidadaEvent evento;
    private Venda venda;

    @BeforeEach
    void setUp() {
        correlationId = randomUUID();
        evento = vendaValidadaEvent(correlationId);
        venda = new Venda(correlationId, BigDecimal.TEN);

        openMocks(this);
    }

    @Test
    void criarUmaVendaPendente(){
        PedidoSolicitadoEvent evento = pedidoSolicitadoEvent();
        servicoDeAutorizacao.criarVendaPendente(evento);

        verify(repositorioVendas).save(evento.toVenda());
    }

    @Test
    void aprovarVendaAposComandaSerAprovada(){
        when(repositorioVendas.findById(correlationId)).thenReturn(Optional.of(venda));
        when(autorizador.autoriza(venda)).thenReturn(true);

        servicoDeAutorizacao.receber(evento);

        verify(repositorioVendas).save(venda);
        verify(saga).vendaAprovada(venda.marcarComoAprovada());
        assertThat(venda.statusAtual()).isEqualTo(APROVADA);
    }

    @Test
    void reprovarVendaAposComandaSerAprovada(){
        when(repositorioVendas.findById(correlationId)).thenReturn(Optional.of(venda));
        when(autorizador.autoriza(venda)).thenReturn(false);

        servicoDeAutorizacao.receber(evento);

        verify(repositorioVendas).save(venda);
        verify(saga).vendaReprovada(venda.marcarComoReprovada());
        assertThat(venda.statusAtual()).isEqualTo(REPROVADA);
    }
}
