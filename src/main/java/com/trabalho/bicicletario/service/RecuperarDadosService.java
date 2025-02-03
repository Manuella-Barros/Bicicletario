package com.trabalho.bicicletario.service;
import com.trabalho.bicicletario.exception.CustomException;
import com.trabalho.bicicletario.model.*;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class RecuperarDadosService {
    private final CartaoService cartaoService;
    private final PassaporteService passaporteService;
    CiclistaService ciclistaService;
    FuncionarioService funcionarioService;
    AluguelService aluguelService;

    public RecuperarDadosService(CiclistaService ciclistaService, FuncionarioService funcionarioService, AluguelService aluguelService, CartaoService cartaoService, PassaporteService passaporteService) {
        this.ciclistaService = ciclistaService;
        this.funcionarioService = funcionarioService;
        this.aluguelService = aluguelService;
        this.cartaoService = cartaoService;
        this.passaporteService = passaporteService;
    }

    public void recuperarDados() throws IOException, CustomException {
        ciclistaService.recuperarDados();
        funcionarioService.recuperarDados();
        Aluguel[] alugueis = aluguelService.recuperarDados();

        for (Aluguel aluguel : alugueis) {
            aluguelService.createMockAluguel(aluguel);
        }
    }

    public Iterable<Ciclista> getCiclistas(){
        return ciclistaService.pegaTodos();
    }

    public Iterable<Cartao> getCartoes(){
        return cartaoService.pegaTodos();
    }

    public Iterable<Funcionario> getFuncionarios(){
        return funcionarioService.pegaTodos();
    }

    public Iterable<Aluguel> getAlugueis(){
        return aluguelService.pegaTodos();
    }

    public Iterable<Passaporte> getPassaportes(){
        return passaporteService.pegaTodos();
    }
}
