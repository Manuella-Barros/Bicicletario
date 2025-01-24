package com.trabalho.bicicletario.controller;

import com.trabalho.bicicletario.exception.CustomException;
import com.trabalho.bicicletario.service.RecuperarDadosService;
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
}
