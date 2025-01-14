package com.trabalho.bicicletario.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.trabalho.bicicletario.dto.response.CiclistaResponseDTO;
import com.trabalho.bicicletario.exception.CustomException;
import com.trabalho.bicicletario.model.*;
import com.trabalho.bicicletario.model.integracoes.*;
import com.trabalho.bicicletario.repository.AluguelRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class AluguelService {
    AluguelRepository aluguelRepository;
    CiclistaService ciclistaService;
    Tranca tranca;
    Totem totem;
    Bicicleta bicicleta;
    Email email;

    public AluguelService(AluguelRepository aluguelRepository, Tranca tranca, Totem totem, Bicicleta bicicleta, Email email) {
        this.aluguelRepository = aluguelRepository;
        this.tranca = tranca;
        this.totem = totem;
        this.bicicleta = bicicleta;
        this.email = email;
    }

    public ResponseEntity<Aluguel> createAluguel(Aluguel aluguel, Ciclista ciclista) throws CustomException {
        try{
            if(!aluguel.checkIfValid()){
                throw new CustomException(ErrorEnum.DADOS_INVALIDOS);
            }

            isAbleToAlugar(aluguel.getTrancaInicio(), aluguel.getCiclista(), aluguel.getBicicleta());

            Cobranca cobranca = new Cobranca();
            boolean isPagamentoAutorizado = cobranca.enviarCobranca(aluguel.getCiclista(), aluguel.getCobranca());

            if(!isPagamentoAutorizado){
                throw new CustomException(ErrorEnum.PAGAMENTO_NAO_AUTORIZADO);
            }

            LocalDateTime dataAtual = LocalDateTime.now();
            aluguel.setHoraInicio(dataAtual);
            aluguel.setHoraFim(dataAtual);
            aluguel.setCobranca(10.00);
            aluguel.setBicicleta(1); // TODO - SERA Q TEM Q BUSCAR A BICICLETA AQUI

            Aluguel createdAluguel = aluguelRepository.save(aluguel);

            bicicleta.alterarStatusBicicleta(StatusBicicletaEnum.EM_USO.getDescricao(), createdAluguel.getBicicleta());
            tranca.alterarStatusTranca(StatusTrancaEnum.LIVRE.getDescricao(), createdAluguel.getTrancaInicio());

            email.enviarEmail(ciclista.getEmail(), "Aluguel realizado", "O aluguel da bicicleta foi realizado com sucesso!");

            return ResponseEntity.ok(createdAluguel);
        } catch (CustomException e) {
            if(e.getMensagem().equals(ErrorEnum.JA_TEM_ALUGUEL.getMensagem())){
                email.enviarEmail(ciclista.getEmail(), "Aluguel negado", "O aluguel da bicicleta foi negado pois o usuário já possui um aluguel");
            }

            throw new CustomException(e);
        }
    }

    public ResponseEntity<Aluguel> getAluguelAberto(int idCiclista) throws CustomException {
        if(idCiclista <= 0){
            throw new CustomException(ErrorEnum.DADOS_INVALIDOS);
        }

        Aluguel aluguel = aluguelRepository.findByCiclista(idCiclista);
        return ResponseEntity.ok(aluguel);
    }

    public ResponseEntity<Aluguel> postDevolucao(Aluguel aluguel) throws CustomException {
        try{
            if(!aluguel.checkIfValid()){
                throw new CustomException(ErrorEnum.DADOS_INVALIDOS);
            }

            Bicicleta bicicletaDevolvida = bicicleta.getBicicleta(aluguel.getBicicleta());

            if(aluguel.getBicicleta() <= 0 || bicicletaDevolvida == null){
                throw new CustomException(ErrorEnum.BICICLETA_INVALIDA);
            }

            if(bicicletaDevolvida.getStatus().equals(StatusBicicletaEnum.NOVA.getDescricao()) || bicicletaDevolvida.getStatus().equals(StatusBicicletaEnum.EM_REPARO.getDescricao())){
                bicicleta.cadastrarBicicleta(bicicletaDevolvida);
            }

            Aluguel oldAluguel = aluguelRepository.findByBicicletaAndTrancaInicio(aluguel.getBicicleta(), aluguel.getTrancaInicio());
            LocalDateTime dataDevolucao = LocalDateTime.now();

            Double cobranca = calculaValorCobranca(oldAluguel.getHoraInicio(), dataDevolucao);

            oldAluguel.setCobranca(cobranca);
            oldAluguel.setHoraFim(dataDevolucao);
            bicicleta.alterarStatusBicicleta(StatusBicicletaEnum.DISPONIVEL.getDescricao(), oldAluguel.getBicicleta());
            tranca.alterarStatusTranca(StatusTrancaEnum.OCUPADA.getDescricao(), oldAluguel.getTrancaInicio());

            Aluguel createdAluguel = aluguelRepository.save(oldAluguel);


            bicicleta.alterarStatusBicicleta(StatusBicicletaEnum.REPARO_SOLICITADO.getDescricao(), oldAluguel.getBicicleta());

            return ResponseEntity.ok(createdAluguel);
        } catch (CustomException e) {
            throw new CustomException(e);
        }
    }

    public void isAbleToAlugar(int idTranca, int idCiclista, int idBicicleta) throws CustomException {
        if(this.tranca.getTranca(idTranca) == null){
            throw new CustomException(ErrorEnum.TRANCA_INVALIDA);
        }

        if(this.tranca.destrancar(idTranca, idBicicleta) == null){
            throw new CustomException(ErrorEnum.NAO_DESTRANCOU);
        }

        if(tranca.getBicicletaByIdTranca(idTranca) == null || tranca.getBicicletaByIdTranca(idTranca).getId() != idBicicleta){
            throw new CustomException(ErrorEnum.TRANCA_SEM_ESSA_BICICLETA);
        }

        if(this.getAluguelAberto(idCiclista).hasBody()){
            throw new CustomException(ErrorEnum.JA_TEM_ALUGUEL);
        }

        if(this.totem.getBicicleta(idTranca).getStatus().equals(StatusBicicletaEnum.EM_REPARO.getDescricao())){
            throw new CustomException(ErrorEnum.BICICLETA_EM_REPARO);
        }
    }

    public double calculaValorCobranca(LocalDateTime initialDate, LocalDateTime finalDate) throws CustomException {
        Duration duracao = Duration.between(initialDate, finalDate);

        if(duracao.toHours() <= 2){
            return 0;
        }

        int meiaHora = 0;
        initialDate = initialDate.plusHours(2);

        while (initialDate.isBefore(finalDate)) { // Enquanto a data atual não ultrapassar a data final
            initialDate = initialDate.plusMinutes(30); // Adiciona 30 minutos
            meiaHora++;
        }

        return (double) meiaHora * 5;

    }

    public void deleteAllAlugueis() {
        aluguelRepository.deleteAll();
    }

    public void recuperarDados() throws JsonProcessingException, CustomException {
        this.deleteAllAlugueis();

        // TODO - DE ONDE VEM E PRA ONDE VAI ESSE STATUS

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        var jsons = "[\n" +
                "    {\n" +
                "        \"ciclista\": 3,\n" +
                "        \"bicicleta\": 3,\n" +
                "        \"trancaInicio\": 2,\n" +
//                "        \"status\": \"EM_ANDAMENTO\",\n" +
                "        \"cobranca\": 1,\n" +
                "        \"horaInicio\":" + LocalDateTime.now().format(formatter) +
                "    },\n" +
                "    {\n" +
                "        \"ciclista\": 4,\n" +
                "        \"bicicleta\": 5,\n" +
                "        \"trancaInicio\": 4,\n" +
//                "        \"status\": \"EM_ANDAMENTO\",\n" +
                "        \"cobranca\": 2,\n" +
                "        \"horaInicio\":" + LocalDateTime.now().minusHours(2).format(formatter)  +
                "    },\n" +
                "    {\n" +
                "        \"ciclista\": 3,\n" +
                "        \"bicicleta\": 1,\n" +
                "        \"trancaInicio\": 1,\n" +
                "        \"trancaFim\": 2,\n" +
//                "        \"status\": \"FINALIZADO_COM_COBRANCA_EXTRA_PENDENTE\",\n" +
                "        \"cobranca\": 3,\n" +
                "        \"horaInicio\":" + LocalDateTime.now().minusHours(2).format(formatter)  +
                "        \"horaFim\":" + LocalDateTime.now().format(formatter)  +
                "    }\n" +
                "]";

        var objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        Aluguel[] alugueis = objectMapper.readValue(jsons, Aluguel[].class);

        for (Aluguel aluguel : alugueis) {
            ResponseEntity<CiclistaResponseDTO> ciclistaDTO = ciclistaService.getCiclistaById(aluguel.getCiclista());
            Ciclista ciclista = new Ciclista(ciclistaDTO.getBody());
            this.createAluguel(aluguel, ciclista);
        }
    }
}
