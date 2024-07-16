package com.goat.jose.models.records;

import com.goat.jose.enums.StatusEspecializacao;
import com.goat.jose.enums.TipoEspecializacao;
import com.goat.jose.models.EspecializacaoModel;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record EspecializacaoRecord(
        @NotBlank String area,
        @NotNull TipoEspecializacao tipoEspecializacao,
        @NotNull int cargaHoraria,
        @NotNull double valorTotalCusto,
        @NotNull Long servidorId,
        StatusEspecializacao status,
        String motivoIndeferimento
) {

    public EspecializacaoRecord(
            String area,
            TipoEspecializacao tipoEspecializacao,
            int cargaHoraria,
            double valorTotalCusto,
            Long servidorId,
            StatusEspecializacao status,
            String motivoIndeferimento
    ) {
        this.area = area;
        this.tipoEspecializacao = tipoEspecializacao;
        this.cargaHoraria = cargaHoraria;
        this.valorTotalCusto = valorTotalCusto;
        this.servidorId = servidorId;
        this.status = status;
        this.motivoIndeferimento = motivoIndeferimento;
    }

    public EspecializacaoRecord(EspecializacaoModel especializacaoModel) {
        this(
            especializacaoModel.getArea(),
            especializacaoModel.getTipoEspecializacao(),
            especializacaoModel.getCargaHoraria(),
            especializacaoModel.getValorTotalCusto(),
            especializacaoModel.getServidor().getServidorId(),
            especializacaoModel.getStatus(),
            especializacaoModel.getMotivoIndeferimento()
        );
    }
}
