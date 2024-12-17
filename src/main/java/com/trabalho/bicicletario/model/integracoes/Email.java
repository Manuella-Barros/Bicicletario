package com.trabalho.bicicletario.model.integracoes;

import org.springframework.stereotype.Service;

@Service
public class Email {
    public String destinatario;
    public String assunto;
    public String mensagem;

    public Email enviarEmail(String destinatario, String assunto, String mensagem) {
        this.destinatario = destinatario;
        this.assunto = assunto;
        this.mensagem = mensagem;
        return new Email();
    }
}
