package com.goat.jose.repositories;

import com.goat.jose.enums.Sexo;
import com.goat.jose.enums.TipoServidor;
import com.goat.jose.models.ServidorModel;
import com.goat.jose.models.records.ServidorRecord;
import jakarta.persistence.EntityManager;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.Optional;

@DataJpaTest
@ActiveProfiles("test")
public class ServidorRepositoryTest {

    @Autowired
    ServidorRepository servidorRepository;

    @Autowired
    EntityManager entityManager;

    @Test
    @DisplayName("Should get employee successfully from DB through their CPF")
    public void ServidorRepository_FindByCpf_Sucess() {
        ServidorRecord servidorRecord = new ServidorRecord(
                "12345678900",
                "test@gmail.com",
                "12345",
                "José Maria",
                LocalDate.of(1900, 1, 1),
                Sexo.MASCULINO,
                TipoServidor.PROFESSOR
        );
        this.createServidor(servidorRecord);

        Optional<ServidorModel> result = servidorRepository.findByCpf("12345678900");

        Assertions.assertThat(result.isPresent()).isTrue();
    }

    @Test
    @DisplayName("Should not get employee from DB through their CPF when employee not exists")
    public void ServidorRepository_FindByCpf_Failure() {
        Optional<ServidorModel> result = servidorRepository.findByCpf("12345678900");

        Assertions.assertThat(result.isEmpty()).isTrue();
    }

    private void createServidor(ServidorRecord servidorRecord) {
        ServidorModel servidorModel = new ServidorModel(servidorRecord);
        this.entityManager.persist(servidorModel);
    }
}
