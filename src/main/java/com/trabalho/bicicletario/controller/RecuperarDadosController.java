package com.trabalho.bicicletario.controller;

import com.trabalho.bicicletario.exception.CustomException;
import com.trabalho.bicicletario.model.*;
import com.trabalho.bicicletario.service.RecuperarDadosService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController()
@RequestMapping("/restaurarBanco")
public class RecuperarDadosController {
    RecuperarDadosService recuperarDadosService;

    public RecuperarDadosController(RecuperarDadosService recuperarDadosService) {
        this.recuperarDadosService = recuperarDadosService;
    }

    @GetMapping("")
    public void recuperarDados() throws IOException, CustomException {
        recuperarDadosService.recuperarDados();
    }

    @GetMapping("/ciclista")
    public ResponseEntity<Iterable<Ciclista>> recuperarCiclistas(){
        return ResponseEntity.ok(recuperarDadosService.getCiclistas());
    }
    @GetMapping("/cartao")
    public ResponseEntity<Iterable<Cartao>> recuperarCartoes(){
        return ResponseEntity.ok(recuperarDadosService.getCartoes());
    }
    @GetMapping("/funcionarios")
    public ResponseEntity<Iterable<Funcionario>> recuperarFuncionarios(){
        return ResponseEntity.ok(recuperarDadosService.getFuncionarios());
    }
    @GetMapping("/alugueis")
    public ResponseEntity<Iterable<Aluguel>> recuperarAlugueis(){
        return ResponseEntity.ok(recuperarDadosService.getAlugueis());
    }
    @GetMapping("/passaportes")
    public ResponseEntity<Iterable<Passaporte>> recuperarpassaportes(){
        return ResponseEntity.ok(recuperarDadosService.getPassaportes());
    }
}
