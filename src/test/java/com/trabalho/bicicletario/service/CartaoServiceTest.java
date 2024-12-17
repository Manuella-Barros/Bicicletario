package com.trabalho.bicicletario.service;

import com.trabalho.bicicletario.exception.CustomException;
import com.trabalho.bicicletario.model.Cartao;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CartaoServiceTest {

    @InjectMocks
    CartaoService cartaoService;

    @Test
    public void testeCartaoInvalido() throws CustomException {
        Cartao cartao = new Cartao();
        assertThrows(CustomException.class, () -> {
            cartaoService.createCartao(cartao);
        });
    }

}