package com.example.syncarquitetura;

import static com.example.syncarquitetura.ObjetosParaTestes.comandaAprovadaEvent;
import static com.example.syncarquitetura.ObjetosParaTestes.consumidorVerificadoEvent;
import static com.example.syncarquitetura.ObjetosParaTestes.vendaValidadaEvent;
import static java.util.UUID.randomUUID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.awaitility.core.ThrowingRunnable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.ActiveProfiles;

// funciona aleatoriamente se rodar sozinho. Ao rodar com os outros testes, dá pau :(
//@ActiveProfiles("test")
//@DirtiesContext
//@EmbeddedKafka(topics = {
//        "${topico.comandas.nome}",
//        "${topico.consumidores.nome}",
//        "${topico.validacoes.nome}"})
//@SpringBootTest(properties = "spring.kafka.bootstrap-servers=${spring.embedded.kafka.brokers}")

@ActiveProfiles("integratedtest")
@SpringBootTest
class KafkaStreamsConfigurationTest {

    @Value("${topico.comandas.nome}")
    private String topicoDeComandas;

    @Value("${topico.consumidores.nome}")
    private String topicoDeConsumidores;

    @Value("${topico.validacoes.nome}")
    private String topicoDeVendasValidadas;

    @Autowired
    private KafkaTemplate<String, ComandaAprovadaEvent> comandaAprovadaKafkaTemplate;

    @Autowired
    private KafkaTemplate<String, ConsumidorVerificadoEvent> consumidorVerificadoKafkaTemplate;

    @Autowired
    private KafkaConsumer<String, VendaValidadaEvent> consumidorVendasValidadas;

    private UUID correlationId;

    @BeforeEach
    void setUp(){
        correlationId = randomUUID();
    }

    private void enviarEventoDeComandaAprovada(UUID correlationId){
        comandaAprovadaKafkaTemplate.send(topicoDeComandas,
                correlationId.toString(),
                comandaAprovadaEvent(this.correlationId));
    }

    private void enviarEventoDeConsumidorVerificado(UUID correlationId){
        consumidorVerificadoKafkaTemplate.send(topicoDeConsumidores,
                correlationId.toString(),
                consumidorVerificadoEvent(this.correlationId));
    }

    @Test
    void aoEnviarMensagensNosTopicosDeConsumidoresVerificadosEDeComandasAprovadasDeveSeGerarUmaMensagemDeVendaValidada(){
        enviarEventoDeComandaAprovada(correlationId);
        enviarEventoDeConsumidorVerificado(correlationId);

        VendaValidadaEvent evento = vendaValidadaEvent(correlationId);

        esperarUmTempoAte(() -> assertThat(mensagensDoTopico(consumidorVendasValidadas)).containsOnlyOnce(evento));
    }

//    @Test
//    void aoEnviarMensagemParaApenasUmTopicoNaoSeGeraUmaMensagemDeVendaValidada(){
//
//    }

    protected List<VendaEvent> mensagensDoTopico(KafkaConsumer<String, ? extends VendaEvent> consumidor){
        List<VendaEvent> eventos = new ArrayList<>();

        consumidor.poll(Duration.ofMillis(1000)).forEach(consumerRecord ->
                eventos.add(consumerRecord.value())
        );

        return eventos;
    }

    protected void esperarUmTempoAte(ThrowingRunnable runnable){
        await()
                .atMost(Duration.ofSeconds(10))
                .untilAsserted(runnable);
    }
}
