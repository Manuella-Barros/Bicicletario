package com.trabalho.bicicletario.model.integracoes;

import com.trabalho.bicicletario.model.BicicletaModel;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@AllArgsConstructor
public class Bicicleta {
    @Qualifier("restTemplate") private final RestTemplate restTemplate;
    private final String url = "https://bicicletario.onrender.com/";

    public BicicletaModel alterarStatusBicicleta(String status, int idBicicleta){
        //altera status tranca no equipamento

        //cria url
        String urlReq = url + "bicicleta/" + idBicicleta + "/status/" + status;

        //variavel de resposta
        BicicletaModel bicicletaResponse = null;
        try {
            //requisicao
            ResponseEntity<BicicletaModel> response = restTemplate.postForEntity(urlReq,"", BicicletaModel.class);
            //retorno nao é bem sucessido
            if (!response.getStatusCode().is2xxSuccessful()) {
                return bicicletaResponse;//retorna null
            }
            bicicletaResponse = response.getBody();//retorno bem sucedido
        } catch (Exception ex) {
            return bicicletaResponse;//null
        }

        return bicicletaResponse;
    }
}
