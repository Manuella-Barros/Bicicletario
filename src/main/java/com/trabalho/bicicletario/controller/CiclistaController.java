package com.trabalho.bicicletario.controller;

import com.trabalho.bicicletario.dto.CadastrarCiclistaDTO;
import com.trabalho.bicicletario.model.Cartao;
import com.trabalho.bicicletario.model.Ciclista;
import com.trabalho.bicicletario.model.Passaporte;
import com.trabalho.bicicletario.service.CartaoService;
import com.trabalho.bicicletario.service.CiclistaService;
import com.trabalho.bicicletario.service.PassaporteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/ciclista")
public class CiclistaController {
    CiclistaService ciclistaService;
    CartaoService cartaoService;
    PassaporteService passaporteService;

    public CiclistaController(CiclistaService ciclistaService, CartaoService cartaoService, PassaporteService passaporteService) {
        this.ciclistaService = ciclistaService;
        this.cartaoService = cartaoService;
        this.passaporteService = passaporteService;
    }

    @PostMapping("")
    public ResponseEntity<Ciclista> cadastrarCiclista(@RequestBody CadastrarCiclistaDTO newCiclista) {
        ResponseEntity<Cartao> cartao = cartaoService.createCartao(newCiclista.meioDePagamento);

        if(cartao.getStatusCode() != HttpStatus.OK) {
            return new ResponseEntity<>(cartao.getStatusCode());
        }

        newCiclista.ciclistaDTO.setIdCartao(cartao.getBody().getId());

        ResponseEntity<Passaporte> passaporte = passaporteService.createPassaporte(newCiclista.ciclistaDTO.getPassaporte());

        if(passaporte.getStatusCode() == HttpStatus.OK) {
            newCiclista.ciclistaDTO.setPassaporte(passaporte.getBody());
            newCiclista.ciclistaDTO.setIdPassaporte(passaporte.getBody().getId());
        }

        if(passaporte.getStatusCode() != HttpStatus.OK && !newCiclista.ciclistaDTO.checkIfBrasileiro()) {
            return new ResponseEntity<>(passaporte.getStatusCode());
        }

        Ciclista ciclistaInfo = new Ciclista(newCiclista.ciclistaDTO);
        ResponseEntity<Ciclista> ciclista = ciclistaService.createCiclista(ciclistaInfo);

        if(ciclista.getStatusCode() != HttpStatus.OK) {
            return new ResponseEntity<>(ciclista.getStatusCode());
        }

        return ResponseEntity.ok(ciclista.getBody());
    }
}
