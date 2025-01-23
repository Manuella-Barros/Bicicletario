package com.trabalho.bicicletario.model.integracoes;

import static org.junit.jupiter.api.Assertions.*;

import com.trabalho.bicicletario.model.Enum.StatusBicicletaEnum;
import com.trabalho.bicicletario.model.integracoes.dtos.BicicletaDTO;
import com.trabalho.bicicletario.model.integracoes.dtos.responses.BicicletaResponse;
import com.trabalho.bicicletario.model.integracoes.dtos.responses.TrancaResponse;
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

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest
class TrancaTestIntegracao {

    @TestConfiguration
    static class TestConfig {

        @Bean
        @Primary
        public RestTemplate mockRestTemplate() {
            return Mockito.mock(RestTemplate.class);
        }
    }

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private Tranca tranca;

    @Test
    void getTranca_sucesso_retornaTrancaResponse() {
        TrancaResponse mockResponse = new TrancaResponse(1,1,1,"","","", StatusBicicletaEnum.DISPONIVEL.getDescricao());

        when(restTemplate.getForEntity(anyString(), Mockito.eq(TrancaResponse.class)))
                .thenReturn(new ResponseEntity<>(mockResponse, HttpStatus.OK));

        TrancaResponse resposta = tranca.getTranca(1);

        assertNotNull(resposta);
        assertEquals(1, resposta.getId());
        assertEquals(StatusBicicletaEnum.DISPONIVEL.getDescricao(), resposta.getStatus());
    }

    @Test
    void getTranca_falha_NOTFOUND() {
        when(restTemplate.getForEntity(anyString(), Mockito.eq(TrancaResponse.class)))
                .thenReturn(new ResponseEntity<>(null, HttpStatus.NOT_FOUND));

        TrancaResponse resposta = tranca.getTranca(1);

        assertNull(resposta);
    }

//    @Test
//    void destrancar_sucesso_retornaTrancaResponse() {
//        TrancaResponse mockResponse = new TrancaResponse(1, "Tranca Mock", "DESBLOQUEADA");
//
//        when(restTemplate.postForEntity(anyString(), any(BicicletaDTO.class), Mockito.eq(TrancaResponse.class)))
//                .thenReturn(new ResponseEntity<>(mockResponse, HttpStatus.OK));
//
//        TrancaResponse resposta = tranca.destrancar(1, 101);
//
//        assertNotNull(resposta, "A resposta não deveria ser nula.");
//        assertEquals("DESBLOQUEADA", resposta.getStatus(), "A tranca deveria estar DESBLOQUEADA.");
//    }

//    @Test
//    void destrancar_falha_retornaNull() {
//        when(restTemplate.postForEntity(anyString(), any(BicicletaDTO.class), Mockito.eq(TrancaResponse.class)))
//                .thenReturn(new ResponseEntity<>(null, HttpStatus.BAD_REQUEST));
//
//        TrancaResponse resposta = trancaService.destrancar(1, 101);
//
//        assertNull(resposta, "A resposta deveria ser nula em caso de falha.");
//    }
//
//    @Test
//    void getBicicletaByIdTranca_sucesso_retornaBicicletaResponse() {
//        BicicletaResponse mockResponse = new BicicletaResponse(101, "Bicicleta Mock");
//
//        when(restTemplate.getForEntity(anyString(), Mockito.eq(BicicletaResponse.class)))
//                .thenReturn(new ResponseEntity<>(mockResponse, HttpStatus.OK));
//
//        BicicletaResponse resposta = trancaService.getBicicletaByIdTranca(1);
//
//        assertNotNull(resposta, "A resposta não deveria ser nula.");
//        assertEquals(101, resposta.getId(), "O ID da bicicleta deveria ser 101.");
//    }
//
//    @Test
//    void alterarStatusTranca_sucesso_retornaTrancaResponse() {
//        TrancaResponse mockResponse = new TrancaResponse(1, "Tranca Mock", "BLOQUEADA");
//
//        when(restTemplate.postForEntity(anyString(), anyString(), Mockito.eq(TrancaResponse.class)))
//                .thenReturn(new ResponseEntity<>(mockResponse, HttpStatus.OK));
//
//        TrancaResponse resposta = trancaService.alterarStatusTranca("BLOQUEADA", 1);
//
//        assertNotNull(resposta, "A resposta não deveria ser nula.");
//        assertEquals("BLOQUEADA", resposta.getStatus(), "O status da tranca deveria ser BLOQUEADA.");
//    }
//
//    @Test
//    void alterarStatusTranca_falha_retornaNull() {
//        when(restTemplate.postForEntity(anyString(), anyString(), Mockito.eq(TrancaResponse.class)))
//                .thenReturn(new ResponseEntity<>(null, HttpStatus.BAD_REQUEST));
//
//        TrancaResponse resposta = trancaService.alterarStatusTranca("BLOQUEADA", 1);
//
//        assertNull(resposta, "A resposta deveria ser nula em caso de falha.");
//    }
}
