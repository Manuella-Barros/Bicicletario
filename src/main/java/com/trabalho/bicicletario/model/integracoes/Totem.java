//package com.trabalho.bicicletario.model.integracoes;
//
//import com.trabalho.bicicletario.model.Bicicleta;
//import com.trabalho.bicicletario.model.Enum.StatusBicicletaEnum;
//import com.trabalho.bicicletario.model.integracoes.dtos.EmailDTO;
//import com.trabalho.bicicletario.model.integracoes.dtos.responses.EmailResponse;
//import lombok.AllArgsConstructor;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Service;
//import org.springframework.web.client.RestTemplate;
//
//@Service
////@AllArgsConstructor
//public class Totem {
//
//    public int idBicicleta;
//
//    public Bicicleta getBicicleta (int idBicicleta){
//        this.idBicicleta = idBicicleta;
//        Bicicleta bicicleta = new Bicicleta();
//        bicicleta.setStatus(StatusBicicletaEnum.DISPONIVEL.getDescricao());
//        return bicicleta;
//    }
//
//    //VARIAVEIS NECESSARIAS PRA IMPLEMENTAR A INTEGRACAO
////    @Qualifier("restTemplate") private final RestTemplate restTemplate;
////    private final String url = "https://bicicletario-4u7u.onrender.com/";
//
////    FAZER ->>>>> metodo pra pegar bicicleta na tranca do totem
//
////    como fazer isso?
////    pegar todas as trancas e depois pegar a bike pela tranca? conferir endpoints no swagger
//
//
//    //METODO
////    public Bicicleta getBicicleta(Integer idTranca) {
////        cria url
////        String urlReq = url+ idTranca;
////
////        fazer get
////        try {
////            requisicao
////            ResponseEntity<EmailResponse> response = restTemplate.getForEntity(urlReq, emailDTO, EmailResponse.class);
////            retorno nao é bem sucessido
////            if (!response.getStatusCode().is2xxSuccessful()) {
////
////            }
////            //retorno bem sucedido
////        } catch (Exception ex) {
////            //caso de errado
////        }
//
//    //resultado deve ser uma bicicleta
////        return new Bicicleta();
////    }
//}