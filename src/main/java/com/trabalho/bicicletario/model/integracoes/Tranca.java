package com.trabalho.bicicletario.model.integracoes;

import com.trabalho.bicicletario.model.integracoes.dtos.BicicletaDTO;
import com.trabalho.bicicletario.model.integracoes.dtos.responses.BicicletaResponse;
import com.trabalho.bicicletario.model.integracoes.dtos.responses.TrancaResponse;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@AllArgsConstructor
public class Tranca {

    @Qualifier("restTemplate") private final RestTemplate restTemplate;
    private final String url = "https://bicicletario.onrender.com/";

    public TrancaResponse getTranca(int idTranca){
        //busca tranca no equipamento
        //cria url
        String urlReq = url+ "tranca/"+ idTranca;
        TrancaResponse trancaResponse=null;
        try {
            //requisicao
            ResponseEntity<TrancaResponse> response = restTemplate.getForEntity(urlReq, TrancaResponse.class);
            //retorno nao é bem sucessido
            if (!response.getStatusCode().is2xxSuccessful()) {
                return trancaResponse;//retorna null
            }
            trancaResponse = response.getBody();//retorno bem sucedido
        } catch (Exception ex) {
            return trancaResponse;//null
        }

        return trancaResponse;
    }

    public TrancaResponse destrancar(int idTranca, int idBicicleta) {
        //destranca a bicicleta da tranca no equipamento

        //cria url
        String urlReq = url + "tranca/" + idTranca+ "/destrancar";
        //monta variavel pro post
        BicicletaDTO bicicletaDTO = new BicicletaDTO(idBicicleta);
        //variavel de resposta
        TrancaResponse trancaResponse = null;
        try {
            //requisicao
            ResponseEntity<TrancaResponse> response = restTemplate.postForEntity(urlReq, bicicletaDTO, TrancaResponse.class);
            //retorno nao é bem sucessido
            if (!response.getStatusCode().is2xxSuccessful()) {
                return trancaResponse;//retorna null
            }
            trancaResponse = response.getBody();//retorno bem sucedido
        } catch (Exception ex) {
            return trancaResponse;//null
        }

        return trancaResponse;
    }

    public BicicletaResponse getBicicletaByIdTranca(int idTranca){
        //busca bicicleta pela tranca no equipamento

        //cria url
        String urlReq = url+ "tranca/"+ idTranca + "/bicicleta";

        BicicletaResponse bicicletaResponse=null;
        try {
            //requisicao
            ResponseEntity<BicicletaResponse> response = restTemplate.getForEntity(urlReq, BicicletaResponse.class);
            //retorno nao é bem sucessido
            if (!response.getStatusCode().is2xxSuccessful()) {
//                return trancaResponse;//retorna null
            }
            bicicletaResponse = response.getBody();//retorno bem sucedido
        } catch (Exception ex) {
            return bicicletaResponse;//null
        }

       return bicicletaResponse;
    }

    public TrancaResponse alterarStatusTranca(String status, int idTranca){
        //altera status tranca no equipamento

        //cria url
        String urlReq = url + "tranca/" + idTranca+ "/status/" + status;
        //variavel de resposta
        TrancaResponse trancaResponse = null;
        try {
            //requisicao
            ResponseEntity<TrancaResponse> response = restTemplate.postForEntity(urlReq,"", TrancaResponse.class);
            //retorno nao é bem sucessido
            if (!response.getStatusCode().is2xxSuccessful()) {
                return trancaResponse;//retorna null
            }
            trancaResponse = response.getBody();//retorno bem sucedido
        } catch (Exception ex) {
            return trancaResponse;//null
        }

        return trancaResponse;
    }
}
