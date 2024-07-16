package com.goat.jose.services;

import com.goat.jose.enums.Sexo;
import com.goat.jose.enums.TipoServidor;
import com.goat.jose.models.ServidorModel;
import com.goat.jose.models.records.ServidorRecord;
import com.goat.jose.repositories.ServidorRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.*;

public class ServidorServiceTest {

    @Mock
    private ServidorRepository servidorRepository;

    private ServidorModel servidorModel;
    private ServidorRecord servidorRecord;

    @Autowired
    @InjectMocks
    private ServidorService servidorService;

    @BeforeEach
    public void setup() {
        startServidor();
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Should return all employees successfully when everything is OK")
    public void ServidorService_FindAll_Success() {
        List<ServidorModel> servidores = mock(List.class);

        when(servidorRepository.findAll()).thenReturn(servidores);

        List<ServidorModel> result = servidorService.findAll();

        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result).isEqualTo(servidores);
    }

    @Test
    @DisplayName("Should return an employee passing their ID successfully when everything is OK")
    public void ServidorService_FindById_Success() {
        when(servidorRepository.findById(1L)).thenReturn(Optional.ofNullable(servidorModel));
        ServidorModel result = servidorService.findById(1L);

        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result).isEqualTo(servidorModel);
    }

    @Test
    @DisplayName("Should return an employee passing their CPF successfully when everything is OK")
    public void ServidorService_FindByCpf_Success() {
        when(servidorRepository.findByCpf(Mockito.any(String.class))).thenReturn(Optional.ofNullable(servidorModel));
        ServidorModel result = servidorService.findByCpf("12345678900");

        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result).isEqualTo(servidorModel);
    }

    @Test
    @DisplayName("Should create an employee successfully when everything is OK")
    public void ServidorService_Save_Success() {
        when(servidorRepository.save(Mockito.any(ServidorModel.class))).thenReturn(servidorModel);

        ServidorModel result = servidorService.save(new ServidorModel(servidorRecord));

        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result).isEqualTo(servidorModel);
    }

    @Test
    @DisplayName("Should delete an employee successfully when everything is OK")
    public void ServidorService_Delete_Success() {
        when(servidorRepository.findById(1L)).thenReturn(Optional.ofNullable(servidorModel));

        assertAll(() -> servidorService.deleteById(1L));
    }

    private void startServidor() {
        this.servidorRecord = new ServidorRecord(
                "12345678900",
                "test@gmail.com",
                "12345",
                "José Maria",
                LocalDate.of(1900, 1, 1),
                Sexo.MASCULINO,
                TipoServidor.PROFESSOR
        );

        this.servidorModel = new ServidorModel(
                1L,
                "12345678900",
                "test@gmail.com",
                "12345",
                "José Maria",
                LocalDate.of(1900, 1, 1),
                Sexo.MASCULINO,
                TipoServidor.PROFESSOR
        );
    }
}
