package com.trabalho.bicicletario.repository;

import com.trabalho.bicicletario.model.Passaporte;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PassaporteRepository extends CrudRepository<Passaporte, Integer> { }