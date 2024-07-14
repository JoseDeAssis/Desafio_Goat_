package com.goat.jose.enums;

public enum TipoEspecializacao {
    POS_GRADUACAO("Pós-graduação"),
    DOUTORADO("Doutorado"),
    MESTRADO("Mestrado");

    private String descricao;

    TipoEspecializacao(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
