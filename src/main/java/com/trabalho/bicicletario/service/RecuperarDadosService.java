package com.trabalho.bicicletario.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.trabalho.bicicletario.dto.CadastrarCiclistaDTO;
import com.trabalho.bicicletario.dto.CiclistaDTO;
import com.trabalho.bicicletario.dto.recuperarDados.RecuperarDadosCiclistaDTO;
import com.trabalho.bicicletario.exception.CustomException;
import com.trabalho.bicicletario.model.*;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public void recuperarDados() throws JsonProcessingException, CustomException {
        ciclistaService.recuperarDados(); // feito
        funcionarioService.recuperarDados(); // feito
//        aluguelService.recuperarDados();
    }
}
