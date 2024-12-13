package com.trabalho.bicicletario.repository;

import com.trabalho.bicicletario.model.Funcionario;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FuncionarioRepository extends CrudRepository<Funcionario, Integer> { }
