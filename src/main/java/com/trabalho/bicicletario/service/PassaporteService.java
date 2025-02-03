package com.trabalho.bicicletario.service;

import com.trabalho.bicicletario.exception.CustomException;
import com.trabalho.bicicletario.model.Ciclista;
import com.trabalho.bicicletario.model.Enum.ErrorEnum;
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
            return null;
        }

        Optional<Passaporte> optionalPassaporte = passaporteRepository.findById( id );

        if(!optionalPassaporte.isPresent()){
            throw new CustomException(ErrorEnum.REQUISICAO_MAL_FORMADA);
        }

        return ResponseEntity.ok(optionalPassaporte.get());
    }

    public ResponseEntity<Passaporte> updatePassaporte(int id, Passaporte updatePassaporte) throws CustomException {
        if(updatePassaporte == null){
            return null;
        }

        if(!updatePassaporte.checkIfValid()){
            throw new CustomException(ErrorEnum.DADOS_INVALIDOS);
        }

        if(id == 0 && updatePassaporte != null){
            Passaporte updatedPassaporte = passaporteRepository.save( updatePassaporte );
            return ResponseEntity.ok(updatedPassaporte);
        }

        if(id == 0){
            throw new CustomException(ErrorEnum.DADOS_INVALIDOS);
        }

        if(!passaporteRepository.existsById(id)){
            throw new CustomException(ErrorEnum.NAO_ENCONTRADO);
        }

        updatePassaporte.setId(id);
        Passaporte updatedPassaporte = passaporteRepository.save( updatePassaporte );

        return ResponseEntity.ok(updatedPassaporte);
    }

    public void deleteAllPassaportes() {
        this.passaporteRepository.deleteAll();
    }

    public Iterable<Passaporte> pegaTodos(){
        return passaporteRepository.findAll();
    }
}
