package com.example.syncarquitetura;

import org.junit.jupiter.api.Test;

import java.util.Properties;
import java.util.UUID;

import static com.example.syncarquitetura.ObjetosParaTestes.pedidoSolicitadoEvent;
import static java.util.UUID.randomUUID;
import static org.assertj.core.api.Assertions.assertThat;

class PedidoSolicitadoEventTest {

    @Test
    void converterParaCaixaDeSaidaEvent(){
        final UUID correlationId = randomUUID();
        PedidoSolicitadoEvent evento = pedidoSolicitadoEvent(correlationId);
        Properties headers = new Properties();
        headers.put("chave", "valor");

        CaixaDeSaidaEvent caixaDeSaidaEvent = evento.toCaixaDeSaidaEvent(headers);

        CaixaDeSaidaEvent caixaDeSaidaEventEsperada = new CaixaDeSaidaEvent(correlationId, evento.getValor(), headers);
        assertThat(caixaDeSaidaEvent).isEqualTo(caixaDeSaidaEventEsperada);
    }
}
