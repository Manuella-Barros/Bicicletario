package com.trabalho.bicicletario.repository;

import com.trabalho.bicicletario.model.Aluguel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AluguelRepository extends CrudRepository<Aluguel, Integer> {
    Aluguel findByCiclista(int id);
    Aluguel findByBicicleta(int id);
}