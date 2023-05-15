package com.example.syncarquitetura;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
class Receptor {

    @Autowired
    private ConsumidorService service;
    
    @Autowired
    private RepositorioMensagensProcessadas repositorioMensagensProcessadas;
    
    private static final Logger log = LoggerFactory.getLogger(Receptor.class);

    @KafkaListener(topics = "${topico.pedidos.nome}", 
    		groupId = "${spring.kafka.group_id}",
    		containerFactory = "pedidoSolicitadoKafkaListener")
    public void receber(PedidoSolicitadoEvent event) {
        log.info("Recebendo pedido solicitado " + event);
    	if(!repositorioMensagensProcessadas.existsById(event.getCorrelationId().toString())) {
        	service.verificarConsumidor(event);
        	repositorioMensagensProcessadas.save(event.toMensagemProcessada());
        }
    }
}
