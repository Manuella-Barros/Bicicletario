package com.trabalho.bicicletario.repository;

import com.trabalho.bicicletario.model.Cartao;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartaoRepository extends CrudRepository<Cartao, Integer> {
}
