package com.goat.jose.controllers;

import com.goat.jose.exceptions.NotFoundServidorException;
import com.goat.jose.models.ServidorModel;
import com.goat.jose.models.records.ServidorRecord;
import com.goat.jose.services.ServidorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/servidores")
public class ServidorController {

    @Autowired
    private ServidorService servidorService;

    @GetMapping
    public ResponseEntity<?> findAll() {
        try {
            List<ServidorModel> listaServidores = servidorService.findAll();
            if(listaServidores.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            List<ServidorRecord> listaServidoresRecord =
                    listaServidores
                            .stream()
                            .map(ServidorRecord::new)
                            .collect(Collectors.toList());
            return new ResponseEntity<>(listaServidoresRecord, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        try {
            ServidorModel servidorModel = servidorService.findById(id);
            ServidorRecord servidorRecord = new ServidorRecord(servidorModel);
            return new ResponseEntity<>(servidorRecord, HttpStatus.OK);
        } catch (NotFoundServidorException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/")
    public ResponseEntity<?> findByCpf(@RequestParam(value = "cpf") String cpf) {
        try {
            ServidorModel servidorModel = servidorService.findByCpf(cpf);
            ServidorRecord servidorRecord = new ServidorRecord(servidorModel);
            return new ResponseEntity<>(servidorRecord, HttpStatus.OK);
        }catch (NotFoundServidorException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<?> saveServidor(@RequestBody @Valid ServidorRecord servidor) {
        try {
            ServidorModel servidorModel = new ServidorModel(servidor);
            ServidorModel servidorModeCreated = servidorService.save(servidorModel);
            return new ResponseEntity<>(new ServidorRecord(servidorModeCreated), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateServidorById(@PathVariable Long id, @RequestBody @Valid ServidorRecord servidor) {
        try {
            ServidorModel servidorModel = servidorService.findById(id);
            servidorModel.setCpf(servidor.cpf());
            servidorModel.setEmail(servidor.email());
            servidorModel.setMatricula(servidor.matricula());
            servidorModel.setNome(servidor.nome());
            servidorModel.setDataNascimento(servidor.dataNascimento());
            servidorModel.setSexo(servidor.sexo());
            servidorModel.setTipoServidor(servidor.tipoServidor());

            ServidorModel servidorModeUpdated = servidorService.save(servidorModel);
            return new ResponseEntity<>(new ServidorRecord(servidorModeUpdated), HttpStatus.OK);
        } catch (NotFoundServidorException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        try {
            servidorService.deleteById(id);
            return new ResponseEntity<>("Servidor deletado com sucesso!", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
