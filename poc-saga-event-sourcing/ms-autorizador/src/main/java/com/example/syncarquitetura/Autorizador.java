package com.example.syncarquitetura;

import org.springframework.stereotype.Component;

@Component
class Autorizador {

    boolean autoriza(Venda venda) {
        return venda.deValorMenorOuIgualACemReais();
    }
}
