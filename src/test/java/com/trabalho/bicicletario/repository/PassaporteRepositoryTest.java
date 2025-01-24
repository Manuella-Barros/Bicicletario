package com.trabalho.bicicletario.repository;

import com.trabalho.bicicletario.model.Passaporte;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PassaporteRepositoryTest {

    @Mock
    private PassaporteRepository passaporteRepository;

    private Passaporte passaporte;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Criando um objeto Passaporte para os testes
        passaporte = new Passaporte();
        passaporte.setId(1);
        passaporte.setNumero("AB123456");
        passaporte.setValidade(LocalDate.of(2030, 12, 31));
        passaporte.setPais("BR");
    }

    @Test
    void deveSalvarPassaporteComSucesso() {
        // Arrange
        when(passaporteRepository.save(passaporte)).thenReturn(passaporte);

        // Act
        Passaporte salvo = passaporteRepository.save(passaporte);

        // Assert
        assertNotNull(salvo);
        assertEquals(passaporte.getId(), salvo.getId());
        verify(passaporteRepository, times(1)).save(passaporte);
    }

    @Test
    void deveEncontrarPassaportePorId() {
        // Arrange
        when(passaporteRepository.findById(passaporte.getId())).thenReturn(Optional.of(passaporte));

        // Act
        Optional<Passaporte> encontrado = passaporteRepository.findById(passaporte.getId());

        // Assert
        assertTrue(encontrado.isPresent());
        assertEquals(passaporte.getNumero(), encontrado.get().getNumero());
        verify(passaporteRepository, times(1)).findById(passaporte.getId());
    }

    @Test
    void deveDeletarPassaportePorId() {
        // Act
        passaporteRepository.deleteById(passaporte.getId());

        // Assert
        verify(passaporteRepository, times(1)).deleteById(passaporte.getId());
    }

    @Test
    void deveDeletarTodosOsPassaportes() {
        // Act
        passaporteRepository.deleteAll();

        // Assert
        verify(passaporteRepository, times(1)).deleteAll();
    }

    @Test
    void deveVerificarSePassaporteEhValido() {
        // Act
        boolean valido = passaporte.checkIfValid();

        // Assert
        assertTrue(valido);
    }

    @Test
    void deveRetornarPassaporteInvalidoQuandoPaisInvalido() {
        // Arrange
        passaporte.setPais("BRA"); // País inválido (deve ser de 2 caracteres)

        // Act
        boolean valido = passaporte.checkIfValid();

        // Assert
        assertFalse(valido);
    }
}
