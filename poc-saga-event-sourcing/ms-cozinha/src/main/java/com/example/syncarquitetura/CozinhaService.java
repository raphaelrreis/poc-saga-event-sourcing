package com.example.syncarquitetura;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
class CozinhaService {

    private GerenciadorDaSaga saga;
    private RepositorioEventosDeVenda repositorioEventosDeVenda;
    private RepositorioComandas repositorioComandas;
    private Estoque estoque;
    
    @Autowired
    CozinhaService(RepositorioEventosDeVenda repositorioEventosDeVenda,
                   GerenciadorDaSaga gerenciador,
    		RepositorioComandas repositorioComandas, Estoque estoque){
    	this.repositorioEventosDeVenda = repositorioEventosDeVenda;
    	this.saga = gerenciador;
    	this.repositorioComandas = repositorioComandas;
        this.estoque = estoque;
    }

    void receberPedidoSolicitado(PedidoSolicitadoEvent evento){
    	repositorioEventosDeVenda.save(evento);
    	
    	Comanda comanda = evento.toComanda();
    	repositorioComandas.save(comanda);
    	
    	ComandaAprovadaEvent comandaAprovada = comanda.toComandaAprovadaEvent(evento.getHeaders());
        saga.comandaAprovada(comandaAprovada);
    }

    void receberVendaAprovada(VendaAprovadaEvent evento) {
        repositorioEventosDeVenda.save(evento);
        Comanda comanda = repositorioComandas.findByCorrelationId(evento.getCorrelationId());
        comanda.marcarEmPreparacao();
        estoque.separarIngredientes(evento.toPedidoDeReservaDeEstoque());
        repositorioComandas.save(comanda);
    }

	void receberVendaReprovada(VendaReprovadaEvent evento) {
		repositorioEventosDeVenda.save(evento);
		Comanda comanda = repositorioComandas.findByCorrelationId(evento.getCorrelationId());
		comanda.marcarCancelada();
		repositorioComandas.save(comanda);
	}
}
