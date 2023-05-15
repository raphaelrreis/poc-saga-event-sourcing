package com.example.syncarquitetura;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
class ConsumidorService {

    private GerenciadorDaSaga saga;
    private RepositorioEventosConsumidor repositorioEventosConsumidor;

    @Autowired
    ConsumidorService(RepositorioEventosConsumidor repositorioEventosConsumidor, GerenciadorDaSaga saga){
        this.repositorioEventosConsumidor = repositorioEventosConsumidor;
    	this.saga = saga;
    }
    
    void verificarConsumidor(PedidoSolicitadoEvent evento){
    	ConsumidorVerificadoEvent consumidorVerificado = evento.toConsumidorVerificadoEvent();
    	repositorioEventosConsumidor.save(consumidorVerificado);
        saga.consumidorVerificado(consumidorVerificado);
    }
}
