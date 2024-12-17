package com.trabalho.bicicletario.model.integracoes;

import com.trabalho.bicicletario.model.Bicicleta;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@NoArgsConstructor
public class Tranca {
    public int idTranca;
    public int idBicicleta;
    public String status;

    public Tranca getTranca(int idTranca){
        this.idTranca = idTranca;
        return new Tranca();
    }

    public Tranca destrancar(int idTranca, int idBicicleta){
        this.idTranca = idTranca;
        this.idBicicleta = idBicicleta;
        return new Tranca();
    }

    public Bicicleta getBicicletaByIdTranca(int idTranca){
        this.idTranca = idTranca;
        return new Bicicleta();
    }

    public Bicicleta alterarStatusTranca(String status, int idTranca){
        this.status = status;
        this.idTranca = idTranca;
        return new Bicicleta();
    }
}
