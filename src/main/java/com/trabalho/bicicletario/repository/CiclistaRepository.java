package com.trabalho.bicicletario.repository;

import com.trabalho.bicicletario.model.Ciclista;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CiclistaRepository extends CrudRepository<Ciclista, Integer> {
    boolean existsByEmail (String email);
}