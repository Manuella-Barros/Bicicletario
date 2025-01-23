package com.trabalho.bicicletario.service;

import com.trabalho.bicicletario.exception.CustomException;
import com.trabalho.bicicletario.model.Cartao;
import com.trabalho.bicicletario.model.Ciclista;
import com.trabalho.bicicletario.model.Enum.ErrorEnum;
import com.trabalho.bicicletario.repository.CartaoRepository;
import com.trabalho.bicicletario.repository.CiclistaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.YearMonth;
import java.util.Optional;

@Service
public class CartaoService {
    CartaoRepository cartaoRepository;

    public CartaoService(CartaoRepository cartaoRepository) {
        this.cartaoRepository = cartaoRepository;
    }

    public ResponseEntity<Cartao> createCartao(Cartao cartao) throws CustomException {
        if(!cartao.checkIfValid()){
            throw new CustomException(ErrorEnum.DADOS_INVALIDOS);
        }

        Cartao createdCartao = cartaoRepository.save(cartao);
        return ResponseEntity.ok(createdCartao);
    }


    public ResponseEntity<Cartao> getCartaoById(int id) throws CustomException {
        if(id <= 0){
            throw new CustomException(ErrorEnum.DADOS_INVALIDOS);
        }

        Optional<Cartao> optionalCartao = cartaoRepository.findById( id );

        if(!optionalCartao.isPresent()){
            throw new CustomException(ErrorEnum.REQUISICAO_MAL_FORMADA);
        }

        return ResponseEntity.ok(optionalCartao.get());
    }

    public ResponseEntity<Cartao> updateCartao(int id, Cartao updateCartao) throws CustomException {
        if(!updateCartao.checkIfValid() || id <= 0){
            throw new CustomException(ErrorEnum.DADOS_INVALIDOS);

        }

        if(!this.getCartaoById(id).hasBody()){
            throw new CustomException(ErrorEnum.REQUISICAO_MAL_FORMADA);

        }

        updateCartao.setId(id);
        this.createCartao(updateCartao);

        return ResponseEntity.ok(updateCartao);
    }

    public void deleteAllCartoes() {
        this.cartaoRepository.deleteAll();
    }

    public Iterable<Cartao> pegaTodos(){
        return cartaoRepository.findAll();
    }

    public void deletaTodos(){
        cartaoRepository.deleteAll();
    }
}
