package com.trabalho.bicicletario.model.integracoes;

import com.trabalho.bicicletario.model.Cartao;
import com.trabalho.bicicletario.model.integracoes.dtos.CobrancaDTO;
import com.trabalho.bicicletario.model.integracoes.dtos.responses.CobrancaResponseDTO;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.time.YearMonth;
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
class CobrancaTestIntegracao {

    @TestConfiguration
    static class TestConfig {

        @Bean
        @Primary // Substitui o bean original do contexto
        public RestTemplate mockRestTemplate() {
            return Mockito.mock(RestTemplate.class); // Retorna um mock do RestTemplate
        }
    }

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    Cobranca cobranca;

    @Test
    void validarCartao_cartaoValido_retornaTrue() {
        YearMonth yearMonth = YearMonth.of(2032, 12);
        Cartao cartao = new Cartao(1, "João da Silva", "4111111111111111", yearMonth, "123");
        String url = "https://bicicletario-gimk.onrender.com/externo/validaCartaoDeCredito";
        when(restTemplate.postForEntity(url, cartao, String.class))
                .thenReturn(new ResponseEntity<>("Cartão válido!", HttpStatus.OK));

        boolean resultado = cobranca.validarCartao(cartao);

        assertTrue(resultado, "O cartão válido deveria retornar true.");
    }

    @Test
    void validarCartao_cartaoInvalido_retornaFalse() {
        YearMonth yearMonth = YearMonth.of(2032, 12);
        Cartao cartao = new Cartao(1, "João da Silva", "4111111111111111", yearMonth, "123");
        String url = "https://bicicletario-gimk.onrender.com/externo/validaCartaoDeCredito";
        when(restTemplate.postForEntity(url, cartao, String.class))
                .thenReturn(new ResponseEntity<>("Cartão inválido!", HttpStatus.UNPROCESSABLE_ENTITY));

        boolean resultado = cobranca.validarCartao(cartao);

        assertFalse(resultado, "O cartão inválido deveria retornar false.");
    }

//    @Test  // ---------->AJUSTAR
//    void enviarCobranca_sucesso_retornaTrue() {
//        int idCiclista = 1;
//        double valor = 150.0;
//        CobrancaDTO cobrancaDTO = new CobrancaDTO(idCiclista, valor);
//
//        String url = "https://bicicletario-gimk.onrender.com/externo/cobranca";
//        when(restTemplate.postForEntity(url, cobrancaDTO, String.class))
//                .thenReturn(ResponseEntity.ok("Sucesso"));
//
//        boolean resultado = cobranca.enviarCobranca(idCiclista, valor);
//
//        assertTrue(resultado, "A cobrança válida deveria retornar true.");
//    }

    @Test
    void enviarCobranca_falha_retornaFalse() {
        int idCiclista = 1;
        double valor = 150.0;
        CobrancaDTO cobrancaDTO = new CobrancaDTO(1, 150.0);

        String url = "https://bicicletario-gimk.onrender.com/externo/cobranca";
        when(restTemplate.postForEntity(url, cobrancaDTO, CobrancaResponseDTO.class))
                .thenReturn(new ResponseEntity<>(new CobrancaResponseDTO(), HttpStatus.UNPROCESSABLE_ENTITY));

        boolean resultado = cobranca.enviarCobranca(idCiclista, valor);

        assertFalse(resultado, "Uma cobrança inválida deveria retornar false.");
    }
}