package com.example.syncarquitetura;

import static com.example.syncarquitetura.ObjetosParaTestes.comandaAprovadaEvent;
import static java.util.UUID.randomUUID;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.UUID;

import org.apache.kafka.common.header.internals.RecordHeaders;
import org.junit.jupiter.api.Test;

class ComandaTest {

    @Test
    void converterParaComandaAprovadaEvent() {
        UUID correlationId = randomUUID();
        Comanda comanda = new Comanda(correlationId);
        ComandaAprovadaEvent eventoObtido = comanda.toComandaAprovadaEvent(new RecordHeaders());

        ComandaAprovadaEvent eventoEsperado = comandaAprovadaEvent(correlationId);
        assertThat(eventoObtido).isEqualTo(eventoEsperado);
    }
}
