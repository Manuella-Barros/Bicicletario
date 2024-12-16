package com.trabalho.bicicletario.service;

import com.trabalho.bicicletario.exception.CustomException;
import com.trabalho.bicicletario.model.Aluguel;
import com.trabalho.bicicletario.model.ErrorEnum;
import com.trabalho.bicicletario.repository.AluguelRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
public class AluguelService {
    AluguelRepository aluguelRepository;

    public AluguelService(AluguelRepository aluguelRepository) {
        this.aluguelRepository = aluguelRepository;
    }

    public ResponseEntity<Aluguel> createAluguel(Aluguel aluguel ) throws CustomException {
        if(!aluguel.checkIfValid()){
            throw new CustomException(ErrorEnum.DADOS_INVALIDOS);
        }

        LocalDateTime dataAtual = LocalDateTime.now();
        aluguel.setHoraInicio(dataAtual);
        aluguel.setHoraFim(dataAtual.plusHours(2));
        aluguel.setCobranca(10.00);

        Aluguel createdAluguel = aluguelRepository.save(aluguel);
        return ResponseEntity.ok(createdAluguel);
    }

    public ResponseEntity<Aluguel> getAluguelByCiclistaId(int ciclistaId) throws CustomException {
        if(ciclistaId <= 0){
            throw new CustomException(ErrorEnum.DADOS_INVALIDOS);
        }

        Aluguel aluguel = aluguelRepository.findByCiclista(ciclistaId);
        return ResponseEntity.ok(aluguel);
    }

    public ResponseEntity<Aluguel> getAluguelByBicicletaId(int bicicletaId) {
        Aluguel aluguel = aluguelRepository.findByBicicleta(bicicletaId);

        LocalDateTime dataAtual = LocalDateTime.now();
        aluguel.setHoraFim(dataAtual);
        aluguel.setCobranca(20.00);

        Aluguel createdAluguel = aluguelRepository.save(aluguel);

        return ResponseEntity.ok(createdAluguel);
    }
}
