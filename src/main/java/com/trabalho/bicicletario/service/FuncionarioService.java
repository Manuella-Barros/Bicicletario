package com.trabalho.bicicletario.service;
import com.trabalho.bicicletario.exception.CustomException;
import com.trabalho.bicicletario.model.ErrorEnum;
import com.trabalho.bicicletario.model.Funcionario;
import com.trabalho.bicicletario.repository.FuncionarioRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FuncionarioService {
    FuncionarioRepository funcionarioRepository;

    public FuncionarioService(FuncionarioRepository funcionarioRepository) {
        this.funcionarioRepository = funcionarioRepository;
    }

    public ResponseEntity<Funcionario> createFuncionario(Funcionario funcionario ) throws CustomException {
        if(!funcionario.checkIfValid()){
            throw new CustomException(ErrorEnum.DADOS_INVALIDOS);
        }

        Funcionario createdFuncionario = funcionarioRepository.save( funcionario );
        return ResponseEntity.ok(createdFuncionario);
    }

    public ResponseEntity<Iterable<Funcionario>> getFuncionarios() {
        Iterable<Funcionario> iterableFuncionario = funcionarioRepository.findAll();

        return ResponseEntity.ok(iterableFuncionario);
    }

    public ResponseEntity<Funcionario> getFuncionarioById(int id) throws CustomException {
        if(id <= 0){
            throw new CustomException(ErrorEnum.DADOS_INVALIDOS);
        }

        Optional<Funcionario> optionalFuncionario = funcionarioRepository.findById( id );

        if(!optionalFuncionario.isPresent()){
            throw new CustomException(ErrorEnum.REQUISICAO_MAL_FORMADA);
        }

        return ResponseEntity.ok(optionalFuncionario.get());
    }

    public ResponseEntity<Funcionario> updateFuncionario(int id, Funcionario updateFuncionario) throws CustomException {
        if(!updateFuncionario.checkIfValid() || id <= 0){
            throw new CustomException(ErrorEnum.DADOS_INVALIDOS);
        }

        if(!this.getFuncionarioById(id).hasBody()){
            throw new CustomException(ErrorEnum.REQUISICAO_MAL_FORMADA);
        }

        updateFuncionario.setId(id);
        this.createFuncionario(updateFuncionario);

        return ResponseEntity.ok(updateFuncionario);
    }

    public ResponseEntity<Void> removeFuncionario(int id) throws CustomException {
        if(id <= 0){
            throw new CustomException(ErrorEnum.DADOS_INVALIDOS);
        }

        if(!this.getFuncionarioById(id).hasBody()){
            throw new CustomException(ErrorEnum.REQUISICAO_MAL_FORMADA);
        }

        funcionarioRepository.deleteById(id);

        return ResponseEntity.ok().build();
    }
}
