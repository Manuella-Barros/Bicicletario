package com.trabalho.bicicletario.controller;

import com.trabalho.bicicletario.dto.RealizarAluguelDTO;
import com.trabalho.bicicletario.dto.RealizarDevolucaoDTO;
import com.trabalho.bicicletario.dto.response.CiclistaResponseDTO;
import com.trabalho.bicicletario.exception.CustomException;
import com.trabalho.bicicletario.model.*;
import com.trabalho.bicicletario.model.Enum.ErrorEnum;
import com.trabalho.bicicletario.model.integracoes.Email;
import com.trabalho.bicicletario.service.AluguelService;
import com.trabalho.bicicletario.service.CiclistaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("")
public class AluguelController {
    AluguelService aluguelService;
    CiclistaService ciclistaService;

    public AluguelController(AluguelService aluguelService, CiclistaService ciclistaService) {
        this.aluguelService = aluguelService;
        this.ciclistaService = ciclistaService;
    }

    @PostMapping("/aluguel")
    public ResponseEntity<Aluguel> realizarAluguel(@RequestBody RealizarAluguelDTO newAluguelDTO) throws CustomException {
        try{
            Aluguel newAluguel = new Aluguel(newAluguelDTO.getCiclista(), newAluguelDTO.getTrancaInicio(), 0);

            ResponseEntity<CiclistaResponseDTO> ciclistaDTO = ciclistaService.getCiclistaById(newAluguelDTO.getCiclista());
            Ciclista ciclista = new Ciclista(ciclistaDTO.getBody());

            if(!ciclistaService.isCiclistaAtivo(newAluguelDTO.getCiclista())){
                throw new CustomException(ErrorEnum.CICLISTA_INATIVO);
            }

            ResponseEntity<Aluguel> aluguel = aluguelService.createAluguel(newAluguel, ciclista);

            return ResponseEntity.ok(aluguel.getBody());
        } catch (CustomException e) {
            throw new CustomException(e);
        }
    }

    @PostMapping("/devolucao")
    public ResponseEntity<Aluguel> realizarDevolucao(@RequestBody RealizarDevolucaoDTO newDevolucaoDTO) throws CustomException {
        try{
            Aluguel devolucao = new Aluguel(0, newDevolucaoDTO.getIdTranca(), newDevolucaoDTO.getIdBicicleta());
            Email email = new Email();
            ResponseEntity<Aluguel> aluguel = aluguelService.postDevolucao(devolucao);
            ResponseEntity<CiclistaResponseDTO> ciclistaDTO = ciclistaService.getCiclistaById(aluguel.getBody().getCiclista());

            email.enviarEmail(ciclistaDTO.getBody().getEmail(), "Devolução realizada", "A devolução da bicicleta foi realizada com sucesso!");

            return ResponseEntity.ok(aluguel.getBody());
        } catch (CustomException e) {
            throw new CustomException(e);
        }}
}