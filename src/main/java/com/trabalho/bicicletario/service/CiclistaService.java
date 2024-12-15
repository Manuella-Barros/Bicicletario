package com.trabalho.bicicletario.service;

import com.trabalho.bicicletario.model.Ciclista;
import com.trabalho.bicicletario.model.StatusCiclista;
import com.trabalho.bicicletario.repository.CiclistaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CiclistaService {
    CiclistaRepository ciclistaRepository;

    public CiclistaService(CiclistaRepository ciclistaRepository) {
        this.ciclistaRepository = ciclistaRepository;
    }

    public ResponseEntity<Ciclista> createCiclista(Ciclista ciclista ) {
        ciclista.setStatus(StatusCiclista.AGUARDANDO_CONFIRMACAO.getDescricao());

        if(!ciclista.checkIfValid()){ // TODO - ERRO ALGUM CAMPO VAZIO - 422
            return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
        }

        Ciclista createdCiclista = ciclistaRepository.save( ciclista );
        return ResponseEntity.ok(createdCiclista);
    }

    public ResponseEntity<Ciclista> getCiclistaById(int id) {
        if(id <= 0){ // TODO - ERRO ID INVÁLIDO - 422
            return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
        }

        Optional<Ciclista> optionalCiclista = ciclistaRepository.findById( id );

        if(!optionalCiclista.isPresent()){ // TODO - NÃO ENCONTRADO - 404
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(optionalCiclista.get());
    }

    public ResponseEntity<Ciclista> updateCiclista(int id, Ciclista updateCiclista) {
        if(!updateCiclista.checkIfValid() || id <= 0){ // TODO - ERRO ALGUM CAMPO INVALIDO - 422
            return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
        }

        if(!this.getCiclistaById(id).hasBody()){ // TODO - NÃO ENCONTRADO - 404
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        updateCiclista.setId(id);
        Ciclista updatedCiclista = ciclistaRepository.save( updateCiclista );

        return ResponseEntity.ok(updatedCiclista);
    }

    public ResponseEntity<Boolean> emailExists(String email) {
        if(email == null){ // TODO - Email não enviado como parâmetro - 400
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return ResponseEntity.ok(ciclistaRepository.existsByEmail(email));
    }

    public ResponseEntity<Boolean> ciclistaExists(int id) {
        if(id <= 0){ // TODO - ERRO ID INVÁLIDO - 422
            return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
        }

        Optional<Ciclista> optionalCiclista = ciclistaRepository.findById( id );

        if(!optionalCiclista.isPresent()){ // TODO - NÃO ENCONTRADO - 404
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok().build();
    }

    public ResponseEntity<Ciclista> ativarCadastro(int id) {
        if(id <= 0){ // TODO - ERRO ID INVÁLIDO - 422
            return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
        }

        Optional<Ciclista> optionalCiclista = ciclistaRepository.findById( id );

        if(!optionalCiclista.isPresent()){ // TODO - NÃO ENCONTRADO - 404
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        optionalCiclista.get().setStatus(StatusCiclista.ATIVO.getDescricao());

        Ciclista updatedCiclista = ciclistaRepository.save( optionalCiclista.get() );

        return ResponseEntity.ok(updatedCiclista);
    }
}
