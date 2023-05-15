package com.example.syncarquitetura;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
class PedidoService {

    private final GerenciadorDaSaga gerenciadorDaSaga;
    private final RepositorioEventosDePedido repositorioEventosDePedido;
    
    @Autowired
    PedidoService(RepositorioEventosDePedido repositorioEventosDePedido, 
    //@Qualifier("gerenciadorDaSagaKafka") 
    @Qualifier("gerenciadorDaSagaOutboxPattern") 
    GerenciadorDaSaga gerenciadorDaSaga){
        this.repositorioEventosDePedido = repositorioEventosDePedido;
    	this.gerenciadorDaSaga = gerenciadorDaSaga;
    }
    
    @Transactional
    public void criarPedido(InformacoesDoPedido informacoesDoPedido) {
    	PedidoSolicitadoEvent evento = informacoesDoPedido.toPedidoSolicitadoEvent();

    	repositorioEventosDePedido.save(evento);
    	gerenciadorDaSaga.criarPedido(evento);  		
    }

    void receberVendaAprovada(VendaAprovadaEvent evento) {
        PedidoAprovadoEvent pedidoAprovadoEvent = evento.toPedidoAprovadoEvent();
    	repositorioEventosDePedido.save(pedidoAprovadoEvent);
    	System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
    	System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
    	System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
    	System.out.printf("Pedido %s foi aprovado! ", pedidoAprovadoEvent);
    	System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
    	System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
    	System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
    }
    
    void receberVendaReprovada(VendaReprovadaEvent evento) {
    	PedidoReprovadoEvent pedidoReprovadoEvent = evento.toPedidoReprovadoEvent();
    	repositorioEventosDePedido.save(pedidoReprovadoEvent);
    	
    	System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
    	System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
    	System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
    	System.out.printf("Pedido %s foi REPROVADO! ", pedidoReprovadoEvent);
    	System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
    	System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
    	System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
    }
}
