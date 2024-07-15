package com.goat.jose.models;

import com.goat.jose.enums.Sexo;
import com.goat.jose.enums.TipoServidor;
import com.goat.jose.models.records.ServidorRecord;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Table(name="TB_SERVIDOR")
public class ServidorModel implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID servidorId;

    private String cpf;

    private String email;

    private String matricula;

    private String nome;

    private LocalDateTime dataNascimento;

    @Enumerated(EnumType.STRING)
    private Sexo sexo;

    @Enumerated(EnumType.STRING)
    private TipoServidor tipoServidor;

    public  ServidorModel() {}

    public ServidorModel(ServidorRecord servidor) {
        this.cpf = servidor.cpf();
        this.email = servidor.email();
        this.matricula = servidor.matricula();
        this.nome = servidor.nome();
        this.dataNascimento = servidor.dataNascimento();
        this.sexo = servidor.sexo();
        this.tipoServidor = servidor.tipoServidor();
    }
}
