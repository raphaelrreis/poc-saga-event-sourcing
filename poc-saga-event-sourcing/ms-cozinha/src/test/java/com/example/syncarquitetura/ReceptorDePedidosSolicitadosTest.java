package com.example.syncarquitetura;

import static com.example.syncarquitetura.ObjetosParaTestes.pedidoSolicitadoEvent;
import static java.util.UUID.randomUUID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.verify;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;

@DirtiesContext
@ExtendWith(MockitoExtension.class)
@EmbeddedKafka
@SpringBootTest(properties = "spring.kafka.bootstrap-servers=${spring.embedded.kafka.brokers}")
//@SpringBootTest
public class ReceptorDePedidosSolicitadosTest {

    @SpyBean
    private ReceptorDePedidosSolicitados receptor;

    @MockBean
    private CozinhaService service;

    @Captor
    private ArgumentCaptor<ConsumerRecord<String, PedidoSolicitadoEvent>> captor;

    @Autowired
    private KafkaTemplate<String, PedidoSolicitadoEvent> pedidoKafkaTemplate;

    @Value("${topico.pedidos.nome}")
    private String nomeTopico;

    @Test
    void receberPedido(){
        PedidoSolicitadoEvent event = pedidoSolicitadoEvent(randomUUID());

        pedidoKafkaTemplate.send(nomeTopico, event);

        verify(receptor, timeout(5000).times(1)).receber(captor.capture());
        assertThat(captor.getValue().value()).isEqualTo(event);
        verify(service).receberPedidoSolicitado(event);
    }
}
