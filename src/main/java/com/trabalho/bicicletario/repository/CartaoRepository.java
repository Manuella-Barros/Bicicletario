package com.trabalho.bicicletario.repository;

import com.trabalho.bicicletario.model.Ciclista;
import org.springframework.data.repository.CrudRepository;

public interface CartaoRepository extends CrudRepository<Ciclista, Integer> { }