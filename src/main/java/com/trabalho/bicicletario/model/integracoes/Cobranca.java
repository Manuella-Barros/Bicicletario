package com.trabalho.bicicletario.model.integracoes;

import com.trabalho.bicicletario.model.Cartao;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@AllArgsConstructor
public class Cobranca {

    @AllArgsConstructor
    public class CobrancaDTO{
        int idCiclista;
        double valor;
    }

//    @Autowired
    @Qualifier("restTemplate") private final RestTemplate restTemplate;
    private final String url = "https://bicicletario-gimk.onrender.com/externo/validaCartaoDeCredito";


    public boolean validarCartao(Cartao cartao) {
        //valida cartão no externo
        String urlReq = url+ "externo/validaCartaoDeCredito";

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
        //enviacobranca externa
        String urlReq = url+ "externo/cobranca";

        CobrancaDTO cobrancaDTO = new CobrancaDTO(idCiclista, valor);

        try {
            ResponseEntity<String> response = restTemplate.postForEntity(urlReq, cobrancaDTO, String.class);

            if (!response.getStatusCode().is2xxSuccessful()) {
                return false;
            }
        } catch (Exception ex) {
            return false;
        }
        return true;
    }
}
