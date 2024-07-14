package com.goat.jose.enums;

public enum TipoServidor {
    PROFESSOR("Professor"),
    TECNICO("Técnico");

    private String descricao;

    TipoServidor(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
