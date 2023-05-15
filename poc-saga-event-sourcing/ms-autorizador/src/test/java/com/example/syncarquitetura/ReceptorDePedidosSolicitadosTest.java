package com.example.syncarquitetura;

import static com.example.syncarquitetura.ObjetosParaTestes.pedidoSolicitadoEvent;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.verify;

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

import com.example.syncarquitetura.teste.PedidoSolicitadoEvent;

@DirtiesContext
@ExtendWith(MockitoExtension.class)
@EmbeddedKafka
@SpringBootTest(properties = "spring.kafka.bootstrap-servers=${spring.embedded.kafka.brokers}")
//@SpringBootTest
public class ReceptorDePedidosSolicitadosTest {

    @SpyBean
    private ReceptorDePedidosSolicitados receptorDePedidosSolicitados;

    @MockBean
    private ServicoDeAutorizacao autorizador;

    @Captor
    private ArgumentCaptor<PedidoSolicitadoEvent> captor;

    @Autowired
    private KafkaTemplate<String, PedidoSolicitadoEvent> kafkaTemplate;

    @Value("${topico.pedidos.nome}")
    private String nomeTopico;

    @Test
    void receberEventoDePedidoSolicitado(){
        PedidoSolicitadoEvent evento = pedidoSolicitadoEvent();

        kafkaTemplate.send(nomeTopico, evento);

        verify(receptorDePedidosSolicitados, timeout(5000).times(1)).receber(captor.capture());
        assertThat(captor.getValue()).isEqualTo(evento);
        verify(autorizador).criarVendaPendente(evento);
    }
}
