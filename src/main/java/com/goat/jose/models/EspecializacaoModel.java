package com.goat.jose.models;

import com.goat.jose.enums.StatusEspecializacao;
import com.goat.jose.enums.TipoEspecializacao;
import com.goat.jose.models.records.EspecializacaoRecord;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name="TB_ESPECIALIZACAO")
public class EspecializacaoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long especializacaoId;

    private String area;

    @Enumerated(EnumType.STRING)
    private TipoEspecializacao tipoEspecializacao;

    private int cargaHoraria;

    private double valorTotalCusto;

    @ManyToOne
    @JoinColumn(name = "servidor_id")
    private ServidorModel servidor;

    @Enumerated(EnumType.STRING)
    private StatusEspecializacao status;

    @Column(columnDefinition = "TEXT")
    private String motivoIndeferimento;

    public EspecializacaoModel(EspecializacaoRecord especializacaoRecord, ServidorModel servidorModel) {
        this.area = especializacaoRecord.area();
        this.tipoEspecializacao = especializacaoRecord.tipoEspecializacao();
        this.cargaHoraria = especializacaoRecord.cargaHoraria();
        this.valorTotalCusto = especializacaoRecord.valorTotalCusto();
        this.servidor = servidorModel;
        this.status = especializacaoRecord.status();
        this.motivoIndeferimento = especializacaoRecord.motivoIndeferimento();
    }
}
