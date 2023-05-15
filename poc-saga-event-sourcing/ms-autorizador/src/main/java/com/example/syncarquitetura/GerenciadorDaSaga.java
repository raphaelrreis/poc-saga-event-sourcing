package com.example.syncarquitetura;

interface GerenciadorDaSaga {

    void vendaAprovada(VendaAprovadaEvent vendaAprovada);

	void vendaReprovada(VendaReprovadaEvent vendaReprovadaEvent);
}
