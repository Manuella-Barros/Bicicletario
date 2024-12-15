package com.trabalho.bicicletario.service;

import com.trabalho.bicicletario.exception.CustomException;
import com.trabalho.bicicletario.model.ErrorEnum;
import com.trabalho.bicicletario.model.Passaporte;
import com.trabalho.bicicletario.repository.PassaporteRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PassaporteService {
    PassaporteRepository passaporteRepository;

    public PassaporteService(PassaporteRepository passaporteRepository) {
        this.passaporteRepository = passaporteRepository;
    }

    public ResponseEntity<Passaporte> createPassaporte(Passaporte passaporte ) throws CustomException {
        if(!passaporte.checkIfValid()){
            throw new CustomException(ErrorEnum.DADOS_INVALIDOS);
        }

        Passaporte createdPassaporte = passaporteRepository.save(passaporte);
        return ResponseEntity.ok(createdPassaporte);
    }

    public ResponseEntity<Passaporte> getPassaporteById(int id) throws CustomException {
        if(id <= 0){
            throw new CustomException(ErrorEnum.DADOS_INVALIDOS);
        }

        Optional<Passaporte> optionalPassaporte = passaporteRepository.findById( id );

        if(!optionalPassaporte.isPresent()){
            throw new CustomException(ErrorEnum.REQUISICAO_MAL_FORMADA);
        }

        return ResponseEntity.ok(optionalPassaporte.get());
    }

}
