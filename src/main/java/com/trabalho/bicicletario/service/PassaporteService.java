package com.trabalho.bicicletario.service;

import com.trabalho.bicicletario.model.Ciclista;
import com.trabalho.bicicletario.model.Passaporte;
import com.trabalho.bicicletario.repository.PassaporteRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PassaporteService {
    PassaporteRepository passaporteRepository;

    public PassaporteService(PassaporteRepository passaporteRepository) {
        this.passaporteRepository = passaporteRepository;
    }

    public ResponseEntity<Passaporte> createPassaporte(Passaporte passaporte ) {
        if(!passaporte.checkIfValid()){ // TODO - DADOS INVALIDOS - 422
            return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
        }

        Passaporte createdPassaporte = passaporteRepository.save(passaporte);
        return ResponseEntity.ok(createdPassaporte);
    }

    public ResponseEntity<Passaporte> getPassaporteById(int id) {
        if(id <= 0){ // TODO - ERRO ID INVÁLIDO - 422
            return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
        }

        Optional<Passaporte> optionalPassaporte = passaporteRepository.findById( id );

        if(!optionalPassaporte.isPresent()){ // TODO - NÃO ENCONTRADO - 404
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(optionalPassaporte.get());
    }

}
