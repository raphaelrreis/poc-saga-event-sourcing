package com.example.syncarquitetura;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class ReceptorDeVendasValidadas {
	
    @Autowired
    private ServicoDeAutorizacao servico;

    @KafkaListener(topics = "${topico.validacoes.nome}", 
    		groupId = "${spring.kafka.group_id}",
    		containerFactory = "vendaValidadaKafkaListener")
    public void receber(VendaValidadaEvent evento) {
        log.info("Recebendo venda validada " + evento);
    	servico.receber(evento);
    }
}
