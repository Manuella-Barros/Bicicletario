package com.trabalho.bicicletario.service;

import com.trabalho.bicicletario.model.Cartao;
import com.trabalho.bicicletario.repository.CartaoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class CartaoService {
    CartaoRepository cartaoRepository;

    public CartaoService(CartaoRepository cartaoRepository) {
        this.cartaoRepository = cartaoRepository;
    }

    public ResponseEntity<Cartao> createCartao(Cartao cartao ) {
        if(!cartao.checkIfValid()){ // TODO - DADOS INVALIDOS - 422
            return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
        }

        Cartao createdCartao = cartaoRepository.save(cartao);
        return ResponseEntity.ok(createdCartao);
    }
}
