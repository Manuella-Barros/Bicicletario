package com.trabalho.bicicletario.service;

import static org.junit.jupiter.api.Assertions.*;

import com.trabalho.bicicletario.exception.CustomException;
import com.trabalho.bicicletario.model.Aluguel;
import com.trabalho.bicicletario.model.Ciclista;
import com.trabalho.bicicletario.model.integracoes.Tranca;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AluguelServiceTest {

    @InjectMocks
    AluguelService aluguelService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testaCriaAluguelComErro() throws CustomException {
        Aluguel aluguel = new Aluguel();
        aluguel.setTrancaInicio(0);
        Ciclista ciclista = new Ciclista();

        assertThrows(CustomException.class, () -> {
            aluguelService.createAluguel(aluguel, ciclista);
        });
    }

}