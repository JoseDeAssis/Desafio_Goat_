package com.goat.jose.models;

import com.goat.jose.enums.StatusEspecializacao;
import com.goat.jose.enums.TipoEspecializacao;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
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
}
