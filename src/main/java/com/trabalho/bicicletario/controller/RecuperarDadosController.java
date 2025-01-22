package com.trabalho.bicicletario.controller;

import com.trabalho.bicicletario.exception.CustomException;
import com.trabalho.bicicletario.model.Bicicleta;
import com.trabalho.bicicletario.model.BicicletaTeste;
import com.trabalho.bicicletario.model.CEP;
import com.trabalho.bicicletario.service.RecuperarDadosService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@RestController()
@RequestMapping("/restaurarDados")
public class RecuperarDadosController {
    RecuperarDadosService recuperarDadosService;
    private final WebClient webClient;
    private final RestTemplate restTemplate;

    public RecuperarDadosController(RecuperarDadosService recuperarDadosService, WebClient webClient, @Qualifier("restTemplate") RestTemplate restTemplate
    ) {
        this.recuperarDadosService = recuperarDadosService;
        this.webClient = webClient;
        this.restTemplate = restTemplate;
    }

    @GetMapping("")
    public void recuperarDados() throws IOException, CustomException {
        recuperarDadosService.recuperarDados();
    }

    @GetMapping("/teste") // TODO - APAGAR
    public ResponseEntity<String> teste() {
//       String url = "https://bicicletario.onrender.com/bicicleta/1";
        String url = "https://viacep.com.br/ws/01001000/json/";
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

        return response;
    }

    public ResponseEntity<String> testa() {
        // WEB CLIENT ---------------------------------------------------
//        BicicletaTeste bicicletaTeste = webClient.get().uri("https://bicicletario.onrender.com/bicicleta/1").retrieve().bodyToMono(BicicletaTeste.class).block();
//        System.out.println("Corpo = " + bicicletaTeste);
//        return ResponseEntity.ok(bicicletaTeste);
//  --------------------------------------------------- ---------------------------------------------------

        // Rest template  ---------------------------------------------------

//        RestTemplate restTemplate = new RestTemplate();
//        String url = "https://bicicletario.onrender.com/bicicleta/1";
//        ResponseEntity<Bicicleta> response = restTemplate.getForEntity(url, Bicicleta.class);
//        return response;
////
//        System.out.println("Copro = " + bicicletaTeste);
//  --------------------------------------------------- ---------------------------------------------------

//        CEP bicicletaTeste = webClient.get().uri("https://viacep.com.br/ws/01001000/json/").retrieve().bodyToMono(CEP.class).block();
//        System.out.println("Corpo = " + bicicletaTeste);
//        return ResponseEntity.ok(bicicletaTeste);

        String url = "https://viacep.com.br/ws/01001000/json/";
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

        return response;
    }
}
