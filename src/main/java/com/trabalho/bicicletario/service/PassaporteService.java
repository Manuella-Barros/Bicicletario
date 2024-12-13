package com.trabalho.bicicletario.service;

import com.trabalho.bicicletario.model.Passaporte;
import com.trabalho.bicicletario.repository.PassaporteRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

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
}
