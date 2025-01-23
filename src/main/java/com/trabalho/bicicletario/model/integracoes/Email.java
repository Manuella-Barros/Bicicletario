package com.trabalho.bicicletario.model.integracoes;

import com.trabalho.bicicletario.model.integracoes.dtos.EmailDTO;
import com.trabalho.bicicletario.model.integracoes.dtos.responses.EmailResponse;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@AllArgsConstructor
public class Email {

    @Qualifier("restTemplate") private final RestTemplate restTemplate;
    private final String url = "https://bicicletario-gimk.onrender.com/externo/";

    public EmailResponse enviarEmail(EmailDTO emailDTO) {
        //manda o email no externo
        //cria url
        String urlReq = url+ "enviarEmail";

        EmailResponse emailResponse = null;
        try {
            //requisicao
            ResponseEntity<EmailResponse> response = restTemplate.postForEntity(urlReq, emailDTO, EmailResponse.class);
            //retorno nao é bem sucessido
            if (!response.getStatusCode().is2xxSuccessful()) {
                return emailResponse;//retorna null
            }
            emailResponse = response.getBody();//retorno bem sucedido
        } catch (Exception ex) {
            return emailResponse;//null
        }

        return emailResponse;
    }
}
