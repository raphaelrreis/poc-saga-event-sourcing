package com.example.syncarquitetura;

import static com.example.syncarquitetura.ObjetosParaTestes.vendaDeValor;
import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@Transactional
//@SpringBootTest("spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration")
class RepositorioVendasTest {

    @Autowired
    private RepositorioVendas repositorioVendas;

    @Test
    void persistirUmaVenda(){
        Venda vendaEsperada = vendaDeValor(BigDecimal.TEN);

        repositorioVendas.save(vendaEsperada);

        assertThat(repositorioVendas.existsById(vendaEsperada.getCorrelationId())).isTrue();
    }
}
