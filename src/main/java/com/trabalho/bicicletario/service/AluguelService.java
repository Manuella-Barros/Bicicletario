package com.trabalho.bicicletario.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.trabalho.bicicletario.exception.CustomException;
import com.trabalho.bicicletario.model.*;
import com.trabalho.bicicletario.model.BicicletaModel;
import com.trabalho.bicicletario.model.Enum.ErrorEnum;
import com.trabalho.bicicletario.model.Enum.StatusBicicletaEnum;
import com.trabalho.bicicletario.model.Enum.StatusTrancaEnum;
import com.trabalho.bicicletario.model.integracoes.*;
import com.trabalho.bicicletario.model.integracoes.dtos.EmailDTO;
import com.trabalho.bicicletario.model.integracoes.dtos.responses.BicicletaResponse;
import com.trabalho.bicicletario.repository.AluguelRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.time.Duration;
import java.time.LocalDateTime;

@Service
public class AluguelService {
    AluguelRepository aluguelRepository;
    Tranca tranca;
    Bicicleta bicicleta;
    Email email;
    Cobranca cobranca;
    BicicletaModel bicicletaModel;

    public AluguelService(AluguelRepository aluguelRepository, Tranca tranca, Bicicleta bicicleta, Email email, Cobranca cobranca) {
        this.aluguelRepository = aluguelRepository;
        this.tranca = tranca;
        this.bicicleta = bicicleta;
        this.email = email;
        this.cobranca= cobranca;
    }

    public ResponseEntity<Aluguel> createAluguel(Aluguel aluguel, Ciclista ciclista) throws CustomException {
        try{
            if(!aluguel.checkIfValid()){
                throw new CustomException(ErrorEnum.DADOS_INVALIDOS);
            }

            isAbleToAlugar(aluguel.getTrancaInicio(), aluguel.getCiclista());

            if(aluguel.getCobranca() == 0)
                aluguel.setCobranca(10.00);

            boolean isPagamentoAutorizado = cobranca.enviarCobranca(aluguel.getCiclista(), aluguel.getCobranca());

            if(!isPagamentoAutorizado){
                cobranca.adicionaFilaDeCobranca(aluguel.getCiclista(), aluguel.getCobranca());
                throw new CustomException(ErrorEnum.PAGAMENTO_NAO_AUTORIZADO);
            }

            LocalDateTime dataAtual = LocalDateTime.now();

            if(aluguel.getHoraInicio() == null)
                aluguel.setHoraInicio(dataAtual);

            if(aluguel.getHoraFim() == null)
                aluguel.setHoraFim(dataAtual);

            if(aluguel.getBicicleta() == 0){
                BicicletaResponse bicicletaTranca = tranca.getBicicletaByIdTranca(aluguel.getTrancaInicio());
                aluguel.setBicicleta(bicicletaTranca.getId());
            }

            Aluguel createdAluguel = aluguelRepository.save(aluguel);

            bicicleta.alterarStatusBicicleta(StatusBicicletaEnum.EM_USO.getDescricao(), createdAluguel.getBicicleta());
            tranca.alterarStatusTranca(StatusTrancaEnum.DESTRANCAR.getDescricao(), createdAluguel.getTrancaInicio());

            EmailDTO emailDTO = new EmailDTO(ciclista.getEmail(), "Aluguel realizado", "O aluguel da bicicleta foi realizado com sucesso!\n\n " +
                    "Dados do aluguel:\n" +
                    "- Horario: " + createdAluguel.getHoraInicio() + ".\n" +
                    "- Tranca: " + createdAluguel.getTrancaInicio() + ".\n" +
                    "- Valor: " + createdAluguel.getCobranca() + ".");
            email.enviarEmail(emailDTO);

            return ResponseEntity.ok(createdAluguel);
        } catch (CustomException e) {
            if(e.getMensagem().equals(ErrorEnum.JA_TEM_ALUGUEL.getMensagem())){
                EmailDTO emailDTO = new EmailDTO(ciclista.getEmail(), "Aluguel negado", "O aluguel da bicicleta foi negado pois o usuário já possui um aluguel");
                email.enviarEmail(emailDTO);
            }

            throw new CustomException(e);
        }
    }

    public ResponseEntity<Aluguel> getAluguelAberto(int idCiclista) throws CustomException {
        if(idCiclista <= 0){
            throw new CustomException(ErrorEnum.DADOS_INVALIDOS);
        }

        Aluguel alugueis[] = aluguelRepository.findAllByCiclista(idCiclista);

        if(alugueis.length == 0){
            return ResponseEntity.ok().build();
        }

        for (Aluguel aluguel : alugueis){
            if (aluguel.getTrancaFim() == 0){
                return ResponseEntity.ok(aluguel);
            }
        }

        return ResponseEntity.ok().build();
    }

