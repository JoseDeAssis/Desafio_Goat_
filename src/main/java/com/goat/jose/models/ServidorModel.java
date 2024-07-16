package com.goat.jose.models;

import com.goat.jose.enums.Sexo;
import com.goat.jose.enums.TipoServidor;
import com.goat.jose.models.records.ServidorRecord;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name="TB_SERVIDOR")
public class ServidorModel implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long servidorId;

    private String cpf;

    private String email;

    private String matricula;

    private String nome;

    private LocalDate dataNascimento;

    @Enumerated(EnumType.STRING)
    private Sexo sexo;

    @Enumerated(EnumType.STRING)
    private TipoServidor tipoServidor;

    public ServidorModel(ServidorRecord servidor) {
        this.cpf = servidor.cpf();
        this.email = servidor.email();
        this.matricula = servidor.matricula();
        this.nome = servidor.nome();
        this.dataNascimento = servidor.dataNascimento();
        this.sexo = servidor.sexo();
        this.tipoServidor = servidor.tipoServidor();
    }

    public void save(Object any) {

    }
}
