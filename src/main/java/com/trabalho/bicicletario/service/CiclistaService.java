package com.trabalho.bicicletario.service;

import com.trabalho.bicicletario.exception.CustomException;
import com.trabalho.bicicletario.model.Ciclista;
import com.trabalho.bicicletario.model.ErrorEnum;
import com.trabalho.bicicletario.model.StatusCiclistaEnum;
import com.trabalho.bicicletario.repository.CiclistaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CiclistaService {
    CiclistaRepository ciclistaRepository;

    public CiclistaService(CiclistaRepository ciclistaRepository) {
        this.ciclistaRepository = ciclistaRepository;
    }

    public ResponseEntity<Ciclista> createCiclista(Ciclista ciclista ) throws CustomException {
        ciclista.setStatus(StatusCiclistaEnum.AGUARDANDO_CONFIRMACAO.getDescricao());

        if(!ciclista.checkIfValid()){
            throw new CustomException(ErrorEnum.DADOS_INVALIDOS);
        }

        Ciclista createdCiclista = ciclistaRepository.save( ciclista );
        return ResponseEntity.ok(createdCiclista);
    }

    public ResponseEntity<Ciclista> getCiclistaById(int id) throws CustomException {
        if(id <= 0){
            throw new CustomException(ErrorEnum.DADOS_INVALIDOS);
        }

        Optional<Ciclista> optionalCiclista = ciclistaRepository.findById( id );

        if(!optionalCiclista.isPresent()){
            throw new CustomException(ErrorEnum.REQUISICAO_MAL_FORMADA);
        }

        return ResponseEntity.ok(optionalCiclista.get());
    }

    public ResponseEntity<Ciclista> updateCiclista(int id, Ciclista updateCiclista) throws CustomException {
        if(!updateCiclista.checkIfValid() || id <= 0){
            throw new CustomException(ErrorEnum.DADOS_INVALIDOS);
        }

        Optional<Ciclista> optionalCiclista = ciclistaRepository.findById( id );

        if(!optionalCiclista.isPresent()){
            throw new CustomException(ErrorEnum.REQUISICAO_MAL_FORMADA);
        }

        updateCiclista.setId(optionalCiclista.get().getId());
        updateCiclista.setStatus(optionalCiclista.get().getStatus());
        updateCiclista.setIdCartao(optionalCiclista.get().getIdCartao());
        updateCiclista.setIdPassaporte(optionalCiclista.get().getIdPassaporte());

        Ciclista updatedCiclista = ciclistaRepository.save( updateCiclista );

        return ResponseEntity.ok(updatedCiclista);
    }

    public ResponseEntity<Boolean> emailExists(String email) throws CustomException {
        if(email == null){
            throw new CustomException(ErrorEnum.EMAIL_VAZIO);
        }

        return ResponseEntity.ok(ciclistaRepository.existsByEmail(email));
    }

    public ResponseEntity<Boolean> ciclistaExists(int id) throws CustomException {
        if(id <= 0){
            throw new CustomException(ErrorEnum.DADOS_INVALIDOS);
        }

        Optional<Ciclista> optionalCiclista = ciclistaRepository.findById( id );

        if(!optionalCiclista.isPresent()){
            throw new CustomException(ErrorEnum.REQUISICAO_MAL_FORMADA);
        }

        return ResponseEntity.ok().build();
    }

    public ResponseEntity<Ciclista> ativarCadastro(int id) throws CustomException {
        if(id <= 0){
            throw new CustomException(ErrorEnum.DADOS_INVALIDOS);
        }

        Optional<Ciclista> optionalCiclista = ciclistaRepository.findById( id );

        if(!optionalCiclista.isPresent()){
            throw new CustomException(ErrorEnum.REQUISICAO_MAL_FORMADA);
        }

        optionalCiclista.get().setStatus(StatusCiclistaEnum.ATIVO.getDescricao());

        Ciclista updatedCiclista = ciclistaRepository.save( optionalCiclista.get() );

        return ResponseEntity.ok(updatedCiclista);
    }

    public boolean isCiclistaAtivo(int id) throws CustomException {
        ResponseEntity<Ciclista> ciclista = this.getCiclistaById(id);

        return ciclista.getBody().getStatus().equals(StatusCiclistaEnum.ATIVO.getDescricao());
    }
}
