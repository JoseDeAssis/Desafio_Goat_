package com.goat.jose.services;

import com.goat.jose.enums.StatusEspecializacao;
import com.goat.jose.exceptions.NotFoundEspecializacaoException;
import com.goat.jose.exceptions.NotFoundServidorException;
import com.goat.jose.models.EspecializacaoModel;
import com.goat.jose.repositories.EspecializacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class EspecializacaoService {

    @Autowired
    private EspecializacaoRepository especializacaoRepository;

    public List<EspecializacaoModel> findAll() {
        return especializacaoRepository.findAll();
    }

    public EspecializacaoModel findById(UUID id) {
        return especializacaoRepository.findById(id).orElseThrow(() ->
                new NotFoundEspecializacaoException("Nenhuma especialização com o UUID: " + id));
    }

    public List<EspecializacaoModel> findByServidorCpf(String cpf) {
        return especializacaoRepository.findByServidorCpf(cpf).orElseThrow(() ->
                new NotFoundServidorException("Nenhum servidor encontrado com o CPF: " + cpf));
    }

    public EspecializacaoModel save(EspecializacaoModel especializacao) {
        return especializacaoRepository.save(especializacao);
    }

    public void deleteById(UUID id) {
        especializacaoRepository.deleteById(id);
    }

    public EspecializacaoModel deferirEspecializacao(UUID id) {
        EspecializacaoModel especializacao = especializacaoRepository.findById(id).orElseThrow(() ->
                new NotFoundEspecializacaoException("Nenhuma especialização com o UUID: " + id));

        if(especializacao != null) {
            especializacao.setStatus(StatusEspecializacao.APROVADO);
            especializacao.setMotivoIndeferimento(null);
            especializacaoRepository.save(especializacao);
        }

        return especializacao;
    }

    public EspecializacaoModel indeferirEspecializacao(UUID id, String motivo) {
        EspecializacaoModel especializacao = especializacaoRepository.findById(id).orElseThrow(() ->
                new NotFoundEspecializacaoException("Nenhuma especialização com o UUID: " + id));

        if(especializacao != null) {
            especializacao.setStatus(StatusEspecializacao.REPROVADO);
            especializacao.setMotivoIndeferimento(motivo);
            especializacaoRepository.save(especializacao);
        }

        return especializacao;
    }
}
