package com.trabalho.bicicletario.service;

import com.trabalho.bicicletario.exception.CustomException;
import com.trabalho.bicicletario.model.Funcionario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class FuncionarioServiceTest {

    @InjectMocks
    FuncionarioService funcionarioService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
        public void testeFuncionarioInvalido() throws CustomException {
        Funcionario funcionario = new Funcionario();
        assertThrows(CustomException.class, () -> {
            funcionarioService.createFuncionario(funcionario);
        });
    }
}