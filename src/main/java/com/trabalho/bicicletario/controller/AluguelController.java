package com.trabalho.bicicletario.controller;

import com.trabalho.bicicletario.dto.RealizarAluguelDTO;
import com.trabalho.bicicletario.dto.RealizarDevolucaoDTO;
import com.trabalho.bicicletario.exception.CustomException;
import com.trabalho.bicicletario.model.*;
import com.trabalho.bicicletario.service.AluguelService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("")
public class AluguelController {
    AluguelService aluguelService;

    public AluguelController(AluguelService aluguelService) {
        this.aluguelService = aluguelService;
    }

    @PostMapping("/aluguel")
    public ResponseEntity<Aluguel> realizarAluguel(@RequestBody RealizarAluguelDTO newAluguelDTO) throws CustomException {
        try{
            Aluguel newAluguel = new Aluguel(newAluguelDTO.getCiclista(), newAluguelDTO.getTrancaInicio(), 0);

            ResponseEntity<Aluguel> aluguel = aluguelService.createAluguel(newAluguel);

            return ResponseEntity.ok(aluguel.getBody());
        } catch (CustomException e) {
            throw new CustomException(e);
        }
    }

    @PostMapping("/devolucao")
    public ResponseEntity<Aluguel> realizarDevolucao(@RequestBody RealizarDevolucaoDTO newDevolucaoDTO) throws CustomException {
        try{
            Aluguel devolucao = new Aluguel(0, newDevolucaoDTO.getIdTranca(), newDevolucaoDTO.getIdBicicleta());
            ResponseEntity<Aluguel> aluguel = aluguelService.postDevolucao(devolucao);

            return ResponseEntity.ok(aluguel.getBody());
        } catch (CustomException e) {
            throw new CustomException(e);
        }}
}