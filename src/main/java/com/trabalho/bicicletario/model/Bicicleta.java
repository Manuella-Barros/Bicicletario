package com.trabalho.bicicletario.model;

import com.trabalho.bicicletario.model.integracoes.StatusBicicletaEnum;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Setter @Getter
@Service
public class Bicicleta {
    private int id;
    private String marca;
    private String modelo;
    private String ano;
    private int numero;
    private String status;

    public Bicicleta(int id, String marca, String modelo, String ano, int numero, String status) {
        this.id = id;
        this.marca = marca;
        this.modelo = modelo;
        this.ano = ano;
        this.numero = numero;
        this.status = status;
        setStatus(StatusBicicletaEnum.DISPONIVEL.getDescricao());
    }

    public Bicicleta(Bicicleta bicicleta) {
        setStatus(StatusBicicletaEnum.NOVA.getDescricao());
        this.id = bicicleta.getId();
        this.marca = bicicleta.getMarca();
        this.modelo = bicicleta.getModelo();
        this.ano = bicicleta.getAno();
        this.numero = bicicleta.getNumero();
        this.status = bicicleta.getStatus();
    }

    public Bicicleta alterarStatusBicicleta(String status, int idBicicleta){
        this.id = idBicicleta;
        return new Bicicleta(id, marca, modelo, ano, numero, status);
    }

    public Bicicleta getBicicleta(int idBicicleta){
        this.id = idBicicleta;
        this.status = StatusBicicletaEnum.NOVA.getDescricao();
        return this;
    }

    public Bicicleta cadastrarBicicleta(Bicicleta bicicleta){
        return new Bicicleta(bicicleta);
    }
}
