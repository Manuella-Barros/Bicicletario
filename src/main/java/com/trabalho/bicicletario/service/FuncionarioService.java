package com.trabalho.bicicletario.service;

import com.trabalho.bicicletario.model.Funcionario;
import com.trabalho.bicicletario.repository.FuncionarioRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FuncionarioService {
    FuncionarioRepository funcionarioRepository;

    public FuncionarioService(FuncionarioRepository funcionarioRepository) {
        this.funcionarioRepository = funcionarioRepository;
    }

    public ResponseEntity<Funcionario> createFuncionario(Funcionario funcionario ) {
        if(!funcionario.checkIfValid()){ // TODO - ERRO ALGUM CAMPO VAZIO - 422
            return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
        }

        Funcionario createdFuncionario = funcionarioRepository.save( funcionario );
        return ResponseEntity.ok(createdFuncionario);
    }

    public ResponseEntity<Funcionario> getFuncionarioById(int id) {
        if(id <= 0){ // TODO - ERRO ID INVÁLIDO - 422
            return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
        }

        Optional<Funcionario> optionalFuncionario = funcionarioRepository.findById( id );

        if(!optionalFuncionario.isPresent()){ // TODO - NÃO ENCONTRADO - 404
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(optionalFuncionario.get());
    }

    public ResponseEntity<Funcionario> updateFuncionario(int id, Funcionario updateFuncionario) {
        if(!updateFuncionario.checkIfValid() || id <= 0){ // TODO - ERRO ALGUM CAMPO INVALIDO - 422
            return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
        }

        if(!this.getFuncionarioById(id).hasBody()){ // TODO - NÃO ENCONTRADO - 404
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        updateFuncionario.setId(id);
        this.createFuncionario(updateFuncionario);

        return ResponseEntity.ok(updateFuncionario);
    }

    public ResponseEntity removeFuncionario(int id) {
        if(id <= 0){ // TODO - Dados Inválidos - 422
            return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
        }

        if(!this.getFuncionarioById(id).hasBody()){ // TODO - NÃO ENCONTRADO - 404
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        funcionarioRepository.deleteById(id);

        return ResponseEntity.ok().build();
    }
}
