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
    private Integer numero;
    private String status;

    public Bicicleta(int id, String marca, String modelo, String ano, Integer numero, String status) {
        this.id = id;
        this.marca = marca;
        this.modelo = modelo;
        this.ano = ano;
        this.numero = numero;
        this.status = status;
        setStatus(StatusBicicletaEnum.DISPONIVEL.getDescricao());
    }

    public Bicicleta(Bicicleta bicicleta) {
        this.id = bicicleta.getId();
        this.marca = bicicleta.getMarca();
        this.modelo = bicicleta.getModelo();
        this.ano = bicicleta.getAno();
        this.numero = bicicleta.getNumero();
        this.status = bicicleta.getStatus();
        setStatus(StatusBicicletaEnum.NOVA.getDescricao());
    }

    public Bicicleta alterarStatusBicicleta(String status, int idBicicleta){
        return new Bicicleta(id, marca, modelo, ano, numero, status);
    }

    public Bicicleta getBicicleta(int idBicicleta){
        return new Bicicleta(id, marca, modelo, ano, numero, status);
    }

    public Bicicleta cadastrarBicicleta(Bicicleta bicicleta){
        return new Bicicleta(bicicleta);
    }
}
