package com.example.syncarquitetura;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.example.syncarquitetura.teste.PedidoSolicitadoEvent;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class ReceptorDePedidosSolicitados {

    @Autowired
    private ServicoDeAutorizacao autorizador;

    @KafkaListener(topics = "${topico.pedidos.nome}", 
    		groupId = "${spring.kafka.group_id}"
    		, containerFactory = "pedidoSolicitadoKafkaListener"
    		)
    public void receber(PedidoSolicitadoEvent evento) {
        log.info("Recebendo pedido solicitado " + evento);
    	autorizador.criarVendaPendente(evento);
    }
}
