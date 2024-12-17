package com.trabalho.bicicletario.model.integracoes;

import com.trabalho.bicicletario.model.Bicicleta;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@NoArgsConstructor
public class Tranca {
    public Tranca getTranca(int idTranca){
        return new Tranca();
    }

    public Tranca destrancar(int idTranca, int idBicicleta){
        return new Tranca();
    }

    public Bicicleta getBicicletaByIdTranca(int idTranca){
        return new Bicicleta();
    }

    public Bicicleta alterarStatusTranca(String status, int idTranca){
        return new Bicicleta();
    }
}
