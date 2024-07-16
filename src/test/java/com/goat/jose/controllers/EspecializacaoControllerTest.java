package com.goat.jose.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.goat.jose.enums.Sexo;
import com.goat.jose.enums.StatusEspecializacao;
import com.goat.jose.enums.TipoEspecializacao;
import com.goat.jose.enums.TipoServidor;
import com.goat.jose.models.EspecializacaoModel;
import com.goat.jose.models.ServidorModel;
import com.goat.jose.models.records.EspecializacaoRecord;
import com.goat.jose.repositories.EspecializacaoRepository;
import com.goat.jose.services.EspecializacaoService;
import com.goat.jose.services.ServidorService;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

@WebMvcTest(controllers = EspecializacaoController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class EspecializacaoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EspecializacaoService especializacaoService;

    @MockBean
    private EspecializacaoRepository especializacaoRepository;

    @MockBean
    private ServidorService servidorService;

    private EspecializacaoModel especializacaoModel;
    private EspecializacaoRecord especializacaoRecord;
    private ServidorModel servidorModel;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        startEspecializacao();
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void EspecializacaoController_FindAll_Success() throws Exception {
        List<EspecializacaoModel> especializacoes = mock(List.class);

        when(especializacaoService.findAll()).thenReturn(especializacoes);

        ResultActions response = mockMvc.perform(get("/especializacao")
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()", CoreMatchers.is(especializacoes.size())));
    }

    @Test
    public void EspecializacaoController_FindById_Success() throws Exception {
        when(especializacaoService.findById(1L)).thenReturn(especializacaoModel);

        ResultActions response = mockMvc.perform(get("/especializacao/1")
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.area", CoreMatchers.is(especializacaoModel.getArea())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.tipoEspecializacao", CoreMatchers.is(especializacaoModel.getTipoEspecializacao().toString())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.cargaHoraria", CoreMatchers.is(especializacaoModel.getCargaHoraria())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.valorTotalCusto", CoreMatchers.is(especializacaoModel.getValorTotalCusto())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.servidorId", CoreMatchers.is(especializacaoModel.getServidor().getServidorId().intValue())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status", CoreMatchers.is(especializacaoModel.getStatus().toString())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.motivoIndeferimento", CoreMatchers.is(especializacaoModel.getMotivoIndeferimento())));
    }

    @Test
    public void EspecializacaoController_FindByServidorCpf_Success() throws Exception {
        List<EspecializacaoModel> especializacoes = mock(List.class);
        when(especializacaoService.findByServidorCpf("12345678900")).thenReturn(especializacoes);

        ResultActions response = mockMvc.perform(get("/especializacao/")
                .contentType(MediaType.APPLICATION_JSON)
                .param("cpf", "12345678900"));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()", CoreMatchers.is(especializacoes.size())));
    }

    @Test
    public void EspecializacaoController_Save_Success() throws Exception {
        given(especializacaoService.save(ArgumentMatchers.any())).willAnswer(invocation -> invocation.getArgument(0));
        when(servidorService.findById(1L)).thenReturn(servidorModel);

        ResultActions response = mockMvc.perform(post("/especializacao")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(especializacaoRecord)));

        response.andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.area", CoreMatchers.is(especializacaoModel.getArea())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.tipoEspecializacao", CoreMatchers.is(especializacaoModel.getTipoEspecializacao().toString())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.cargaHoraria", CoreMatchers.is(especializacaoModel.getCargaHoraria())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.valorTotalCusto", CoreMatchers.is(especializacaoModel.getValorTotalCusto())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.servidorId", CoreMatchers.is(especializacaoModel.getServidor().getServidorId().intValue())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status", CoreMatchers.is(especializacaoModel.getStatus().toString())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.motivoIndeferimento", CoreMatchers.is(especializacaoModel.getMotivoIndeferimento())));
    }

    @Test
    public void EspecializacaoController_Update_Success() throws Exception {
        when(especializacaoService.save(especializacaoModel)).thenReturn(especializacaoModel);
        when(especializacaoService.findById(1L)).thenReturn(especializacaoModel);
        when(servidorService.findById(1L)).thenReturn(servidorModel);

        ResultActions response = mockMvc.perform(put("/especializacao/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(especializacaoRecord)));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.area", CoreMatchers.is(especializacaoModel.getArea())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.tipoEspecializacao", CoreMatchers.is(especializacaoModel.getTipoEspecializacao().toString())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.cargaHoraria", CoreMatchers.is(especializacaoModel.getCargaHoraria())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.valorTotalCusto", CoreMatchers.is(especializacaoModel.getValorTotalCusto())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status", CoreMatchers.is(especializacaoModel.getStatus().toString())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.motivoIndeferimento", CoreMatchers.is(especializacaoModel.getMotivoIndeferimento())));
    }

    @Test
    public void EspecializacaoController_Delete_Success() throws Exception {
        doNothing().when(especializacaoService).deleteById(1L);

        ResultActions response = mockMvc.perform(delete("/especializacao/1")
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void EspecializacaoController_Deferir_Success() throws Exception {
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
        when(especializacaoService.deferirEspecializacao(especializacaoModel.getEspecializacaoId())).thenReturn(especializacaoDeferida);
        when(especializacaoRepository.findById(1L)).thenReturn(Optional.ofNullable(especializacaoModel));
        when(especializacaoRepository.save(especializacaoDeferida)).thenReturn(especializacaoDeferida);

        ResultActions response = mockMvc.perform(put("/especializacao/1/deferir")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(especializacaoRecord)));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.area", CoreMatchers.is(especializacaoDeferida.getArea())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.tipoEspecializacao", CoreMatchers.is(especializacaoDeferida.getTipoEspecializacao().toString())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.cargaHoraria", CoreMatchers.is(especializacaoDeferida.getCargaHoraria())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.valorTotalCusto", CoreMatchers.is(especializacaoDeferida.getValorTotalCusto())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status", CoreMatchers.is(especializacaoDeferida.getStatus().toString())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.motivoIndeferimento", CoreMatchers.is(especializacaoDeferida.getMotivoIndeferimento())));
    }

    @Test
    public void EspecializacaoController_Indeferir_Success() throws Exception {
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
        when(especializacaoService.indeferirEspecializacao(especializacaoModel.getEspecializacaoId(), "Nota insuficiente")).thenReturn(especializacaoIndeferida);
        when(especializacaoRepository.findById(1L)).thenReturn(Optional.ofNullable(especializacaoModel));
        when(especializacaoRepository.save(especializacaoIndeferida)).thenReturn(especializacaoIndeferida);

        ResultActions response = mockMvc.perform(put("/especializacao/1/indeferir")
                .contentType(MediaType.APPLICATION_JSON)
                .param("motivoIndeferimento", "Nota insuficiente"));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.area", CoreMatchers.is(especializacaoIndeferida.getArea())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.tipoEspecializacao", CoreMatchers.is(especializacaoIndeferida.getTipoEspecializacao().toString())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.cargaHoraria", CoreMatchers.is(especializacaoIndeferida.getCargaHoraria())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.valorTotalCusto", CoreMatchers.is(especializacaoIndeferida.getValorTotalCusto())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status", CoreMatchers.is(especializacaoIndeferida.getStatus().toString())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.motivoIndeferimento", CoreMatchers.is(especializacaoIndeferida.getMotivoIndeferimento())));
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
