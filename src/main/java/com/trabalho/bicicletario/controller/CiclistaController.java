package com.trabalho.bicicletario.controller;

import com.trabalho.bicicletario.dto.CadastrarCiclistaDTO;
import com.trabalho.bicicletario.dto.CiclistaDTO;
import com.trabalho.bicicletario.dto.response.CiclistaResponseDTO;
import com.trabalho.bicicletario.exception.CustomException;
import com.trabalho.bicicletario.model.*;
import com.trabalho.bicicletario.service.*;
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
    public ResponseEntity<CiclistaResponseDTO> cadastrarCiclista(@RequestBody CadastrarCiclistaDTO newCiclista) throws CustomException {
        try{
            Ciclista ciclistaInfo = new Ciclista(newCiclista.ciclistaDTO);
            Cartao cartao = new Cartao(newCiclista.meioDePagamento);
            Passaporte passaporte = new Passaporte(newCiclista.ciclistaDTO.getPassaporte());
            ResponseEntity<CiclistaResponseDTO> ciclista = ciclistaService.createCiclista(ciclistaInfo, cartao, passaporte);

            return ResponseEntity.ok(ciclista.getBody());
        } catch (CustomException e) {
            throw new CustomException(e);
        }
    }

    @GetMapping("/{idCiclista}")
    public ResponseEntity<CiclistaResponseDTO> recuperarCiclista(@PathVariable int idCiclista) throws CustomException {
        try{
            ResponseEntity<CiclistaResponseDTO> ciclista = ciclistaService.getCiclistaById(idCiclista);

            return ResponseEntity.ok(ciclista.getBody());
        } catch (CustomException e) {
            throw new CustomException(e);
        }
    }

    @PutMapping("/{idCiclista}")
    public ResponseEntity<CiclistaResponseDTO> editarCiclista(@PathVariable int idCiclista, @RequestBody CiclistaDTO updateCiclistaDTO) throws CustomException {
        try{
            Ciclista updateCiclista = new Ciclista(updateCiclistaDTO);
            ResponseEntity<Ciclista> ciclista = ciclistaService.updateCiclista(idCiclista, updateCiclista);
            ResponseEntity<Passaporte> passaporte = passaporteService.updatePassaporte(ciclista.getBody().getIdPassaporte(), updateCiclistaDTO.getPassaporte());

            CiclistaResponseDTO response = new CiclistaResponseDTO(ciclista.getBody());
            response.setPassaporte(passaporte.getBody());

            return ResponseEntity.ok(response);
        } catch (CustomException e) {
            throw new CustomException(e);
        }
    }

    @GetMapping("/existeEmail/{email}")
    public ResponseEntity<Boolean> verificarEmail(@PathVariable String email) throws CustomException {
        try{
            ResponseEntity<Boolean> exists = ciclistaService.emailExists(email);

            return ResponseEntity.ok(exists.getBody());
        } catch (CustomException e) {
            throw new CustomException(e);
        }
    }

    @GetMapping("/{idCiclista}/bicicletaAlugada")
    public ResponseEntity<Bicicleta> recuperarBicicleta(@PathVariable int idCiclista) throws CustomException {
        try{
            ciclistaService.ciclistaExists(idCiclista);
            ResponseEntity<Aluguel> aluguel = aluguelService.getAluguelAberto(idCiclista);

            if(!aluguel.hasBody()){
                return ResponseEntity.ok().build();
            }

            Bicicleta bicicleta = new Bicicleta(aluguel.getBody().getBicicleta(), "Caloi", "Elite Carbon", "2023", 1234, "OCUPADA");

            return ResponseEntity.ok(bicicleta);
        } catch (CustomException e) {
            throw new CustomException(e);
        }
    }

    @PostMapping("/{idCiclista}/ativar")
    public ResponseEntity<CiclistaResponseDTO> ativarCadastro(@PathVariable int idCiclista) throws CustomException {
        try{
            ResponseEntity<CiclistaResponseDTO> ciclista = ciclistaService.ativarCadastro(idCiclista);

            return ciclista;
        } catch (CustomException e) {
            throw new CustomException(e);
        }
    }

    @PostMapping("/{idCiclista}/permiteAluguel")
    public ResponseEntity<Boolean> permiteAluguel(@PathVariable int idCiclista) throws CustomException {
        try{
            boolean hasAluguel = ciclistaService.hasAluguel(idCiclista);

            return ResponseEntity.ok(!hasAluguel);
        } catch (CustomException e) {
            throw new CustomException(e);
        }
    }
}
