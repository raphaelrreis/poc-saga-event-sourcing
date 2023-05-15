package com.example.syncarquitetura;

import static com.example.syncarquitetura.ObjetosParaTestes.pedidoSolicitadoEvent;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
public class ReceptorTest {

    @SpyBean
    private Receptor receptor;

    @MockBean
    private ConsumidorService service;
    
    @MockBean
    private RepositorioMensagensProcessadas repositorioMensagensProcessadas;

    @Captor
    private ArgumentCaptor<PedidoSolicitadoEvent> captor;

    @Autowired
    private KafkaTemplate<String, PedidoSolicitadoEvent> pedidoKafkaTemplate;

    @Value("${topico.pedidos.nome}")
    private String nomeTopico;

    @Test
    void receberEventoNovoDePedidoSolicitado(){
    	PedidoSolicitadoEvent event = pedidoSolicitadoEvent();
    	when(repositorioMensagensProcessadas.existsById(event.getCorrelationId().toString())).thenReturn(false);

        pedidoKafkaTemplate.send(nomeTopico, event);

        verify(receptor, timeout(10000).times(1)).receber(captor.capture());
        assertThat(captor.getValue()).isEqualTo(event);
        verify(service).verificarConsumidor(event);
        verify(repositorioMensagensProcessadas).save(event.toMensagemProcessada());
    }
    
    @Test
    void receberEventoRepetido() {
        PedidoSolicitadoEvent event = pedidoSolicitadoEvent();
        when(repositorioMensagensProcessadas.existsById(event.getCorrelationId().toString())).thenReturn(true);

        pedidoKafkaTemplate.send(nomeTopico, event);

        verify(receptor, timeout(10000).times(1)).receber(captor.capture());
        assertThat(captor.getValue()).isEqualTo(event);
        verify(service, never()).verificarConsumidor(event);
        verify(repositorioMensagensProcessadas, never()).save(event.toMensagemProcessada());
    }
}
