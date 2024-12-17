package com.trabalho.bicicletario.model.integracoes;

import com.trabalho.bicicletario.model.Cartao;
import org.springframework.stereotype.Service;

@Service
public class Cobranca {
    Cartao cartao;
    int idCiclista;
    Double valor;

    public boolean validarCartao(Cartao cartao) {
        this.cartao = cartao;
        return true;
    }

    public boolean enviarCobranca(int idCiclista, Double valor) {
        this.idCiclista = idCiclista;
        this.valor = valor;
        return true;
    }
}
