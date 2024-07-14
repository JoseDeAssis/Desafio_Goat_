package com.goat.jose.enums;

public enum StatusEspecializacao {
    APROVADO("Aprovado"),
    REPROVADO("Reprovado"),
    PENDENTE("Pendente");

    private String descricao;

    StatusEspecializacao(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
