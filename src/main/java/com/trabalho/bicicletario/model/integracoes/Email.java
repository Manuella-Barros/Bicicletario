package com.trabalho.bicicletario.model.integracoes;

import org.springframework.stereotype.Service;

@Service
public class Email {
    public Email enviarEmail(String destinatario, String assunto, String mensagem) {
        return new Email();
    }
}