    public ResponseEntity<Aluguel> postDevolucao(int idBicicleta, int idTranca) throws CustomException {
        try{
            Aluguel aluguel = new Aluguel();
            BicicletaModel bicicletaModelDevolvida = bicicleta.getBicicleta(idBicicleta);

            if(bicicletaModelDevolvida.getStatus().equals(StatusBicicletaEnum.NOVA.getDescricao()) || bicicletaModelDevolvida.getStatus().equals(StatusBicicletaEnum.EM_REPARO.getDescricao())){
                bicicletaModel.cadastrarBicicleta(bicicletaModelDevolvida);
            }

            Aluguel[] oldAlugueis = aluguelRepository.findByBicicletaAndTrancaInicio(idBicicleta, idTranca);

            for (Aluguel oldAluguel : oldAlugueis){
                if (oldAluguel.getTrancaFim() == 0){
                    aluguel = oldAluguel;
                }
            }

            if (aluguel.getId() == 0){
                throw new CustomException(ErrorEnum.DADOS_INVALIDOS);
            }

            LocalDateTime dataDevolucao = LocalDateTime.now();

            Double cobranca = calculaValorCobranca(aluguel.getHoraInicio(), dataDevolucao);

            aluguel.setCobranca(cobranca);

            if(aluguel.getCobranca() != 0){
                boolean isPagamentoAutorizado = this.cobranca.enviarCobranca(aluguel.getCiclista(), aluguel.getCobranca());

                if(!isPagamentoAutorizado){
                    this.cobranca.adicionaFilaDeCobranca(aluguel.getCiclista(), aluguel.getCobranca());
                    throw new CustomException(ErrorEnum.PAGAMENTO_NAO_AUTORIZADO);
                }
            }

            aluguel.setHoraFim(dataDevolucao);
            aluguel.setTrancaFim(idTranca);
            bicicleta.alterarStatusBicicleta(StatusBicicletaEnum.DISPONIVEL.getDescricao(), aluguel.getBicicleta());
            tranca.alterarStatusTranca(StatusTrancaEnum.DESTRANCAR.getDescricao(), aluguel.getTrancaInicio());

            Aluguel createdAluguel = aluguelRepository.save(aluguel);

            bicicleta.alterarStatusBicicleta(StatusBicicletaEnum.REPARO_SOLICITADO.getDescricao(), aluguel.getBicicleta());

            return ResponseEntity.ok(createdAluguel);
        } catch (CustomException e) {
            throw new CustomException(e);
        }
    }

    public void isAbleToAlugar(int idTranca, int idCiclista) throws CustomException {
        if(this.tranca.getTranca(idTranca) == null){
            throw new CustomException(ErrorEnum.TRANCA_INVALIDA);
        }

        BicicletaResponse bicicletaResponse = tranca.getBicicletaByIdTranca(idTranca);

        if(bicicletaResponse == null){
            throw new CustomException(ErrorEnum.TRANCA_SEM_ESSA_BICICLETA);
        }

        if(this.getAluguelAberto(idCiclista).hasBody()){
            throw new CustomException(ErrorEnum.JA_TEM_ALUGUEL);
        }

        if(bicicletaResponse.getStatus().equals(StatusBicicletaEnum.EM_REPARO.getDescricao())){
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

    public Aluguel[] recuperarDados() throws JsonProcessingException, CustomException {
        this.deleteAllAlugueis();

        var jsons = " [\n" +
                "    {\n" +
                "        \"ciclista\": 3,\n" +
                "        \"bicicleta\": 3,\n" +
                "        \"trancaInicio\": 2,\n" +
//      "        \"status\": \"EM_ANDAMENTO\",\n" +
                "        \"cobranca\": 1,\n" +
                "        \"horaInicio\": \"" + LocalDateTime.now() + "\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"ciclista\": 4,\n" +
                "        \"bicicleta\": 5,\n" +
                "        \"trancaInicio\": 4,\n" +
//      "        \"status\": \"EM_ANDAMENTO\",\n" +
                "        \"cobranca\": 2,\n" +
                "        \"horaInicio\": \"" + LocalDateTime.now().minusHours(2) + "\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"ciclista\": 3,\n" +
                "        \"bicicleta\": 1,\n" +
                "        \"trancaInicio\": 1,\n" +
                "        \"trancaFim\": 2,\n" +
//      "        \"status\": \"FINALIZADO_COM_COBRANCA_EXTRA_PENDENTE\",\n" +
                "        \"cobranca\": 3,\n" +
                "        \"horaInicio\": \"" + LocalDateTime.now().minusHours(2) + "\",\n" +
                "        \"horaFim\": \"" + LocalDateTime.now() + "\"\n" +
                "    }\n" +
                "]";

        var objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        Aluguel[] alugueis = objectMapper.readValue(jsons, Aluguel[].class);

        return alugueis;
    }

    public Iterable<Aluguel> pegaTodos(){
        return aluguelRepository.findAll();
    }

    public void deletaTodos(){
        aluguelRepository.deleteAll();
    }

    public ResponseEntity<Aluguel> createMockAluguel(Aluguel aluguel) {
        LocalDateTime dataAtual = LocalDateTime.now();

        if(aluguel.getHoraFim() == null)
            aluguel.setHoraFim(dataAtual);

        Aluguel createdAluguel = aluguelRepository.save(aluguel);

        return ResponseEntity.ok(createdAluguel);
    }
}
