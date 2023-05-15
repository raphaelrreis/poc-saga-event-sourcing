package com.example.syncarquitetura;

interface GerenciadorDaSaga {

    void criarPedido(PedidoSolicitadoEvent event);
}
