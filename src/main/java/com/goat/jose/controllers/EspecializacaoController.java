package com.goat.jose.controllers;

import com.goat.jose.enums.StatusEspecializacao;
import com.goat.jose.exceptions.NotFoundEspecializacaoException;
import com.goat.jose.exceptions.NotFoundServidorException;
import com.goat.jose.models.EspecializacaoModel;
import com.goat.jose.models.ServidorModel;
import com.goat.jose.models.records.EspecializacaoRecord;
import com.goat.jose.services.EspecializacaoService;
import com.goat.jose.services.ServidorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/especializacao")
public class EspecializacaoController {

    @Autowired
    private EspecializacaoService especializacaoService;

    @Autowired
    private ServidorService servidorService;

    @GetMapping
    public ResponseEntity<?> findAll() {
        try {
            List<EspecializacaoModel> listaEspecializacao = especializacaoService.findAll();
            List<EspecializacaoRecord> listaEspecializacaoRecord =
                    listaEspecializacao
                            .stream()
                            .map(EspecializacaoRecord::new)
                            .collect(Collectors.toList());

            return new ResponseEntity<>(listaEspecializacaoRecord, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable UUID id) {
        try {
            EspecializacaoModel especializacaoModel = especializacaoService.findById(id);
            return new ResponseEntity<>(new EspecializacaoRecord(especializacaoModel), HttpStatus.OK);
        } catch (NotFoundEspecializacaoException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{cpf}")
    public ResponseEntity<?> findByServidorCpf(@PathVariable String cpf) {
        try {
            List<EspecializacaoModel> listaEspecializacaoModel = especializacaoService.findByServidorCpf(cpf);
            List<EspecializacaoRecord> listaEspecializacaoRecord =
                    listaEspecializacaoModel
                            .stream()
                            .map(EspecializacaoRecord::new)
                            .collect(Collectors.toList());

            return new ResponseEntity<>(listaEspecializacaoRecord, HttpStatus.OK);
        } catch (NotFoundServidorException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody @Valid EspecializacaoRecord especializacao) {
        try {
            ServidorModel servidorModel = servidorService.findById(especializacao.servidorId());
            EspecializacaoModel especializacaoModel = new EspecializacaoModel(especializacao, servidorModel);
            especializacaoModel.setStatus(StatusEspecializacao.PENDENTE);
            EspecializacaoModel especializacaoModeCreated = especializacaoService.save(especializacaoModel);

            return new ResponseEntity<>(new EspecializacaoRecord(especializacaoModeCreated), HttpStatus.CREATED);
        } catch (NotFoundServidorException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateById(@PathVariable UUID id, @RequestBody @Valid EspecializacaoRecord especializacao) {
        try {
            EspecializacaoModel especializacaoModel = especializacaoService.findById(id);
            especializacaoModel.setArea(especializacao.area());
            especializacaoModel.setTipoEspecializacao(especializacao.tipoEspecializacao());
            especializacaoModel.setCargaHoraria(especializacao.cargaHoraria());
            especializacaoModel.setValorTotalCusto(especializacao.valorTotalCusto());

            if(!especializacao.servidorId().equals(especializacaoModel.getServidor().getServidorId())) {
                ServidorModel servidorModel = servidorService.findById(especializacao.servidorId());
                especializacaoModel.setServidor(servidorModel);
            }
            EspecializacaoModel especializacaoModeUpdated = especializacaoService.save(especializacaoModel);

            return new ResponseEntity<>(especializacaoModeUpdated, HttpStatus.OK);
        } catch (NotFoundServidorException | NotFoundEspecializacaoException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable UUID id) {
        try {
            especializacaoService.deleteById(id);
            return new ResponseEntity<>("Especialização deletada com sucesso!", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}/deferir")
    public ResponseEntity<?> deferirEspecializacao(@PathVariable UUID id) {
        try {
            EspecializacaoModel especializacaoModel = especializacaoService.deferirEspecializacao(id);
            return new ResponseEntity<>(new EspecializacaoRecord(especializacaoModel), HttpStatus.OK);
        } catch (NotFoundEspecializacaoException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("{id}/indeferir")
    public ResponseEntity<?> indeferirEspecializacao(@PathVariable UUID id, @RequestBody String motivo) {
        try {
            EspecializacaoModel especializacaoModel = especializacaoService.indeferirEspecializacao(id, motivo);
            return new ResponseEntity<>(new EspecializacaoRecord(especializacaoModel), HttpStatus.OK);
        } catch (NotFoundEspecializacaoException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
