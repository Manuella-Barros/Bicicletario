package com.trabalho.bicicletario.controller;

import com.trabalho.bicicletario.exception.CustomException;
import com.trabalho.bicicletario.model.Cartao;
import com.trabalho.bicicletario.service.CartaoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("/cartaoDeCredito")
public class CartaoController {
    CartaoService cartaoService;

    public CartaoController(CartaoService cartaoService) {
        this.cartaoService = cartaoService;
    }

    @GetMapping("/{idCiclista}")
    public ResponseEntity<Cartao> recuperarCartao(@PathVariable int idCiclista) throws CustomException {
        try{
            ResponseEntity<Cartao> cartao = cartaoService.getCartaoById(idCiclista);

            return ResponseEntity.ok(cartao.getBody());
        } catch (CustomException e) {
            throw new CustomException(e);
        }
    }

    @PutMapping("/{idCiclista}")
    public ResponseEntity<Cartao> editarCartao(@PathVariable int idCiclista, @RequestBody Cartao updateCartao) throws CustomException {
        try{
            ResponseEntity<Cartao> cartao = cartaoService.updateCartao(idCiclista, updateCartao);

            return ResponseEntity.ok(cartao.getBody());
        } catch (CustomException e) {
            throw new CustomException(e);
        }
    }

}
