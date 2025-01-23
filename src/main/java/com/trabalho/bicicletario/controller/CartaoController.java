package com.trabalho.bicicletario.controller;

import com.trabalho.bicicletario.dto.CartaoDTO;
import com.trabalho.bicicletario.exception.CustomException;
import com.trabalho.bicicletario.model.Cartao;
import com.trabalho.bicicletario.service.CartaoService;
import com.trabalho.bicicletario.service.CiclistaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("/cartaoDeCredito")
public class CartaoController {
    CartaoService cartaoService;
    CiclistaService ciclistaService;

    public CartaoController(CartaoService cartaoService, CiclistaService ciclistaService) {
        this.cartaoService = cartaoService;
        this.ciclistaService = ciclistaService;
    }

    @GetMapping("/{idCiclista}")
    public ResponseEntity<Cartao> recuperarCartao(@PathVariable int idCiclista) throws CustomException {
        try{
            ResponseEntity<Cartao> cartao = ciclistaService.getCartaoByCiclistaId(idCiclista);

            return ResponseEntity.ok(cartao.getBody());
        } catch (CustomException e) {
            throw new CustomException(e);
        }
    }

    @PutMapping("/{idCiclista}")
    public ResponseEntity<Cartao> editarCartao(@PathVariable int idCiclista, @RequestBody CartaoDTO updateCartaoDto) throws CustomException {
        try{
            Cartao updateCartao = new Cartao(updateCartaoDto);
            ResponseEntity<Cartao> cartao = ciclistaService.updateCartaoByCiclistaId(idCiclista, updateCartao);

            return ResponseEntity.ok(cartao.getBody());
        } catch (CustomException e) {
            throw new CustomException(e);
        }
    }

    @GetMapping("/getAll") // TODO - APAGAR
    public ResponseEntity<Iterable<Cartao>> pegaTodos() {
        Iterable<Cartao> alugueis = cartaoService.pegaTodos();

        return ResponseEntity.ok(alugueis);
    }

    @DeleteMapping("/deleteAll") // TODO - APAGAR
    public ResponseEntity deletaTodos() {
        cartaoService.deletaTodos();

        return ResponseEntity.ok().build();
    }

    @GetMapping("/")
    public ResponseEntity<Cartao> createCartao(@RequestBody CartaoDTO newCartaoDTO) throws CustomException {
        try{
            Cartao newCartao = new Cartao(newCartaoDTO);
            ResponseEntity<Cartao> cartao = cartaoService.createCartao(newCartao);

            return ResponseEntity.ok(cartao.getBody());
        } catch (CustomException e) {
            throw new CustomException(e);
        }
    }
}
