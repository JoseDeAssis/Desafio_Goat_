package com.goat.jose.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.goat.jose.enums.Sexo;
import com.goat.jose.enums.TipoServidor;
import com.goat.jose.models.ServidorModel;
import com.goat.jose.models.records.ServidorRecord;
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
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest(controllers = ServidorController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class ServidorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ServidorService servidorService;

    private ServidorModel servidorModel;
    private ServidorRecord servidorRecord;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        startServidor();
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void ServidorController_FindAll_Success() throws Exception {
        List<ServidorModel> servidores = mock(List.class);

        when(servidorService.findAll()).thenReturn(servidores);

        ResultActions response = mockMvc.perform(get("/servidores")
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()", CoreMatchers.is(servidores.size())));
    }

    @Test
    public void ServidorController_FindById_Success() throws Exception {
        when(servidorService.findById(1L)).thenReturn(servidorModel);

        ResultActions response = mockMvc.perform(get("/servidores/1")
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.cpf", CoreMatchers.is(servidorModel.getCpf())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email", CoreMatchers.is(servidorModel.getEmail())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.matricula", CoreMatchers.is(servidorModel.getMatricula())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.nome", CoreMatchers.is(servidorModel.getNome())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.dataNascimento", CoreMatchers.is(servidorModel.getDataNascimento().toString())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.sexo", CoreMatchers.is(servidorModel.getSexo().toString())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.tipoServidor", CoreMatchers.is(servidorModel.getTipoServidor().toString())));
    }

    @Test
    public void ServidorController_FindByCpf_Success() throws Exception {
        when(servidorService.findByCpf("12345678900")).thenReturn(servidorModel);

        ResultActions response = mockMvc.perform(get("/servidores/")
                .contentType(MediaType.APPLICATION_JSON)
                .param("cpf", "12345678900"));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.cpf", CoreMatchers.is(servidorModel.getCpf())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email", CoreMatchers.is(servidorModel.getEmail())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.matricula", CoreMatchers.is(servidorModel.getMatricula())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.nome", CoreMatchers.is(servidorModel.getNome())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.dataNascimento", CoreMatchers.is(servidorModel.getDataNascimento().toString())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.sexo", CoreMatchers.is(servidorModel.getSexo().toString())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.tipoServidor", CoreMatchers.is(servidorModel.getTipoServidor().toString())));
    }

    @Test
    public void ServidorController_Save_Success() throws Exception {
        given(servidorService.save(ArgumentMatchers.any())).willAnswer(invocation -> invocation.getArgument(0));

        ResultActions response = mockMvc.perform(post("/servidores")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(servidorRecord)));

        response.andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.cpf", CoreMatchers.is(servidorModel.getCpf())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email", CoreMatchers.is(servidorModel.getEmail())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.matricula", CoreMatchers.is(servidorModel.getMatricula())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.nome", CoreMatchers.is(servidorModel.getNome())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.dataNascimento", CoreMatchers.is(servidorModel.getDataNascimento().toString())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.sexo", CoreMatchers.is(servidorModel.getSexo().toString())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.tipoServidor", CoreMatchers.is(servidorModel.getTipoServidor().toString())));
    }

    @Test
    public void ServidorController_Update_Success() throws Exception {
        when(servidorService.save(servidorModel)).thenReturn(servidorModel);
        when(servidorService.findById(1L)).thenReturn(servidorModel);

        ResultActions response = mockMvc.perform(put("/servidores/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(servidorRecord)));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.cpf", CoreMatchers.is(servidorModel.getCpf())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email", CoreMatchers.is(servidorModel.getEmail())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.matricula", CoreMatchers.is(servidorModel.getMatricula())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.nome", CoreMatchers.is(servidorModel.getNome())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.dataNascimento", CoreMatchers.is(servidorModel.getDataNascimento().toString())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.sexo", CoreMatchers.is(servidorModel.getSexo().toString())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.tipoServidor", CoreMatchers.is(servidorModel.getTipoServidor().toString())));
    }

    @Test
    public void ServidorController_Delete_Success() throws Exception {
        doNothing().when(servidorService).deleteById(1L);

        ResultActions response = mockMvc.perform(delete("/servidores/1")
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isOk());
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
