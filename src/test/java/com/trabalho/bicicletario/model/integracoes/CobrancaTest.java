package com.trabalho.bicicletario.model.integracoes;

import com.trabalho.bicicletario.model.Cartao;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;

import java.time.YearMonth;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.web.client.RestTemplate;

import static org.hamcrest.Matchers.any;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;


@SpringBootTest
class CobrancaTestIntegracao {

    @Mock
    RestTemplate restTemplate;

    @InjectMocks
    Cobranca cobranca;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Inicializa os mocks e objetos anotados
    }

    @Test
    void validarCartao() {
        YearMonth yearMonth = YearMonth.of(2032, 12);
        Cartao cartao = new Cartao(1,"João da Silva","4111111111111111", yearMonth, "123");
        assertEquals(true, cobranca.validarCartao(cartao));
    }

    @Test
    void validarCartao_cartaoValido_retornaTrue() {
        YearMonth yearMonth = YearMonth.of(2032, 12);
        Cartao cartao = new Cartao(1, "João da Silva", "4111111111111111", yearMonth, "123");

        when(restTemplate.postForEntity( ArgumentMatchers.any(),  ArgumentMatchers.any(), ArgumentMatchers.any() ))
                .thenReturn(new ResponseEntity<>("Cartão válido!", HttpStatus.OK));

        boolean resultado = cobranca.validarCartao(cartao);

        assertTrue(resultado, "O cartão válido deveria retornar true.");
    }

    @Test
    void validarCartao_cartaoInvalido_retornaFalse() { //ajustar

        YearMonth yearMonth = YearMonth.of(2032, 12);
        Cartao cartao = new Cartao(1, "João da Silva", "4111111111111111", yearMonth, "123");

        when(restTemplate.postForEntity(ArgumentMatchers.any(), any(Cartao.class), eq(String.class)))
                .thenThrow(new RuntimeException("Erro ao validar cartão"));

        boolean resultado = cobranca.validarCartao(cartao);

        assertFalse(resultado, "Um erro ao validar o cartão deveria retornar false.");
    }

}