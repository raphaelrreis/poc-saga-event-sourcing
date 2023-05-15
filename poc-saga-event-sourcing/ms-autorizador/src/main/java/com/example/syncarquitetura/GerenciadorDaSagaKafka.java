package com.example.syncarquitetura;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
class GerenciadorDaSagaKafka implements GerenciadorDaSaga {

    @Autowired
    @Qualifier("vendaKafkaTemplate")
    private KafkaTemplate<String, VendaEvent> vendaKafkaTemplate;
    
    @Value("${topico.vendas.nome}")
    private String topicoVendasAprovadas;
    
    @Value("${topico.vendas_reprovadas.nome}")
    private String topicoVendasReprovadas;

    @Override
    public void vendaAprovada(VendaAprovadaEvent vendaAprovada) {
    	vendaKafkaTemplate.send(topicoVendasAprovadas, vendaAprovada);
    }

	@Override
	public void vendaReprovada(VendaReprovadaEvent vendaReprovada) {
		vendaKafkaTemplate.send(topicoVendasReprovadas, vendaReprovada);
	}
}
