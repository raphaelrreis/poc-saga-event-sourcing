package com.example.syncarquitetura;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ReceptorDeVendas {

    @Autowired
    private CozinhaService service;

    @KafkaListener(topics = "${topico.vendas.nome}",
    		groupId = "${spring.kafka.group_id}",
    		containerFactory = "vendaAprovadaKafkaListener")
    void receber(VendaAprovadaEvent event) {
        log.info("Recebendo venda aprovada " + event);
    	service.receberVendaAprovada(event);
    }
    

    @KafkaListener(topics = "${topico.vendas_reprovadas.nome}",
    		groupId = "${spring.kafka.group_id}",
    		containerFactory = "vendaReprovadaKafkaListener")
    void receber(VendaReprovadaEvent event) {
    	log.info("Recebendo venda reprovada " + event);
        service.receberVendaReprovada(event);
    }
}
