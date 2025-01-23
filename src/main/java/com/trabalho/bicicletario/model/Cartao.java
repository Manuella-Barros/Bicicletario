package com.trabalho.bicicletario.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.trabalho.bicicletario.dto.CartaoDTO;
import com.trabalho.bicicletario.util.converter.YearMonthStringConverter;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.YearMonth;

@Entity
@RequiredArgsConstructor
@Setter @Getter
public class Cartao {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "nome_titular")
    private String nomeTitular;
    private String numero;

    @JsonFormat(pattern = "yyyy-MM")
    private YearMonth validade;
    private String cvv;

    public Cartao(int id, String nomeTitular, String numero, YearMonth validade, String cvv) {
        this.id = id;
        this.nomeTitular = nomeTitular;
        this.numero = numero;
        this.validade = validade;
        this.cvv = cvv;
    }
    public Cartao(Cartao cartao) {
        this.id = cartao.getId();
        this.nomeTitular = cartao.getNomeTitular();
        this.numero = cartao.getNumero();
        this.validade = cartao.getValidade();
        this.cvv = cartao.getCvv();
    }

    public Cartao(CartaoDTO cartaoDTO) {
        this.nomeTitular = cartaoDTO.getNomeTitular();
        this.numero = cartaoDTO.getNumero();
        this.cvv = cartaoDTO.getCvv();

        String oldValiditty = cartaoDTO.getValidade().substring(0,7);

        YearMonthStringConverter converter = new YearMonthStringConverter();
        this.validade = converter.convertToEntityAttribute(oldValiditty);
    }

    public boolean checkIfValid(){
        return this.nomeTitular != null && this.numero != null && this.validade != null && this.cvv != null && (this.cvv.length() == 3 || this.cvv.length() == 4);
    }
}
