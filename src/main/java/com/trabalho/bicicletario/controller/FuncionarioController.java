package com.trabalho.bicicletario.controller;

import com.trabalho.bicicletario.exception.CustomException;
import com.trabalho.bicicletario.model.Funcionario;
import com.trabalho.bicicletario.service.FuncionarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/funcionario")
public class FuncionarioController {
    FuncionarioService funcionarioService;

    public FuncionarioController(FuncionarioService funcionarioService) {
        this.funcionarioService = funcionarioService;
    }

    @PostMapping("")
    public ResponseEntity<Funcionario> cadastrarFuncionario(@RequestBody Funcionario newFuncionario) throws CustomException {
        try {
            ResponseEntity<Funcionario> funcionario = funcionarioService.createFuncionario(newFuncionario);

            return ResponseEntity.ok(funcionario.getBody());
        } catch (CustomException e) {
            throw new CustomException(e);
        }
    }

    @GetMapping("")
    public ResponseEntity<Iterable<Funcionario>> recuperarTodosOsFuncionarios() {
        ResponseEntity<Iterable<Funcionario>> funcionarios = funcionarioService.getFuncionarios();

        return ResponseEntity.ok(funcionarios.getBody());
    }

    @GetMapping("/{idFuncionario}")
    public ResponseEntity<Funcionario> recuperarFuncionario(@PathVariable int idFuncionario) throws CustomException {
        try{
            ResponseEntity<Funcionario> funcionario = funcionarioService.getFuncionarioById(idFuncionario);

            return ResponseEntity.ok(funcionario.getBody());
        } catch (CustomException e) {
            throw new CustomException(e);
        }
    }

    @PutMapping("/{idFuncionario}")
    public ResponseEntity<Funcionario> editarFuncionario(@PathVariable int idFuncionario, @RequestBody Funcionario updateFuncionario) throws CustomException {
        try{
            ResponseEntity<Funcionario> funcionario = funcionarioService.updateFuncionario(idFuncionario, updateFuncionario);

            if(funcionario.getStatusCode() != HttpStatus.OK) {
                return new ResponseEntity<>(funcionario.getStatusCode());
            }

            return ResponseEntity.ok(funcionario.getBody());
        } catch (CustomException e) {
            throw new CustomException(e);
        }
    }

    @DeleteMapping("/{idFuncionario}")
    public ResponseEntity<Void> removerFuncionario(@PathVariable int idFuncionario) throws CustomException {
        try{
            funcionarioService.removeFuncionario(idFuncionario);

            return ResponseEntity.ok().build();
        } catch (CustomException e) {
            throw new CustomException(e);
        }
    }
}
