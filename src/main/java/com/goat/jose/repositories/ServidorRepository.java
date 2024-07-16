package com.goat.jose.repositories;

import com.goat.jose.models.ServidorModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ServidorRepository extends JpaRepository<ServidorModel, Long> {
    Optional<ServidorModel> findByCpf(String cpf);
}
