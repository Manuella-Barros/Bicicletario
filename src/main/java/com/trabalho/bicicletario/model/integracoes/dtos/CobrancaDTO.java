package com.trabalho.bicicletario.model.integracoes.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class CobrancaDTO{
    private int ciclista;
    private double valor;
}