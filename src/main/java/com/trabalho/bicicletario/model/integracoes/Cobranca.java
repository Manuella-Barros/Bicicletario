package com.trabalho.bicicletario.model.integracoes;

import com.trabalho.bicicletario.model.Cartao;

public class Cobranca {
    public boolean validarCartao(Cartao cartao) {
        return true;
    }

    public boolean enviarCobranca(int idCiclista, Double valor) {
        return true;
    }
}
