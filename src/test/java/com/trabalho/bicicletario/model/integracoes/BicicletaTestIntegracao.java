package com.trabalho.bicicletario.model.integracoes;

import com.trabalho.bicicletario.model.BicicletaModel;
import com.trabalho.bicicletario.model.Enum.StatusBicicletaEnum;
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
class BicicletaTestIntegracao {

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
    Bicicleta bicicleta;

    @Test
    void alterarStatusBicicleta_sucesso_retornaBicicletaModel() {
        int idBicicleta = 1;
        String status = StatusBicicletaEnum.DISPONIVEL.getDescricao();
        String url = "https://bicicletario.onrender.com/bicicleta/" + idBicicleta + "/status/" + status;

        BicicletaModel bicicletaModelMock = new BicicletaModel();
        bicicletaModelMock.setId(idBicicleta);
        bicicletaModelMock.setStatus(status);

        when(restTemplate.postForEntity(url, "", BicicletaModel.class))
                .thenReturn(new ResponseEntity<>(bicicletaModelMock, HttpStatus.OK));

        BicicletaModel resultado = bicicleta.alterarStatusBicicleta(status, idBicicleta);

        assertNotNull(resultado);
        assertEquals(idBicicleta, resultado.getId());
        assertEquals(status, resultado.getStatus());
    }

    @Test
    void alterarStatusBicicleta_falha_bicicletaNOTFOUD_retornaBicicletaModel() {
        int idBicicleta = 1;
        String status = StatusBicicletaEnum.DISPONIVEL.getDescricao();
        String url = "https://bicicletario.onrender.com/bicicleta/" + idBicicleta + "/status/" + status;

        BicicletaModel bicicletaModelMock = new BicicletaModel();
        bicicletaModelMock.setId(idBicicleta);
        bicicletaModelMock.setStatus(status);

        when(restTemplate.postForEntity(url, "", BicicletaModel.class))
                .thenReturn(new ResponseEntity<>(bicicletaModelMock, HttpStatus.NOT_FOUND));

        BicicletaModel resultado = bicicleta.alterarStatusBicicleta(status, idBicicleta);

        assertNull(resultado);
    }

    @Test
    void alterarStatusBicicleta_falha_bicicletaDadosInvalidos_retornaBicicletaModel() {
        int idBicicleta = 0;
        String status = "status";
        String url = "https://bicicletario.onrender.com/bicicleta/" + idBicicleta + "/status/" + status;

        BicicletaModel bicicletaModelMock = new BicicletaModel();
        bicicletaModelMock.setId(idBicicleta);
        bicicletaModelMock.setStatus(status);

        when(restTemplate.postForEntity(url, "", BicicletaModel.class))
                .thenReturn(new ResponseEntity<>(bicicletaModelMock, HttpStatus.UNPROCESSABLE_ENTITY));

        BicicletaModel resultado = bicicleta.alterarStatusBicicleta(status, idBicicleta);

        assertNull(resultado);
    }
}