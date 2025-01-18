package com.trabalho.bicicletario.service;

import com.trabalho.bicicletario.dto.response.CiclistaResponseDTO;
import com.trabalho.bicicletario.exception.CustomException;
import com.trabalho.bicicletario.model.Aluguel;
import com.trabalho.bicicletario.model.Ciclista;
import org.springframework.http.ResponseEntity;
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
        ciclistaService.recuperarDados();
        funcionarioService.recuperarDados();
        Aluguel[] alugueis = aluguelService.recuperarDados();

        for (Aluguel aluguel : alugueis) {
            ResponseEntity<CiclistaResponseDTO> ciclistaDTO = ciclistaService.getCiclistaById(aluguel.getCiclista());
            Ciclista ciclista = new Ciclista(ciclistaDTO.getBody());
            aluguelService.createAluguel(aluguel, ciclista);
        }
    }
}
