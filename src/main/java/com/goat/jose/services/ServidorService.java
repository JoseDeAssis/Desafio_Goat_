package com.goat.jose.services;

import com.goat.jose.exceptions.NotFoundServidorException;
import com.goat.jose.models.ServidorModel;
import com.goat.jose.repositories.ServidorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ServidorService {

    @Autowired
    private ServidorRepository servidorRepository;

    public List<ServidorModel> findAll() {
        return servidorRepository.findAll();
    }

    public ServidorModel findById(Long id) {
        return servidorRepository.findById(id).orElseThrow(() -> new NotFoundServidorException("Nenhum servidor encontrado com o ID: " + id));
    }

    public ServidorModel findByCpf(String cpf) {
        return servidorRepository.findByCpf(cpf).orElseThrow(() -> new NotFoundServidorException("Nenhum servidor encontrado com o CPF: " + cpf));
    }

    public ServidorModel save(ServidorModel servidor) {
        return servidorRepository.save(servidor);
    }

    public void deleteById(Long id) {
        servidorRepository.deleteById(id);
    }
}
