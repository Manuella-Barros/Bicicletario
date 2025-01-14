package com.trabalho.bicicletario.repository;

import com.trabalho.bicicletario.model.Ciclista;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface CiclistaRepository extends CrudRepository<Ciclista, Integer> {
    boolean existsByEmail (String email);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO bancoaluguel.ciclista (id, nome, data_nascimento, cpf, nacionalidade, id_passaporte, id_cartao, email, status, url_foto_documento, senha) VALUES (:id, :nome, :data_nascimento, :cpf, :nacionalidade, :id_passaporte, :id_cartao, :email, :status, :url_foto_documento, :senha);", nativeQuery = true)
    void insertWithId(int id, String nome, LocalDate data_nascimento, String cpf, String nacionalidade, int id_passaporte, int id_cartao, String email, String status, String url_foto_documento, String senha);
}