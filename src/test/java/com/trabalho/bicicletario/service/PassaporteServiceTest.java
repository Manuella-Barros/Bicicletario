package com.trabalho.bicicletario.service;

import com.trabalho.bicicletario.exception.CustomException;
import com.trabalho.bicicletario.model.Passaporte;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PassaporteServiceTest {

    @InjectMocks
    PassaporteService passaporteService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testePassaporteInvalido() throws CustomException {
        Passaporte passaporte = new Passaporte();
        assertThrows(CustomException.class, () -> {
            passaporteService.createPassaporte(passaporte);
        });
    }

}