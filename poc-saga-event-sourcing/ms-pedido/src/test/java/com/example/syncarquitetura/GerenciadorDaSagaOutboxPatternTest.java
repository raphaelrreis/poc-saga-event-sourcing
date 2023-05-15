package com.example.syncarquitetura;

import static com.example.syncarquitetura.ObjetosParaTestes.pedidoSolicitadoEvent;
import static java.util.Arrays.asList;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Properties;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class GerenciadorDaSagaOutboxPatternTest {

	@Mock
	private CaixaDeSaida caixaDeSaida;

	@Mock
	private HttpServletRequest requisicao; 
	
    @InjectMocks
    private GerenciadorDaSagaOutboxPattern saga;

    @Test
    void enviarPedidoAoKafka() {
    	PedidoSolicitadoEvent evento = pedidoSolicitadoEvent();
    	
    	String umHeader = "um-header";
    	String umValor = "um-valor";
    	
    	when(requisicao.getHeaderNames()).thenReturn(new Vector<>(asList(umHeader)).elements());
    	when(requisicao.getHeader(umHeader)).thenReturn(umValor);
    	Properties cabecalhosDaRequisicao = new Properties();
    	cabecalhosDaRequisicao.put(umHeader, umValor);
    	
    	saga.criarPedido(evento);

    	verify(caixaDeSaida).save(evento.toCaixaDeSaidaEvent(cabecalhosDaRequisicao));
    }
}