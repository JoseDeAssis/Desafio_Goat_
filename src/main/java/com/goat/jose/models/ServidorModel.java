package com.goat.jose.models;

import com.goat.jose.enums.Sexo;
import com.goat.jose.enums.TipoServidor;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name="TB_SERVIDOR")
public class ServidorModel implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long servidorId;

    private String cpf;

    private String email;

    private String matricula;

    private String nome;

    private LocalDateTime dataNascimento;

    @Enumerated(EnumType.STRING)
    private Sexo sexo;

    @Enumerated(EnumType.STRING)
    private TipoServidor tipoServidor;
}
