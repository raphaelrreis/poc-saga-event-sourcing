package com.example.syncarquitetura;

import java.util.Enumeration;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("gerenciadorDaSagaOutboxPattern")
class GerenciadorDaSagaOutboxPattern implements GerenciadorDaSaga {
	
	@Autowired
	private CaixaDeSaida caixaDeSaida;
	
	@Autowired
	private HttpServletRequest requisicao;

    @Override
    public void criarPedido(PedidoSolicitadoEvent event) {
    	caixaDeSaida.save(event.toCaixaDeSaidaEvent(lerCabecalhosDa(requisicao)));
    }

	private Properties lerCabecalhosDa(HttpServletRequest requisicao) {
		Properties cabecalhosDaRequisicao = new Properties();
		Enumeration<String> cabecalhos = requisicao.getHeaderNames();
		while(cabecalhos.hasMoreElements()) {
			String nomeCabecalho = cabecalhos.nextElement();
			cabecalhosDaRequisicao.put(nomeCabecalho, requisicao.getHeader(nomeCabecalho));
		}
		return cabecalhosDaRequisicao;
	}
}
