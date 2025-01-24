package com.trabalho.bicicletario.repository;

import com.trabalho.bicicletario.model.Cartao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class CartaoRepositoryTest {

    @Mock
    private CartaoRepository cartaoRepository;

    @InjectMocks
    private CartaoRepositoryTest cartaoRepositoryTest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deveSalvarCartao() {
        // Arrange
        Cartao cartao = new Cartao();
        cartao.setId(1);
        cartao.setNumero("1234567890");
        when(cartaoRepository.save(cartao)).thenReturn(cartao);

        // Act
        Cartao salvo = cartaoRepository.save(cartao);

        // Assert
        assertEquals(cartao.getId(), salvo.getId());
        assertEquals(cartao.getNumero(), salvo.getNumero());
        verify(cartaoRepository, times(1)).save(cartao);
    }

    @Test
    void deveBuscarCartaoPorId() {
        // Arrange
        Cartao cartao = new Cartao();
        cartao.setId(1);
        cartao.setNumero("1234567890");
        when(cartaoRepository.findById(1)).thenReturn(Optional.of(cartao));

        // Act
        Optional<Cartao> encontrado = cartaoRepository.findById(1);

        // Assert
        assertTrue(encontrado.isPresent());
        assertEquals(cartao.getId(), encontrado.get().getId());
        assertEquals(cartao.getNumero(), encontrado.get().getNumero());
        verify(cartaoRepository, times(1)).findById(1);
    }

    @Test
    void deveExcluirCartao() {
        // Arrange
        int cartaoId = 1;
        doNothing().when(cartaoRepository).deleteById(cartaoId);

        // Act
        cartaoRepository.deleteById(cartaoId);

        // Assert
        verify(cartaoRepository, times(1)).deleteById(cartaoId);
    }
}
