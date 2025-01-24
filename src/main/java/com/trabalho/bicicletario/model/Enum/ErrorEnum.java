package com.trabalho.bicicletario.model.Enum;

import org.springframework.http.HttpStatus;

public enum ErrorEnum {
    CICLISTA_CRIADO("Ciclista cadastrado", "201", HttpStatus.CREATED),
    REQUISICAO_MAL_FORMADA("Requisição mal formada", "404", HttpStatus.NOT_FOUND),
    NAO_ENCONTRADO("Não foi encontrado", "404", HttpStatus.NOT_FOUND),
    DADOS_INVALIDOS("Dados Inválidos", "422", HttpStatus.UNPROCESSABLE_ENTITY),
    EMAIL_VAZIO("Email não enviado como parâmetro", "400", HttpStatus.BAD_REQUEST ),
    CICLISTA_INATIVO("Cadastro do ciclista não está ativo", "422", HttpStatus.UNPROCESSABLE_ENTITY ),
    BICICLETA_EM_REPARO("A bicicleta está em reparo", "422", HttpStatus.UNPROCESSABLE_ENTITY ),
    JA_TEM_ALUGUEL("O clicista já possui aluguel", "422", HttpStatus.UNPROCESSABLE_ENTITY ),
    PAGAMENTO_NAO_AUTORIZADO("O pagamento não foi autorizado", "422", HttpStatus.UNPROCESSABLE_ENTITY ),
    TRANCA_INVALIDA("A tranca inserida é inválida", "422", HttpStatus.UNPROCESSABLE_ENTITY ),
    BICICLETA_INVALIDA("A bicicleta inserida é inválida", "422", HttpStatus.UNPROCESSABLE_ENTITY ),
    TRANCA_SEM_ESSA_BICICLETA("Não há bicicleta na tranca", "422", HttpStatus.UNPROCESSABLE_ENTITY ),
    CARTAO_INVALIDO("O cartão inserido é inválido", "422", HttpStatus.UNPROCESSABLE_ENTITY ),
    ERRO_ENVIO_EMAIL("Erro ao enviar o email", "422", HttpStatus.UNPROCESSABLE_ENTITY ),
    NAO_DESTRANCOU("Não foi possível destrancar a tranca", "422", HttpStatus.UNPROCESSABLE_ENTITY );

    private String mensagem;
    private String codigo;
    private HttpStatus status;

    ErrorEnum(String mensagem, String codigo, HttpStatus status) {
        this.mensagem = mensagem;
        this.codigo = codigo;
        this.status = status;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }
}
