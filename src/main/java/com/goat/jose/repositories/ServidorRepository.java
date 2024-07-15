package com.goat.jose.repositories;

import com.goat.jose.models.ServidorModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ServidorRepository extends JpaRepository<ServidorModel, UUID> {
    Optional<ServidorModel> findByCpf(String cpf);
}
