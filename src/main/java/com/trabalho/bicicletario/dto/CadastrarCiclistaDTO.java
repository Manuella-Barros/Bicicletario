package com.trabalho.bicicletario.dto;

import com.trabalho.bicicletario.model.Cartao;

public class CadastrarCiclistaDTO {
    public CiclistaDTO ciclistaDTO;
    public Cartao meioDePagamento;

    public CadastrarCiclistaDTO(CiclistaDTO ciclista, Cartao meioDePagamento) {
        this.ciclistaDTO = ciclista;
        this.meioDePagamento = meioDePagamento;
    }

    public CiclistaDTO getCiclistaDTO() {
        return ciclistaDTO;
    }

    public void setCiclistaDTO(CiclistaDTO ciclistaDTO) {
        this.ciclistaDTO = ciclistaDTO;
    }

    public Cartao getMeioDePagamento() {
        return meioDePagamento;
    }

    public void setMeioDePagamento(Cartao meioDePagamento) {
        this.meioDePagamento = meioDePagamento;
    }
}
