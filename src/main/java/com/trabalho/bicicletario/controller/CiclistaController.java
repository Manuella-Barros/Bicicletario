package com.trabalho.bicicletario.controller;

import com.trabalho.bicicletario.dto.CadastrarCiclistaDTO;
import com.trabalho.bicicletario.dto.CiclistaDTO;
import com.trabalho.bicicletario.dto.response.CiclistaResponseDTO;
import com.trabalho.bicicletario.exception.CustomException;
import com.trabalho.bicicletario.model.*;
import com.trabalho.bicicletario.service.AluguelService;
import com.trabalho.bicicletario.service.CartaoService;
import com.trabalho.bicicletario.service.CiclistaService;
import com.trabalho.bicicletario.service.PassaporteService;
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
            ResponseEntity<Cartao> cartao = cartaoService.createCartao(newCiclista.meioDePagamento);
            newCiclista.ciclistaDTO.setIdCartao(cartao.getBody().getId());

            ResponseEntity<Passaporte> passaporte = passaporteService.createPassaporte(newCiclista.ciclistaDTO.getPassaporte());
            newCiclista.ciclistaDTO.setPassaporte(passaporte.getBody());
            newCiclista.ciclistaDTO.setIdPassaporte(passaporte.getBody().getId());

            Ciclista ciclistaInfo = new Ciclista(newCiclista.ciclistaDTO);
            ResponseEntity<Ciclista> ciclista = ciclistaService.createCiclista(ciclistaInfo);

            CiclistaResponseDTO response = new CiclistaResponseDTO(ciclista.getBody());
            response.setPassaporte(passaporte.getBody());

            return ResponseEntity.ok(response);
        } catch (CustomException e) {
            throw new CustomException(e);
        }
    }

    @GetMapping("/{idCiclista}") // TODO - NÃO CONSEGUI TERMINAR, REPENSAR MELHOR E VOLTAR!!
    public ResponseEntity<CiclistaResponseDTO> recuperarCiclista(@PathVariable int idCiclista) throws CustomException {
        try{
            ResponseEntity<Ciclista> ciclista = ciclistaService.getCiclistaById(idCiclista);
            ResponseEntity<Passaporte> passaporte = passaporteService.getPassaporteById(ciclista.getBody().getIdPassaporte());

            CiclistaResponseDTO response = new CiclistaResponseDTO(ciclista.getBody());
            response.setPassaporte(passaporte.getBody());

            return ResponseEntity.ok(response);
        } catch (CustomException e) {
            throw new CustomException(e);
        }
    }

    @PutMapping("/{idCiclista}") // TODO - NÃO CONSEGUI TERMINAR, REPENSAR MELHOR E VOLTAR!!
    public ResponseEntity<Ciclista> editarCiclista(@PathVariable int idCiclista, @RequestBody Ciclista updateCiclista) throws CustomException {
        try{
            ResponseEntity<Ciclista> ciclista = ciclistaService.updateCiclista(idCiclista, updateCiclista);

            return ResponseEntity.ok(ciclista.getBody());
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
            ResponseEntity<Aluguel> aluguel = aluguelService.getAluguelByCiclistaId(idCiclista);

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
    public ResponseEntity<Ciclista> ativarCadastro(@PathVariable int idCiclista) throws CustomException {
        try{
            ResponseEntity<Ciclista> ciclista = ciclistaService.ativarCadastro(idCiclista);

            return ciclista;
        } catch (CustomException e) {
            throw new CustomException(e);
        }
    }
}
