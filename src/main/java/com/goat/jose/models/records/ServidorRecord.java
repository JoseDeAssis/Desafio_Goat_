package com.goat.jose.models.records;

import com.goat.jose.enums.Sexo;
import com.goat.jose.enums.TipoServidor;
import com.goat.jose.models.ServidorModel;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record ServidorRecord(
        @NotBlank String cpf,
        @NotBlank @Email String email,
        @NotBlank String matricula,
        @NotBlank String nome,
        @NotNull LocalDate dataNascimento,
        @NotNull Sexo sexo,
        @NotNull TipoServidor tipoServidor
) {

    public ServidorRecord(String cpf,
                          String email,
                          String matricula,
                          String nome,
                          LocalDate dataNascimento,
                          Sexo sexo,
                          TipoServidor tipoServidor) {
        this.cpf = cpf;
        this.email = email;
        this.matricula = matricula;
        this.nome = nome;
        this.dataNascimento = dataNascimento;
        this.sexo = sexo;
        this.tipoServidor = tipoServidor;
    }

    public ServidorRecord(ServidorModel servidorModel) {
        this(
            servidorModel.getCpf(),
            servidorModel.getEmail(),
            servidorModel.getMatricula(),
            servidorModel.getNome(),
            servidorModel.getDataNascimento(),
            servidorModel.getSexo(),
            servidorModel.getTipoServidor()
        );
    }
}
