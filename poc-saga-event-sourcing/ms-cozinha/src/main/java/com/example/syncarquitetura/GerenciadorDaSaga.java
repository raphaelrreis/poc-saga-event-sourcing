package com.example.syncarquitetura;

interface GerenciadorDaSaga {

    void comandaAprovada(ComandaAprovadaEvent consumidorVerificado);
}
