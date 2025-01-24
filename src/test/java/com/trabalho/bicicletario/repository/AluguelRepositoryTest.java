package com.trabalho.bicicletario.repository;

import com.trabalho.bicicletario.model.Aluguel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;


@DataJpaTest
public class AluguelRepositoryTest {

    @Autowired
    private AluguelRepository aluguelRepository;

    private Aluguel aluguel;

    @BeforeEach
    public void setUp() {
        aluguel = new Aluguel();
        aluguel.setCiclista(1);
        aluguel.setBicicleta(101);
        aluguel.setTrancaInicio(1001);
        aluguelRepository.save(aluguel);
    }

    @Test
    public void testFindByCiclista() {
        Aluguel found = aluguelRepository.findByCiclista(1);
        assertNotNull(found);
        assertEquals(1, found.getCiclista());
    }

    @Test
    public void testFindByBicicleta() {
        Aluguel found = aluguelRepository.findByBicicleta(101);
        assertNotNull(found);
        assertEquals(101, found.getBicicleta());
    }

    @Test
    public void testFindByBicicletaAndTrancaInicio() {
        Aluguel[] found = aluguelRepository.findByBicicletaAndTrancaInicio(101, 1001);
        assertNotNull(found);
        assertEquals(1, found.length);
        assertEquals(101, found[0].getBicicleta());
        assertEquals(1001, found[0].getTrancaInicio());
    }

    @Test
    public void testFindAllByCiclista() {
        Aluguel[] found = aluguelRepository.findAllByCiclista(1);
        assertNotNull(found);
        assertTrue(found.length > 0);
        assertEquals(1, found[0].getCiclista());
    }
}