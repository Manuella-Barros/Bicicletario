package com.trabalho.bicicletario.model.integracoes;

import com.trabalho.bicicletario.model.Cartao;
import com.trabalho.bicicletario.model.integracoes.dtos.CobrancaDTO;
import com.trabalho.bicicletario.model.integracoes.dtos.responses.CobrancaResponseDTO;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@AllArgsConstructor
public class Cobranca {

    @Qualifier("restTemplate") private final RestTemplate restTemplate;
    private final String url = "https://bicicletario-gimk.onrender.com/externo/";


    public boolean validarCartao(Cartao cartao) {
        //valida cartão no externo
        String urlReq = url+ "validaCartaoDeCredito";

        try {
            ResponseEntity<String> response = restTemplate.postForEntity(urlReq, cartao, String.class);

            if (!response.getStatusCode().is2xxSuccessful()) {
                return false;
            }
        } catch (Exception ex) {
            return false;
        }
        return true;
    }

    public boolean enviarCobranca(int idCiclista, Double valor) {
        //envia cobranca no externao
        String urlReq = url+ "cobranca";

        CobrancaDTO cobrancaDTO = new CobrancaDTO(idCiclista, valor);

        try {
            ResponseEntity<CobrancaResponseDTO> response = restTemplate.postForEntity(urlReq, cobrancaDTO, CobrancaResponseDTO.class);
            if (!response.getStatusCode().is2xxSuccessful()) {
                return false;
            }
        } catch (Exception ex) {
            return false;
        }
        return true;
    }

    public boolean adicionaFilaDeCobranca(int idCiclista, Double valor) {
        //adiciona a cobrança na fila de cobranças
        String urlReq = url+ "filaCobranca";

        CobrancaDTO cobrancaDTO = new CobrancaDTO(idCiclista, valor);

        try {
            ResponseEntity<CobrancaResponseDTO> response = restTemplate.postForEntity(urlReq, cobrancaDTO, CobrancaResponseDTO.class);
            if (!response.getStatusCode().is2xxSuccessful()) {
                return false;
            }
        } catch (Exception ex) {
            return false;
        }
        return true;
    }
}
