package com.trabalho.bicicletario.service;

import com.trabalho.bicicletario.exception.CustomException;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class RecuperarDadosService {
    CiclistaService ciclistaService;
    FuncionarioService funcionarioService;
    AluguelService aluguelService;

    public RecuperarDadosService(CiclistaService ciclistaService, FuncionarioService funcionarioService, AluguelService aluguelService) {
        this.ciclistaService = ciclistaService;
        this.funcionarioService = funcionarioService;
        this.aluguelService = aluguelService;
    }

    public void recuperarDados() throws IOException, CustomException {
        ciclistaService.recuperarDados(); // feito
        funcionarioService.recuperarDados(); // feito
//        aluguelService.recuperarDados();
    }
}
