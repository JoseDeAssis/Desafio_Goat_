package com.goat.jose.enums;

public enum Sexo {
    MASCULINO("Masculino"),
    FEMININO("Feminino"),
    PREFIRO_NAO_DIZER("Prefiro não dizer");

    private String descricao;

    Sexo(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return this.descricao;
    }
}
