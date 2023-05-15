package com.example.syncarquitetura;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.syncarquitetura.teste.PedidoSolicitadoEvent;

@Service
class ServicoDeAutorizacao {

    @Autowired
    private RepositorioVendas repositorioVendas;

    @Autowired
    private Autorizador autorizador;
    
    @Autowired
    private GerenciadorDaSaga saga;

    public void criarVendaPendente(PedidoSolicitadoEvent evento) {
        Venda venda = evento.toVenda();
        repositorioVendas.save(venda);
    }

	public void receber(VendaValidadaEvent evento) {
        Venda venda = repositorioVendas.findById(evento.getCorrelationId()).get();

        boolean autorizou = autorizador.autoriza(venda);

        if(autorizou) {
        	saga.vendaAprovada(venda.marcarComoAprovada());
        }
        else {
        	saga.vendaReprovada(venda.marcarComoReprovada());
        }
        repositorioVendas.save(venda);
	}

}
