package com.goat.jose.repositories;

import com.goat.jose.models.EspecializacaoModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface EspecializacaoRepository extends JpaRepository<EspecializacaoModel, UUID> {
    Optional<List<EspecializacaoModel>> findByServidorCpf(String cpf);
}
