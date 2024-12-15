package com.trabalho.bicicletario.service;

import com.trabalho.bicicletario.model.Cartao;
import com.trabalho.bicicletario.repository.CartaoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

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

    public ResponseEntity<Cartao> getCartaoById(int id) {
        if(id <= 0){ // TODO - ERRO ID INVÁLIDO - 422
            return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
        }

        Optional<Cartao> optionalCartao = cartaoRepository.findById( id );

        if(!optionalCartao.isPresent()){ // TODO - NÃO ENCONTRADO - 404
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(optionalCartao.get());
    }

    public ResponseEntity<Cartao> updateCartao(int id, Cartao updateCartao) {
        if(!updateCartao.checkIfValid() || id <= 0){ // TODO - ERRO ALGUM CAMPO INVALIDO - 422
            return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
        }

        if(!this.getCartaoById(id).hasBody()){ // TODO - NÃO ENCONTRADO - 404
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        updateCartao.setId(id);
        this.createCartao(updateCartao);

        return ResponseEntity.ok(updateCartao);
    }
}
