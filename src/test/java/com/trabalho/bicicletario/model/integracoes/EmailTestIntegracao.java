package com.trabalho.bicicletario.model.integracoes;

import com.trabalho.bicicletario.model.integracoes.dtos.EmailDTO;
import com.trabalho.bicicletario.model.integracoes.dtos.responses.EmailResponse;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class EmailTestIntegracao {

    @TestConfiguration
    static class TestConfig {
        @Bean
        @Primary // Substitui o bean original do contexto
        public RestTemplate mockRestTemplate() {
            return Mockito.mock(RestTemplate.class); // Retorna um mock do RestTemplate
        }
    }

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private Email email;

    @Test
    void enviarEmail_sucesso_retornaRespostaValida() {
        String destinatario = "usuario@email.com";
        String assunto = "Teste de Email";
        String mensagem = "Esta é uma mensagem de teste.";
        String urlReq = "https://bicicletario-gimk.onrender.com/externo/enviarEmail";

        EmailDTO emailDTO = new EmailDTO(destinatario, assunto, mensagem);
        EmailResponse mockResponse = new EmailResponse(1, "", "", "");

        when(restTemplate.postForEntity(urlReq,emailDTO, EmailResponse.class))
                .thenReturn(new ResponseEntity<>(mockResponse, HttpStatus.OK));

        EmailResponse resposta = email.enviarEmail(emailDTO);

        assertNotNull(resposta);
    }

    @Test
    void enviarEmail_falha_NotFound() {
        String destinatario = "usuario@email.com";
        String assunto = "Teste de Email";
        String mensagem = "Esta é uma mensagem de teste.";
        String urlReq = "https://bicicletario-gimk.onrender.com/externo/enviarEmail";

        EmailDTO emailDTO = new EmailDTO(destinatario, assunto, mensagem);
        EmailResponse mockResponse = new EmailResponse(1, "", "", "");

        when(restTemplate.postForEntity(urlReq, emailDTO, EmailResponse.class))
                .thenReturn(new ResponseEntity<>(mockResponse, HttpStatus.NOT_FOUND));

        EmailResponse resposta = email.enviarEmail(emailDTO);

        assertNull(resposta);
    }

    @Test
    void enviarEmail_excecao_DadosInvalidos() {
        String destinatario = "usuario@email.com";
        String assunto = "Teste de Email";
        String mensagem = "Esta é uma mensagem de teste.";
        String urlReq = "https://bicicletario-gimk.onrender.com/externo/enviarEmail";

        EmailDTO emailDTO = new EmailDTO(destinatario, assunto, mensagem);
        EmailResponse mockResponse = new EmailResponse(1, "", "", "");

        when(restTemplate.postForEntity(urlReq, emailDTO, EmailResponse.class))
                .thenReturn(new ResponseEntity<>(mockResponse, HttpStatus.UNPROCESSABLE_ENTITY));

        EmailResponse resposta = email.enviarEmail(emailDTO);

        assertNull(resposta);
    }
}
