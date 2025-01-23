package com.trabalho.bicicletario.dto;

public class CadastrarCiclistaDTO {
    public CiclistaDTO ciclistaDTO;
    public CartaoDTO meioDePagamento;

    public CadastrarCiclistaDTO(CiclistaDTO ciclista, CartaoDTO meioDePagamento) {
        this.ciclistaDTO = ciclista;
        this.meioDePagamento = meioDePagamento;
    }

    public CiclistaDTO getCiclistaDTO() {
        return ciclistaDTO;
    }

    public void setCiclistaDTO(CiclistaDTO ciclistaDTO) {
        this.ciclistaDTO = ciclistaDTO;
    }

    public CartaoDTO getMeioDePagamento() {
        return meioDePagamento;
    }

    public void setMeioDePagamento(CartaoDTO meioDePagamento) {
        this.meioDePagamento = meioDePagamento;
    }
}
