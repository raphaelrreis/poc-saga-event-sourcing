package com.example.syncarquitetura;

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

import static com.example.syncarquitetura.ObjetosParaTestes.vendaAprovadaEvent;
import static com.example.syncarquitetura.ObjetosParaTestes.vendaReprovadaEvent;
import static java.util.UUID.randomUUID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.verify;

@DirtiesContext
@ExtendWith(MockitoExtension.class)
@EmbeddedKafka
@SpringBootTest(properties = "spring.kafka.bootstrap-servers=${spring.embedded.kafka.brokers}")
// comente as linhas acima e descomente as abaixo caso queira realizar um teste integrado
//@SpringBootTest
class ReceptorDeVendasTest {

    @SpyBean
    private ReceptorDeVendas receptor;

    @MockBean
    private CozinhaService service;

    @Captor
    private ArgumentCaptor<VendaAprovadaEvent> vendaAprovadaCaptor;
    
    @Autowired
    private KafkaTemplate<String, VendaAprovadaEvent> vendaAprovadaKafkaTemplate;
    
    @Captor
    private ArgumentCaptor<VendaReprovadaEvent> vendaReprovadaCaptor;
     
    @Autowired
    private KafkaTemplate<String, VendaReprovadaEvent> vendaReprovadaKafkaTemplate;

    @Value("${topico.vendas.nome}")
    private String topicoVendasAprovadas;
    
    @Value("${topico.vendas_reprovadas.nome}")
    private String topicoVendasReprovadas;

    @Test
    void receberVendaAprovada(){
        VendaAprovadaEvent event = vendaAprovadaEvent(randomUUID());

        vendaAprovadaKafkaTemplate.send(topicoVendasAprovadas, event);

        verify(receptor, timeout(5000).times(1)).receber(vendaAprovadaCaptor.capture());
        assertThat(vendaAprovadaCaptor.getValue()).isEqualTo(event);
        verify(service).receberVendaAprovada(event);
    }
    
    @Test
    void receberVendaReprovada(){
        VendaReprovadaEvent event = vendaReprovadaEvent();

        vendaReprovadaKafkaTemplate.send(topicoVendasReprovadas, event);

        verify(receptor, timeout(5000).times(1)).receber(vendaReprovadaCaptor.capture());
        assertThat(vendaReprovadaCaptor.getValue()).isEqualTo(event);
        verify(service).receberVendaReprovada(event);
    }
}
