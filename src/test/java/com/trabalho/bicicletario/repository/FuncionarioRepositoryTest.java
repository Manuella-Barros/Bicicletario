package com.trabalho.bicicletario.repository;

import com.trabalho.bicicletario.model.Funcionario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FuncionarioRepositoryTest {

    @Mock
    private FuncionarioRepository funcionarioRepository;

    private Funcionario funcionario;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Criando um objeto Funcionario para os testes
        funcionario = new Funcionario();
        funcionario.setId(1);
        funcionario.setNome("Carlos Almeida");
        funcionario.setEmail("carlos.almeida@email.com");
        funcionario.setSenha("senha123");
        funcionario.setConfirmacaoSenha("senha123");
        funcionario.setCpf("12345678901");
        funcionario.setIdade(30);
        funcionario.setFuncao("Gerente");
        funcionario.setMatricula(12345);
    }

    @Test
    void deveSalvarFuncionarioComSucesso() {
        // Arrange
        when(funcionarioRepository.save(funcionario)).thenReturn(funcionario);

        // Act
        Funcionario salvo = funcionarioRepository.save(funcionario);

        // Assert
        assertNotNull(salvo);
        assertEquals(funcionario.getId(), salvo.getId());
        verify(funcionarioRepository, times(1)).save(funcionario);
    }

    @Test
    void deveEncontrarFuncionarioPorId() {
        // Arrange
        when(funcionarioRepository.findById(funcionario.getId())).thenReturn(Optional.of(funcionario));

        // Act
        Optional<Funcionario> encontrado = funcionarioRepository.findById(funcionario.getId());

        // Assert
        assertTrue(encontrado.isPresent());
        assertEquals(funcionario.getId(), encontrado.get().getId());
        verify(funcionarioRepository, times(1)).findById(funcionario.getId());
    }

    @Test
    void deveDeletarFuncionarioPorId() {
        // Act
        funcionarioRepository.deleteById(funcionario.getId());

        // Assert
        verify(funcionarioRepository, times(1)).deleteById(funcionario.getId());
    }

    @Test
    void deveDeletarTodosFuncionarios() {
        // Act
        funcionarioRepository.deleteAll();

        // Assert
        verify(funcionarioRepository, times(1)).deleteAll();
    }

    @Test
    void deveVerificarSeFuncionarioEhValido() {
        // Act
        boolean valido = funcionario.checkIfValid();

        // Assert
        assertTrue(valido);
    }
}
