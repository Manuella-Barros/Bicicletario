package com.trabalho.bicicletario.model.integracoes.DTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class CobrancaDTO{
    private int idCiclista;
    private double valor;
}