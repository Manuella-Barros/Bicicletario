package com.trabalho.bicicletario.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    static WebClient equipamentoApi = WebClient.create("https://bicicletario.onrender.com");
    static WebClient externoApi = WebClient.create("https://bicicletario-gimk.onrender.com");

    @Bean
    public WebClient webClient(WebClient.Builder builder) {
        return builder.build();
    }
}