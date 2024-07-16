package com.goat.jose.services;

import com.goat.jose.enums.Sexo;
import com.goat.jose.enums.StatusEspecializacao;
import com.goat.jose.enums.TipoEspecializacao;
import com.goat.jose.enums.TipoServidor;
import com.goat.jose.models.EspecializacaoModel;
import com.goat.jose.models.ServidorModel;
import com.goat.jose.models.records.EspecializacaoRecord;
import com.goat.jose.repositories.EspecializacaoRepository;
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
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class EspecializacaoServiceTest {

    @Mock
    private EspecializacaoRepository especializacaoRepository;

    private EspecializacaoModel especializacaoModel;
    private EspecializacaoRecord especializacaoRecord;
    private ServidorModel servidorModel;

    @Autowired
    @InjectMocks
    private EspecializacaoService especializacaoService;

    @BeforeEach
    public void setup() {
        startEspecializacao();
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Should return all specializations successfully when everything is OK")
    public void EspecializacaoService_FindAll_Success() {
        List<EspecializacaoModel> especializacoes = mock(List.class);

        when(especializacaoRepository.findAll()).thenReturn(especializacoes);

        List<EspecializacaoModel> result = especializacaoService.findAll();

        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result).isEqualTo(especializacoes);
    }

    @Test
    @DisplayName("Should return a specialization passing its ID successfully when everything is OK")
    public void EspecializacaoService_FindById_Success() {
        when(especializacaoRepository.findById(1L)).thenReturn(Optional.ofNullable(especializacaoModel));
        EspecializacaoModel result = especializacaoService.findById(1L);

        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result).isEqualTo(especializacaoModel);
    }

    @Test
    @DisplayName("Should return a specialization passing the CPF of an employee successfully when everything is OK")
    public void EspecializacaoService_FindByCpf_Success() {
        List<EspecializacaoModel> especializacoes = mock(List.class);
        when(especializacaoRepository.findByServidorCpf(Mockito.any(String.class))).thenReturn(Optional.ofNullable(especializacoes));
        List<EspecializacaoModel> result = especializacaoService.findByServidorCpf("12345678900");

        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result).isEqualTo(especializacoes);
    }

    @Test
    @DisplayName("Should create a specialization successfully when everything is OK")
    public void EspecializacaoService_Save_Success() {
        when(especializacaoRepository.save(Mockito.any(EspecializacaoModel.class))).thenReturn(especializacaoModel);

        EspecializacaoModel result = especializacaoService.save(new EspecializacaoModel(especializacaoRecord, servidorModel));

        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result).isEqualTo(especializacaoModel);
    }

    @Test
    @DisplayName("Should delete a specialization successfully when everything is OK")
    public void EspecializacaoService_Delete_Success() {
        when(especializacaoRepository.findById(1L)).thenReturn(Optional.ofNullable(especializacaoModel));

        assertAll(() -> especializacaoService.deleteById(1L));
    }

    @Test
    @DisplayName("Should defer a status from a specialization when everything is OK")
    public void EspecializacaoService_Deferir_Success() {
        EspecializacaoModel especializacaoDeferida = new EspecializacaoModel(
                1L,
                "ciência de dados",
                TipoEspecializacao.DOUTORADO,
                200,
                5000.0,
                this.servidorModel,
                StatusEspecializacao.APROVADO,
                ""
        );

        when(especializacaoRepository.findById(1L)).thenReturn(Optional.ofNullable(especializacaoModel));
        when(especializacaoRepository.save(Mockito.any(EspecializacaoModel.class))).thenReturn(especializacaoDeferida);

        EspecializacaoModel result = especializacaoService.deferirEspecializacao(1L);

        Assertions.assertThat(result.getStatus()).isEqualTo(StatusEspecializacao.APROVADO);
        Assertions.assertThat(result.getMotivoIndeferimento()).isNull();
    }

    @Test
    @DisplayName("Should deny a status from a specialization when everything is OK")
    public void EspecializacaoService_Indeferir_Success() {
        EspecializacaoModel especializacaoIndeferida = new EspecializacaoModel(
                1L,
                "ciência de dados",
                TipoEspecializacao.DOUTORADO,
                200,
                5000.0,
                this.servidorModel,
                StatusEspecializacao.REPROVADO,
                "Nota insuficiente"
        );

        when(especializacaoRepository.findById(1L)).thenReturn(Optional.ofNullable(especializacaoModel));
        when(especializacaoRepository.save(Mockito.any(EspecializacaoModel.class))).thenReturn(especializacaoIndeferida);

        EspecializacaoModel result = especializacaoService.indeferirEspecializacao(1L, "Nota insuficiente");

        Assertions.assertThat(result.getStatus()).isEqualTo(StatusEspecializacao.REPROVADO);
        Assertions.assertThat(result.getMotivoIndeferimento()).isEqualTo("Nota insuficiente");
    }

    private void startEspecializacao() {
        this.especializacaoRecord = new EspecializacaoRecord(
                "ciência de dados",
                TipoEspecializacao.DOUTORADO,
                200,
                5000.0,
                1L,
                StatusEspecializacao.PENDENTE,
                ""
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

        this.especializacaoModel = new EspecializacaoModel(
                1L,
                "ciência de dados",
                TipoEspecializacao.DOUTORADO,
                200,
                5000.0,
                this.servidorModel,
                StatusEspecializacao.PENDENTE,
                ""
        );
    }
}
