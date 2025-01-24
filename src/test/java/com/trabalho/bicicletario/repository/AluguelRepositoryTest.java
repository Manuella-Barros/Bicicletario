package com.trabalho.bicicletario.repository;

import com.trabalho.bicicletario.model.Aluguel;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AluguelRepositoryTest {

    @Mock
    private AluguelRepository aluguelRepository;

    @Test
    void deveSalvarEEncontrarAluguelPorCiclista() {
        Aluguel aluguel = new Aluguel();
        aluguel.setCiclista(1);
        aluguel.setBicicleta(2);
        aluguel.setTrancaInicio(3);
        aluguel.setTrancaFim(4);
        aluguel.setHoraInicio(LocalDateTime.now());
        aluguel.setHoraFim(LocalDateTime.now().plusHours(1));

        when(aluguelRepository.findByCiclista(1)).thenReturn(aluguel);

        Aluguel encontrado = aluguelRepository.findByCiclista(1);

        assertNotNull(encontrado);
        assertEquals(1, encontrado.getCiclista());
        verify(aluguelRepository).findByCiclista(1);
    }

    @Test
    void deveSalvarEEncontrarAluguelPorBicicleta() {
        Aluguel aluguel = new Aluguel();
        aluguel.setCiclista(1);
        aluguel.setBicicleta(2);
        aluguel.setTrancaInicio(3);
        aluguel.setTrancaFim(4);
        aluguel.setHoraInicio(LocalDateTime.now());
        aluguel.setHoraFim(LocalDateTime.now().plusHours(1));

        when(aluguelRepository.findByBicicleta(2)).thenReturn(aluguel);

        Aluguel encontrado = aluguelRepository.findByBicicleta(2);

        assertNotNull(encontrado);
        assertEquals(2, encontrado.getBicicleta());
        verify(aluguelRepository).findByBicicleta(2);
    }

    @Test
    void deveSalvarEEncontrarAluguelPorBicicletaETrancaInicio() {
        Aluguel aluguel = new Aluguel();
        aluguel.setCiclista(1);
        aluguel.setBicicleta(2);
        aluguel.setTrancaInicio(3);
        aluguel.setTrancaFim(4);
        aluguel.setHoraInicio(LocalDateTime.now());
        aluguel.setHoraFim(LocalDateTime.now().plusHours(1));

        when(aluguelRepository.findByBicicletaAndTrancaInicio(2, 3))
                .thenReturn(new Aluguel[]{aluguel});

        Aluguel[] encontrados = aluguelRepository.findByBicicletaAndTrancaInicio(2, 3);

        assertNotNull(encontrados);
        assertEquals(1, encontrados.length);
        assertEquals(2, encontrados[0].getBicicleta());
        assertEquals(3, encontrados[0].getTrancaInicio());
        verify(aluguelRepository).findByBicicletaAndTrancaInicio(2, 3);
    }

    @Test
    void deveSalvarEEncontrarTodosOsAlugueisPorCiclista() {
        Aluguel aluguel1 = new Aluguel();
        aluguel1.setCiclista(1);
        aluguel1.setBicicleta(2);
        aluguel1.setTrancaInicio(3);
        aluguel1.setTrancaFim(4);
        aluguel1.setHoraInicio(LocalDateTime.now());
        aluguel1.setHoraFim(LocalDateTime.now().plusHours(1));

        Aluguel aluguel2 = new Aluguel();
        aluguel2.setCiclista(1);
        aluguel2.setBicicleta(5);
        aluguel2.setTrancaInicio(6);
        aluguel2.setTrancaFim(7);
        aluguel2.setHoraInicio(LocalDateTime.now());
        aluguel2.setHoraFim(LocalDateTime.now().plusHours(2));

        Aluguel[] aluguels = {aluguel1, aluguel2};

        when(aluguelRepository.findAllByCiclista(1))
                .thenReturn(aluguels);

        Aluguel[] encontrados = aluguelRepository.findAllByCiclista(1);

        assertNotNull(encontrados);
        assertEquals(2, encontrados.length);
        assertEquals(2, encontrados[0].getBicicleta());
        assertEquals(1, encontrados[1].getCiclista());
        verify(aluguelRepository).findAllByCiclista(1);
    }
}
