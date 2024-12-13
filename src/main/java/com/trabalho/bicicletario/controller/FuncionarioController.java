package com.trabalho.bicicletario.controller;

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
    public ResponseEntity<Funcionario> cadastrarFuncionario(@RequestBody Funcionario newFuncionario) {
        ResponseEntity<Funcionario> funcionario = funcionarioService.createFuncionario(newFuncionario);

        if(funcionario.getStatusCode() != HttpStatus.OK) {
            return new ResponseEntity<>(funcionario.getStatusCode());
        }

        return ResponseEntity.ok(funcionario.getBody());
    }

    @GetMapping("/{idFuncionario}")
    public ResponseEntity<Funcionario> recuperarFuncionario(@PathVariable int idFuncionario) {
        ResponseEntity<Funcionario> funcionario = funcionarioService.getFuncionarioById(idFuncionario);

        if(funcionario.getStatusCode() != HttpStatus.OK) {
            return new ResponseEntity<>(funcionario.getStatusCode());
        }

        return ResponseEntity.ok(funcionario.getBody());
    }

    @PutMapping("/{idFuncionario}")
    public ResponseEntity<Funcionario> editarFuncionario(@PathVariable int idFuncionario, @RequestBody Funcionario updateFuncionario) {
        ResponseEntity<Funcionario> funcionario = funcionarioService.updateFuncionario(idFuncionario, updateFuncionario);

        if(funcionario.getStatusCode() != HttpStatus.OK) {
            return new ResponseEntity<>(funcionario.getStatusCode());
        }

        return ResponseEntity.ok(funcionario.getBody());
    }

    @DeleteMapping("/{idFuncionario}")
    public ResponseEntity removerFuncionario(@PathVariable int idFuncionario) {
        ResponseEntity funcionario = funcionarioService.removeFuncionario(idFuncionario);

        if(funcionario.getStatusCode() != HttpStatus.OK) {
            return new ResponseEntity<>(funcionario.getStatusCode());
        }

        return ResponseEntity.ok().build();
    }
}
