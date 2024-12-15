package com.trabalho.bicicletario.service;

import com.trabalho.bicicletario.model.Aluguel;
import com.trabalho.bicicletario.repository.AluguelRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
public class AluguelService {
    AluguelRepository aluguelRepository;

    public AluguelService(AluguelRepository aluguelRepository) {
        this.aluguelRepository = aluguelRepository;
    }

    public ResponseEntity<Aluguel> createAluguel(Aluguel aluguel ) {
        if(!aluguel.checkIfValid()){ // TODO - DADOS INVALIDOS - 422
            return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
        }

        LocalDateTime dataAtual = LocalDateTime.now();
        aluguel.setHoraInicio(dataAtual);
        aluguel.setHoraFim(dataAtual.plusHours(2));
        aluguel.setCobranca(10.00);

        Aluguel createdAluguel = aluguelRepository.save(aluguel);
        return ResponseEntity.ok(createdAluguel);
    }

    public ResponseEntity<Aluguel> getAluguelByCiclistaId(int ciclistaId) {
        if(ciclistaId <= 0){ // TODO - ERRO ID INVÁLIDO - 422
            return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
        }

        Aluguel aluguel = aluguelRepository.findByCiclista(ciclistaId);
        return ResponseEntity.ok(aluguel);
    }
}
