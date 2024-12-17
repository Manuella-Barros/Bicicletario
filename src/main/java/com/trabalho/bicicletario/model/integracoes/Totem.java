package com.trabalho.bicicletario.model.integracoes;

import com.trabalho.bicicletario.model.Bicicleta;
import org.springframework.stereotype.Service;

@Service
public class Totem {
    public Bicicleta getBicicleta (int idBicicleta){
        Bicicleta bicicleta = new Bicicleta();
        bicicleta.setStatus(StatusBicicletaEnum.DISPONIVEL.getDescricao());
        return bicicleta;
    }
}