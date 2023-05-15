package com.example.syncarquitetura;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.header.Headers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
class ReceptorDePedidosSolicitados {

    @Autowired
    private CozinhaService service;

    @KafkaListener(topics = "${topico.pedidos.nome}",
            groupId = "${spring.kafka.group_id}",
    		containerFactory = "pedidoSolicitadoKafkaListener")
    void receber(ConsumerRecord<String, PedidoSolicitadoEvent> record){
        log.info("Recebendo pedido solicitado");

        final PedidoSolicitadoEvent event = record.value();
        final Headers headers = record.headers();
        event.setHeaders(headers);

        service.receberPedidoSolicitado(event);
    }
}
