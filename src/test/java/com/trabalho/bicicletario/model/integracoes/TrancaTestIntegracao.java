package com.trabalho.bicicletario.model.integracoes;

import static org.junit.jupiter.api.Assertions.*;

import com.trabalho.bicicletario.model.Enum.StatusBicicletaEnum;
import com.trabalho.bicicletario.model.Enum.StatusTrancaEnum;
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
        TrancaResponse mockResponse = new TrancaResponse(1,1,1,"","","", StatusTrancaEnum.TRANCAR.getDescricao());

        when(restTemplate.getForEntity(anyString(), Mockito.eq(TrancaResponse.class)))
                .thenReturn(new ResponseEntity<>(mockResponse, HttpStatus.OK));

        TrancaResponse resposta = tranca.getTranca(1);

        assertNotNull(resposta);
        assertEquals(1, resposta.getId());
        assertEquals(StatusTrancaEnum.TRANCAR.getDescricao(), resposta.getStatus());
    }

    @Test
    void getTranca_falha_NOTFOUND() {
        when(restTemplate.getForEntity(anyString(), Mockito.eq(TrancaResponse.class)))
                .thenReturn(new ResponseEntity<>(null, HttpStatus.NOT_FOUND));

        TrancaResponse resposta = tranca.getTranca(1);

        assertNull(resposta);
    }

    @Test
    void destrancar_sucesso_retornaTrancaResponse() {
        String url = "https://bicicletario.onrender.com/tranca/1/destrancar";
        TrancaResponse mockResponse = new TrancaResponse(1,1,1,"","","", StatusTrancaEnum.DESTRANCAR.getDescricao());
        BicicletaDTO bicicletaDTO = new BicicletaDTO(1);
        when(restTemplate.postForEntity(url, bicicletaDTO, TrancaResponse.class))
                .thenReturn(new ResponseEntity<>(mockResponse, HttpStatus.OK));

        TrancaResponse resposta = tranca.destrancar(1,bicicletaDTO);

        assertNotNull(resposta);
        assertEquals(StatusTrancaEnum.DESTRANCAR.getDescricao(), resposta.getStatus());
    }

    @Test
    void destrancar_falha_retornaNOTFOUND() {
        String url = "https://bicicletario.onrender.com/tranca/1/destrancar";
        BicicletaDTO bicicletaDTO = new BicicletaDTO(0);

        when(restTemplate.postForEntity(url, bicicletaDTO, TrancaResponse.class))
                .thenReturn(new ResponseEntity<>(null, HttpStatus.NOT_FOUND));

        TrancaResponse resposta = tranca.destrancar(1, bicicletaDTO);

        assertNull(resposta);
    }

    @Test
    void destrancar_falha_retornaDadosInvalidos() {
        String url = "https://bicicletario.onrender.com/tranca/1/destrancar";
        BicicletaDTO bicicletaDTO = new BicicletaDTO(0);

        when(restTemplate.postForEntity(url, bicicletaDTO, TrancaResponse.class))
                .thenReturn(new ResponseEntity<>(null, HttpStatus.UNPROCESSABLE_ENTITY));

        TrancaResponse resposta = tranca.destrancar(0, bicicletaDTO);

        assertNull(resposta);
    }

    @Test
    void getBicicletaByIdTranca_sucesso_retornaBicicletaResponse() {
        BicicletaResponse mockResponse = new BicicletaResponse(1,"","","",1, StatusBicicletaEnum.DISPONIVEL.getDescricao());

        String url = "https://bicicletario.onrender.com/tranca/1/bicicleta";
        when(restTemplate.getForEntity(url, BicicletaResponse.class))
                .thenReturn(new ResponseEntity<>(mockResponse, HttpStatus.OK));

        BicicletaResponse resposta = tranca.getBicicletaByIdTranca(1);

        assertNotNull(resposta);
        assertEquals(1, resposta.getId());
    }
    @Test
    void getBicicletaByIdTranca_falha_NOTFOUND() {

        String url = "https://bicicletario.onrender.com/tranca/1/bicicleta";
        when(restTemplate.getForEntity(url, BicicletaResponse.class))
                .thenReturn(new ResponseEntity<>(null, HttpStatus.NOT_FOUND));

        BicicletaResponse resposta = tranca.getBicicletaByIdTranca(1);

        assertNull(resposta);
    }

    @Test
    void getBicicletaByIdTranca_falha_DADOSInvalidos() {
        String url = "https://bicicletario.onrender.com/tranca/1/bicicleta";
        when(restTemplate.getForEntity(url, BicicletaResponse.class))
                .thenReturn(new ResponseEntity<>(null, HttpStatus.UNPROCESSABLE_ENTITY));

        BicicletaResponse resposta = tranca.getBicicletaByIdTranca(1);

        assertNull(resposta);
    }

    @Test
    void alterarStatusTranca_sucesso_retornaTrancaResponse() {
        TrancaResponse mockResponse = new TrancaResponse(1,1,1,"","","", StatusTrancaEnum.TRANCAR.getDescricao());
        String url = "https://bicicletario.onrender.com/tranca/1/status/Trancar";

        when(restTemplate.postForEntity(url, "", TrancaResponse.class))
                .thenReturn(new ResponseEntity<>(mockResponse, HttpStatus.OK));

        TrancaResponse resposta = tranca.alterarStatusTranca(StatusTrancaEnum.TRANCAR.getDescricao(), 1);

        assertNotNull(resposta);
        assertEquals(StatusTrancaEnum.TRANCAR.getDescricao(), resposta.getStatus());
    }

    @Test
    void alterarStatusTranca_falha_retornaNOTFOUND() {
        String url = "https://bicicletario.onrender.com/tranca/1/status/Trancar";

        when(restTemplate.postForEntity(url, "", TrancaResponse.class))
                .thenReturn(new ResponseEntity<>(null, HttpStatus.NOT_FOUND));

        TrancaResponse resposta = tranca.alterarStatusTranca(StatusTrancaEnum.TRANCAR.getDescricao(), 1);

        assertNull(resposta);
        assertEquals(StatusTrancaEnum.TRANCAR.getDescricao(), resposta.getStatus());
    }

    @Test
    void alterarStatusTranca_falha_retornaDADOSInvalidos() {
        String url = "https://bicicletario.onrender.com/tranca/1/status/Trancar";

        when(restTemplate.postForEntity(url, "", TrancaResponse.class))
                .thenReturn(new ResponseEntity<>(null, HttpStatus.UNPROCESSABLE_ENTITY));

        TrancaResponse resposta = tranca.alterarStatusTranca(StatusTrancaEnum.TRANCAR.getDescricao(), 1);

        assertNull(resposta);
        assertEquals(StatusTrancaEnum.TRANCAR.getDescricao(), resposta.getStatus());
    }
}
