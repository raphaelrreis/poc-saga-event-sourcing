package com.example.syncarquitetura;

import static com.example.syncarquitetura.ObjetosParaTestes.pedidoSolicitadoEvent;
import static com.example.syncarquitetura.ObjetosParaTestes.vendaAprovadaEvent;
import static com.example.syncarquitetura.ObjetosParaTestes.vendaReprovadaEvent;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.mongodb.MongoTransactionManager;

@ExtendWith(MockitoExtension.class)
class PedidoServiceTest {

    @Mock
    private GerenciadorDaSaga saga;

    @Mock
    private RepositorioEventosDePedido repositorioEventosDePedido;
    
    @Mock
    private MongoTransactionManager transactionManager;

    @InjectMocks
    private PedidoService servico;
    
    @Test
    void receberUmaSolicitacaoDePedido() {
    	InformacoesDoPedido informacoesDoPedido = mock(InformacoesDoPedido.class);
        PedidoSolicitadoEvent evento = pedidoSolicitadoEvent();
    	when(informacoesDoPedido.toPedidoSolicitadoEvent()).thenReturn(evento);
    	
    	servico.criarPedido(informacoesDoPedido);
    	
        verify(repositorioEventosDePedido).save(evento);
        verify(saga).criarPedido(evento);
    }

    @Test
    void receberUmPedidoAprovado(){
        VendaAprovadaEvent event = vendaAprovadaEvent();

        servico.receberVendaAprovada(event);

        verify(repositorioEventosDePedido).save(event.toPedidoAprovadoEvent());
    }
    
    @Test
    void receberUmPedidoReprovado(){
        VendaReprovadaEvent event = vendaReprovadaEvent();

        servico.receberVendaReprovada(event);

        verify(repositorioEventosDePedido).save(event.toPedidoReprovadoEvent());
    }
}
