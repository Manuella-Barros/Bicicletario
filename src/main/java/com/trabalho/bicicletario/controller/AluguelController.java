package com.trabalho.bicicletario.controller;

import com.trabalho.bicicletario.dto.RealizarAluguelDTO;
import com.trabalho.bicicletario.dto.RealizarDevolucaoDTO;
import com.trabalho.bicicletario.model.Aluguel;
import com.trabalho.bicicletario.service.AluguelService;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<Aluguel> realizarAluguel(@RequestBody RealizarAluguelDTO newAluguelDTO) {
        Aluguel newAluguel = new Aluguel(newAluguelDTO.getCiclista(), newAluguelDTO.getTrancaInicio());
        ResponseEntity<Aluguel> aluguel = aluguelService.createAluguel(newAluguel);

        if(aluguel.getStatusCode() != HttpStatus.OK) {
            return new ResponseEntity<>(aluguel.getStatusCode());
        }

        return ResponseEntity.ok(aluguel.getBody());
    }

    @PostMapping("/devolucao")
    public ResponseEntity<Aluguel> realizarDevolucao(@RequestBody RealizarDevolucaoDTO newDevolucaoDTO) {
        ResponseEntity<Aluguel> aluguel = aluguelService.getAluguelByBicicletaId(newDevolucaoDTO.getIdBicicleta());

        if(aluguel.getStatusCode() != HttpStatus.OK) {
            return new ResponseEntity<>(aluguel.getStatusCode());
        }

        return ResponseEntity.ok(aluguel.getBody());
    }
}