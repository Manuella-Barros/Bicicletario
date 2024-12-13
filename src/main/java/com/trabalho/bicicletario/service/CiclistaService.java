package com.trabalho.bicicletario.service;

import com.trabalho.bicicletario.model.Ciclista;
import com.trabalho.bicicletario.model.StatusCiclista;
import com.trabalho.bicicletario.repository.CiclistaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class CiclistaService {
    CiclistaRepository ciclistaRepository;

    public CiclistaService(CiclistaRepository ciclistaRepository) {
        this.ciclistaRepository = ciclistaRepository;
    }

    public ResponseEntity<Ciclista> createCiclista(Ciclista ciclista ) {
        ciclista.setStatus(StatusCiclista.INATIVO.getDescricao());

        if(!ciclista.checkIfValid()){ // TODO - ERRO ALGUM CAMPO VAZIO - 422
            return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
        }

        Ciclista createdCiclista = ciclistaRepository.save( ciclista );
        return ResponseEntity.ok(createdCiclista);
    }

}
