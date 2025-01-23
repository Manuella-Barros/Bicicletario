package com.trabalho.bicicletario.model;

import com.trabalho.bicicletario.model.Enum.StatusBicicletaEnum;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Setter @Getter
@Service
public class BicicletaModel {
    private int id;
    private String marca;
    private String modelo;
    private String ano;
    private int numero;
    private String status;

    public BicicletaModel(int id, String marca, String modelo, String ano, int numero, String status) {
        this.id = id;
        this.marca = marca;
        this.modelo = modelo;
        this.ano = ano;
        this.numero = numero;
        this.status = status;
        setStatus(StatusBicicletaEnum.DISPONIVEL.getDescricao());
    }

    public BicicletaModel(BicicletaModel bicicletaModel) {
        setStatus(StatusBicicletaEnum.NOVA.getDescricao());
        this.id = bicicletaModel.getId();
        this.marca = bicicletaModel.getMarca();
        this.modelo = bicicletaModel.getModelo();
        this.ano = bicicletaModel.getAno();
        this.numero = bicicletaModel.getNumero();
        this.status = bicicletaModel.getStatus();
    }

    public BicicletaModel alterarStatusBicicleta(String status, int idBicicleta){
        this.id = idBicicleta;
        return new BicicletaModel(id, marca, modelo, ano, numero, status);
    }

    public BicicletaModel getBicicleta(int idBicicleta){
        this.id = idBicicleta;
        this.status = StatusBicicletaEnum.NOVA.getDescricao();
        return this;
    }

    public BicicletaModel cadastrarBicicleta(BicicletaModel bicicletaModel){
        return new BicicletaModel(bicicletaModel);
    }
}
