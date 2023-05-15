package com.example.syncarquitetura;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
class GerenciadorDaSagaKafka implements GerenciadorDaSaga {

    @Autowired
    private KafkaTemplate<String, ConsumidorVerificadoEvent> consumidorKafkaTemplate;

    @Value("${topico.consumidores.nome}")
    private String nomeDoTopico;

    @Override
    public void consumidorVerificado(ConsumidorVerificadoEvent event) {
        consumidorKafkaTemplate.send(nomeDoTopico, event.getCorrelationId().toString(), event);
    }
}
