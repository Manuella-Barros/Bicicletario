package com.trabalho.bicicletario.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Setter @Getter
public class Bicicleta {
    private int id;
    private String marca;
    private String modelo;
    private String ano;
    private Integer numero;
    private String status;

    public Bicicleta(int id, String marca, String modelo, String ano, Integer numero, String status) {
        this.id = id;
        this.marca = marca;
        this.modelo = modelo;
        this.ano = ano;
        this.numero = numero;
        this.status = status;
    }
}
