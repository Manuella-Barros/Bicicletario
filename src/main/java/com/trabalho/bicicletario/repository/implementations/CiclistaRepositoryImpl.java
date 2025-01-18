package com.trabalho.bicicletario.repository.implementations;

import com.trabalho.bicicletario.model.Ciclista;
import com.trabalho.bicicletario.repository.CiclistaRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class CiclistaRepositoryImpl {

    EntityManager entityManager;

    public CiclistaRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public <S extends Ciclista> S save(S entity) {
        String sql = "INSERT INTO ciclista (id, nome, data_nascimento, cpf, nacionalidade, id_passaporte, id_cartao, email, status, url_foto_documento, senha) " +
                "VALUES (:id, :nome, :data_nascimento, :cpf, :nacionalidade, :id_passaporte, :id_cartao, :email, :status, :url_foto_documento, :senha)";
        Query query = entityManager.createNativeQuery(sql);
        query.setParameter("nome", entity.getNome());
        query.setParameter("data_nascimento", entity.getNascimento());
        query.setParameter("cpf", entity.getCpf());
        query.setParameter("nacionalidade", entity.getNacionalidade());
        query.setParameter("id_passaporte", entity.getIdPassaporte());
        query.setParameter("id_cartao", entity.getIdCartao());
        query.setParameter("email", entity.getEmail());
        query.setParameter("status", entity.getStatus());
        query.setParameter("url_foto_documento", entity.getUrlFotoDocumento());
        query.setParameter("senha", entity.getSenha());
        query.setParameter("id", entity.getId());

        query.executeUpdate();
        return entity;
    }
}
