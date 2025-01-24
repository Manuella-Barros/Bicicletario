package com.trabalho.bicicletario.controller;

import com.trabalho.bicicletario.dto.RealizarAluguelDTO;
import com.trabalho.bicicletario.dto.RealizarDevolucaoDTO;
import com.trabalho.bicicletario.dto.response.CiclistaResponseDTO;
import com.trabalho.bicicletario.exception.CustomException;
import com.trabalho.bicicletario.model.*;
import com.trabalho.bicicletario.model.Enum.ErrorEnum;
import com.trabalho.bicicletario.model.integracoes.Email;
import com.trabalho.bicicletario.model.integracoes.dtos.EmailDTO;
import com.trabalho.bicicletario.service.AluguelService;
import com.trabalho.bicicletario.service.CiclistaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("")
public class AluguelController {
    AluguelService aluguelService;
    CiclistaService ciclistaService;
    Email email;

    public AluguelController(AluguelService aluguelService, CiclistaService ciclistaService, Email email) {
        this.aluguelService = aluguelService;
        this.ciclistaService = ciclistaService;
        this.email=email;
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

            if (ciclistaService.hasAluguel(ciclista.getId())){
                throw new CustomException(ErrorEnum.JA_TEM_ALUGUEL);
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
            ResponseEntity<Aluguel> aluguel = aluguelService.postDevolucao(newDevolucaoDTO.getIdBicicleta(), newDevolucaoDTO.getIdTranca());
            ResponseEntity<CiclistaResponseDTO> ciclistaDTO = ciclistaService.getCiclistaById(aluguel.getBody().getCiclista());

            EmailDTO emailDTO = new EmailDTO(ciclistaDTO.getBody().getEmail(), "Devolução realizada", "A devolução da bicicleta foi realizada com sucesso!");
            email.enviarEmail(emailDTO);

            return ResponseEntity.ok(aluguel.getBody());
        } catch (CustomException e) {
            throw new CustomException(e);
        }}
}