package com.example.syncarquitetura;

import static com.example.syncarquitetura.ObjetosParaTestes.vendaDeValor;
import static com.example.syncarquitetura.Venda.CEM_REAIS;
import static com.example.syncarquitetura.VendaTest.CEM_REAIS_E_UM_CENTAVO;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class AutorizadorTest {

    private Autorizador autorizador = new Autorizador();

    @Test
    void autorizaPagamentoQuandoOValorDaCompraSerMenorOuIgualACem(){
        Venda venda = vendaDeValor(CEM_REAIS);

        assertThat(autorizador.autoriza(venda)).isEqualTo(true);
    }

    @Test
    void naoAutorizaPagamentoQuandoOValorDaCompraSerMaiorQueCem(){
        Venda venda = vendaDeValor(CEM_REAIS_E_UM_CENTAVO);

        assertThat(autorizador.autoriza(venda)).isEqualTo(false);
    }
}
