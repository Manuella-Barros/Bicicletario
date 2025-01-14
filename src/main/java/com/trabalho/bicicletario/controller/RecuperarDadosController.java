package com.trabalho.bicicletario.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.trabalho.bicicletario.dto.recuperarDados.RecuperarDadosCiclistaDTO;
import com.trabalho.bicicletario.exception.CustomException;
import com.trabalho.bicicletario.model.Ciclista;
import com.trabalho.bicicletario.model.Funcionario;
import com.trabalho.bicicletario.service.RecuperarDadosService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/restaurarDados")
public class RecuperarDadosController {
    RecuperarDadosService recuperarDadosService;

    public RecuperarDadosController(RecuperarDadosService recuperarDadosService) {
        this.recuperarDadosService = recuperarDadosService;
    }

    @GetMapping("")
    public void recuperarDados() throws JsonProcessingException, CustomException {
        recuperarDadosService.recuperarDados();
    }
}
