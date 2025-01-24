package com.trabalho.bicicletario.repository;

import com.trabalho.bicicletario.model.Ciclista;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CiclistaRepositoryTest {

    @Mock
    private CiclistaRepository ciclistaRepository;

    private Ciclista ciclista;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        ciclista = new Ciclista();
        ciclista.setId(1);
        ciclista.setNome("João Silva");
        ciclista.setCpf("12345678901");
        ciclista.setNacionalidade("brasileira");
        ciclista.setEmail("joao.silva@email.com");
        ciclista.setSenha("senha123");
        ciclista.setNascimento(LocalDate.of(1990, 1, 1));
    }

    @Test
    void deveVerificarSeCiclistaExistePorEmail() {
        // Arrange
        when(ciclistaRepository.existsByEmail(ciclista.getEmail())).thenReturn(true);

        // Act
        boolean existe = ciclistaRepository.existsByEmail(ciclista.getEmail());

        // Assert
        assertTrue(existe);
        verify(ciclistaRepository, times(1)).existsByEmail(ciclista.getEmail());
    }

    @Test
    void deveSalvarCiclistaComSucesso() {
        // Arrange
        when(ciclistaRepository.save(ciclista)).thenReturn(ciclista);

        // Act
        Ciclista salvo = ciclistaRepository.save(ciclista);

        // Assert
        assertNotNull(salvo);
        assertEquals(ciclista.getId(), salvo.getId());
        verify(ciclistaRepository, times(1)).save(ciclista);
    }

    @Test
    void deveEncontrarCiclistaPorId() {
        // Arrange
        when(ciclistaRepository.findById(ciclista.getId())).thenReturn(Optional.of(ciclista));

        // Act
        Optional<Ciclista> encontrado = ciclistaRepository.findById(ciclista.getId());

        // Assert
        assertTrue(encontrado.isPresent());
        assertEquals(ciclista.getId(), encontrado.get().getId());
        verify(ciclistaRepository, times(1)).findById(ciclista.getId());
    }

    @Test
    void deveDeletarTodosOsCiclistas() {
        // Act
        ciclistaRepository.deleteAll();

        // Assert
        verify(ciclistaRepository, times(1)).deleteAll();
    }
}
