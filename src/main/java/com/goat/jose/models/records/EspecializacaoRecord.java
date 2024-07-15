package com.goat.jose.models.records;

import com.goat.jose.enums.StatusEspecializacao;
import com.goat.jose.enums.TipoEspecializacao;
import com.goat.jose.models.EspecializacaoModel;
import jakarta.validation.constraints.NotBlank;

import java.util.UUID;

public record EspecializacaoRecord(
        @NotBlank String area,
        @NotBlank TipoEspecializacao tipoEspecializacao,
        @NotBlank int cargaHoraria,
        @NotBlank double valorTotalCusto,
        @NotBlank UUID servidorId,
        StatusEspecializacao status,
        String motivoIndeferimento
) {

    public EspecializacaoRecord(
            String area,
            TipoEspecializacao tipoEspecializacao,
            int cargaHoraria,
            double valorTotalCusto,
            UUID servidorId,
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
