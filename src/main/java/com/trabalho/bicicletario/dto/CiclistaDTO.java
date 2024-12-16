package com.trabalho.bicicletario.dto;

import com.trabalho.bicicletario.model.Ciclista;
import com.trabalho.bicicletario.model.Passaporte;

public class CiclistaDTO extends Ciclista {
    private Passaporte passaporte;

    public Passaporte getPassaporte() {
        return passaporte;
    }

    public void setPassaporte(Passaporte passaporte) {
        this.passaporte = passaporte;
    }
}
