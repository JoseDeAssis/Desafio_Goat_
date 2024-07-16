package com.goat.jose.repositories;

import com.goat.jose.enums.Sexo;
import com.goat.jose.enums.StatusEspecializacao;
import com.goat.jose.enums.TipoEspecializacao;
import com.goat.jose.enums.TipoServidor;
import com.goat.jose.models.EspecializacaoModel;
import com.goat.jose.models.ServidorModel;
import com.goat.jose.models.records.EspecializacaoRecord;
import com.goat.jose.models.records.ServidorRecord;
import jakarta.persistence.EntityManager;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@DataJpaTest
@ActiveProfiles("test")
public class EspecializacaoRepositoryTest {

    @Autowired
    EspecializacaoRepository especializacaoRepository;

    @Autowired
    EntityManager entityManager;

    @Test
    @DisplayName("Should get specialization by CPF successfully from DB")
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
        EspecializacaoRecord especializacaoRecord = new EspecializacaoRecord(
                "ciência de dados",
                TipoEspecializacao.DOUTORADO,
                200,
                5000,
                1L,
                StatusEspecializacao.PENDENTE,
                null
        );
        this.createEspecializacao(especializacaoRecord, servidorRecord);

        Optional<List<EspecializacaoModel>> result = especializacaoRepository.findByServidorCpf("12345678900");

        Assertions.assertThat(result.isPresent()).isTrue();
    }

    @Test
    @DisplayName("Should not get specialization by CPF from DB when employee not exists")
    public void ServidorRepository_FindByCpf_Failure() {
        Optional<List<EspecializacaoModel>> result = especializacaoRepository.findByServidorCpf("12345678900");

        Assertions.assertThat(result.isPresent()).isTrue();
        Assertions.assertThat(result.get().size()).isEqualTo(0);
    }

    private void createEspecializacao(EspecializacaoRecord especializacaoRecord, ServidorRecord servidorRecord) {
        ServidorModel servidorModel = new ServidorModel(servidorRecord);
        EspecializacaoModel especializacaoModel = new EspecializacaoModel(especializacaoRecord, servidorModel);
        this.entityManager.persist(servidorModel);
        this.entityManager.persist(especializacaoModel);
    }
}
