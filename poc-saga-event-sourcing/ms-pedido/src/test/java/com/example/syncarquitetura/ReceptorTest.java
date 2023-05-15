package com.example.syncarquitetura;

import static com.example.syncarquitetura.ObjetosParaTestes.vendaAprovadaEvent;
import static com.example.syncarquitetura.ObjetosParaTestes.vendaReprovadaEvent;
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

@DirtiesContext
@ExtendWith(MockitoExtension.class)
@EmbeddedKafka
@SpringBootTest(properties = "spring.kafka.bootstrap-servers=${spring.embedded.kafka.brokers}")
//@SpringBootTest
public class ReceptorTest {

	@SpyBean
	private Receptor receptor;
	
	@MockBean
	private PedidoService service;
	
	@Captor
	private ArgumentCaptor<VendaAprovadaEvent> captorAprovado;
	
	@Captor
	private ArgumentCaptor<VendaReprovadaEvent> captorReprovado;
	
	@Autowired
	private KafkaTemplate<String, VendaAprovadaEvent> aprovadoKafkaTemplate;

	@Autowired
	private KafkaTemplate<String, VendaReprovadaEvent> reprovadoKafkaTemplate;
	
    @Value("${topico.vendas.nome}")
    private String topicoDeVendas;
    
    @Value("${topico.vendas_reprovadas.nome}")
    private String topicoDeVendasReprovadas;

    @Test
    void receberEventoDePedidoAprovado(){
        VendaAprovadaEvent evento = vendaAprovadaEvent();

        aprovadoKafkaTemplate.send(topicoDeVendas, evento);

        verify(receptor, timeout(5000).times(1)).receber(captorAprovado.capture());
        assertThat(captorAprovado.getValue()).isEqualTo(evento);
        verify(service).receberVendaAprovada(evento);
    } 
    
    @Test
    void receberEventoDePedidoReprovado(){
        VendaReprovadaEvent evento = vendaReprovadaEvent();

        reprovadoKafkaTemplate.send(topicoDeVendasReprovadas, evento);

        verify(receptor, timeout(5000).times(1)).receber(captorReprovado.capture());
        assertThat(captorReprovado.getValue()).isEqualTo(evento);
        verify(service).receberVendaReprovada(evento);
    } 
}
