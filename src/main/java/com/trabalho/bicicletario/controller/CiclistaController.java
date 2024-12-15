package com.trabalho.bicicletario.controller;

import com.trabalho.bicicletario.dto.CadastrarCiclistaDTO;
import com.trabalho.bicicletario.model.*;
import com.trabalho.bicicletario.service.AluguelService;
import com.trabalho.bicicletario.service.CartaoService;
import com.trabalho.bicicletario.service.CiclistaService;
import com.trabalho.bicicletario.service.PassaporteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("/ciclista")
public class CiclistaController {
    CiclistaService ciclistaService;
    CartaoService cartaoService;
    PassaporteService passaporteService;
    AluguelService aluguelService;

    public CiclistaController(CiclistaService ciclistaService, CartaoService cartaoService, PassaporteService passaporteService, AluguelService aluguelService) {
        this.ciclistaService = ciclistaService;
        this.cartaoService = cartaoService;
        this.passaporteService = passaporteService;
        this.aluguelService = aluguelService;
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

    @GetMapping("/{idCiclista}") // TODO - NÃO CONSEGUI TERMINAR, REPENSAR MELHOR E VOLTAR!!
    public ResponseEntity<Ciclista> recuperarCiclista(@PathVariable int idCiclista) {
        ResponseEntity<Ciclista> ciclista = ciclistaService.getCiclistaById(idCiclista);

        if(ciclista.getStatusCode() != HttpStatus.OK) {
            return new ResponseEntity<>(ciclista.getStatusCode());
        }

        return ResponseEntity.ok(ciclista.getBody());
    }

    @PutMapping("/{idCiclista}") // TODO - NÃO CONSEGUI TERMINAR, REPENSAR MELHOR E VOLTAR!!
    public ResponseEntity<Ciclista> editarCiclista(@PathVariable int idCiclista, @RequestBody Ciclista updateCiclista) {
        ResponseEntity<Ciclista> ciclista = ciclistaService.updateCiclista(idCiclista, updateCiclista);

        if(ciclista.getStatusCode() != HttpStatus.OK) {
            return new ResponseEntity<>(ciclista.getStatusCode());
        }

        return ResponseEntity.ok(ciclista.getBody());
    }

    @GetMapping("/existeEmail/{email}")
    public ResponseEntity<Boolean> verificarEmail(@PathVariable String email){
        ResponseEntity<Boolean> exists = ciclistaService.emailExists(email);

        if(exists.getStatusCode() != HttpStatus.OK) {
            return new ResponseEntity<>(exists.getStatusCode());
        }

        return ResponseEntity.ok(exists.getBody());
    }

    @GetMapping("/{idCiclista}/bicicletaAlugada")
    public ResponseEntity<Bicicleta> recuperarBicicleta(@PathVariable int idCiclista){
        ResponseEntity Ciclista = ciclistaService.ciclistaExists(idCiclista);

        if (Ciclista.getStatusCode() != HttpStatus.OK) {
            return new ResponseEntity<>(Ciclista.getStatusCode());
        }

        ResponseEntity<Aluguel> aluguel = aluguelService.getAluguelByCiclistaId(idCiclista);

        if(aluguel.getStatusCode() != HttpStatus.OK) {
            return new ResponseEntity<>(aluguel.getStatusCode());
        }

        if(!aluguel.hasBody()){
            return ResponseEntity.ok().build();
        }

        Bicicleta bicicleta = new Bicicleta(aluguel.getBody().getBicicleta(), "Caloi", "Elite Carbon", "2023", 1234, "OCUPADA");

        return ResponseEntity.ok(bicicleta);
    }

}
