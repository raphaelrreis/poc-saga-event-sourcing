package com.example.syncarquitetura;

import static com.example.syncarquitetura.Estado.APROVADA;
import static com.example.syncarquitetura.Estado.PENDENTE;
import static com.example.syncarquitetura.Estado.REPROVADA;
import static java.util.UUID.randomUUID;
import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class VendaTest {

    static final BigDecimal CEM_REAIS_E_UM_CENTAVO = new BigDecimal("100.01");

    private Venda vendaComValorAlto;
    
    @BeforeEach
    void setUp() {
    	vendaComValorAlto = new Venda(randomUUID(), CEM_REAIS_E_UM_CENTAVO);
    }
    
    @Test
    void valorMenorOuIgualACem(){
        Venda venda = new Venda(randomUUID(), Venda.CEM_REAIS);

        assertThat(venda.deValorMenorOuIgualACemReais()).isEqualTo(true);
    }

    @Test
    void valorMaiorQueCem(){
        assertThat(vendaComValorAlto.deValorMenorOuIgualACemReais()).isEqualTo(false);
    }

    @Test
    void mudancasDeStatus(){
        assertThat(vendaComValorAlto.statusAtual()).isEqualTo(PENDENTE);

        vendaComValorAlto.marcarComoAprovada();
        assertThat(vendaComValorAlto.statusAtual()).isEqualTo(APROVADA);

        vendaComValorAlto.marcarComoReprovada();
        assertThat(vendaComValorAlto.statusAtual()).isEqualTo(REPROVADA);
    }
    
    @Test
    void converterParaVendaAprovadaEvent() {
    	UUID correlationId = randomUUID();
    	Venda venda = new Venda(correlationId, CEM_REAIS_E_UM_CENTAVO);
    	
    	VendaAprovadaEvent eventoEsperado = new VendaAprovadaEvent(correlationId);
    	
    	assertThat(venda.marcarComoAprovada()).isEqualTo(eventoEsperado);
    }
    
    @Test
    void converterParaVendaReprovadaEvent() {
    	UUID correlationId = randomUUID();
    	Venda venda = new Venda(correlationId, CEM_REAIS_E_UM_CENTAVO);
    	
    	VendaReprovadaEvent eventoEsperado = new VendaReprovadaEvent(correlationId);
    	
    	assertThat(venda.marcarComoReprovada()).isEqualTo(eventoEsperado);
    }
}
