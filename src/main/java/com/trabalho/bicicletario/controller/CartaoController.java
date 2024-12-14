package com.trabalho.bicicletario.controller;

import com.trabalho.bicicletario.model.Cartao;
import com.trabalho.bicicletario.service.CartaoService;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<Cartao> recuperarCartao(@PathVariable int idCiclista) {
        ResponseEntity<Cartao> cartao = cartaoService.getCartaoById(idCiclista);

        if(cartao.getStatusCode() != HttpStatus.OK) {
            return new ResponseEntity<>(cartao.getStatusCode());
        }

        return ResponseEntity.ok(cartao.getBody());
    }

    @PutMapping("/{idCiclista}")
    public ResponseEntity<Cartao> editarCartao(@PathVariable int idCiclista, @RequestBody Cartao updateCartao) {
        ResponseEntity<Cartao> cartao = cartaoService.updateCartao(idCiclista, updateCartao);

        if(cartao.getStatusCode() != HttpStatus.OK) {
            return new ResponseEntity<>(cartao.getStatusCode());
        }

        return ResponseEntity.ok(cartao.getBody());
    }
}
