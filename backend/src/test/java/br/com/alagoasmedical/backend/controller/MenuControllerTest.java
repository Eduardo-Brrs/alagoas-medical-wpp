package br.com.alagoasmedical.backend.controller;

import br.com.alagoasmedical.backend.dto.MenuMessageRequest;
import br.com.alagoasmedical.backend.dto.MenuMessageResponse;
import br.com.alagoasmedical.backend.service.MenuService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;

@WebMvcTest(MenuController.class)
class MenuControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MenuService menuService;

    @Test
    void deveRetornar200ComRespostaValida() throws Exception {
        when(menuService.processar(any(MenuMessageRequest.class)))
                .thenReturn(new MenuMessageResponse("5582999999999", "Resposta de teste", "AGUARDAR_ATENDENTE"));

        mockMvc.perform(post("/menu/message")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"from\":\"5582999999999\",\"message\":\"1\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.to").value("5582999999999"))
                .andExpect(jsonPath("$.nextAction").value("AGUARDAR_ATENDENTE"))
                .andExpect(jsonPath("$.message").isNotEmpty());
    }

    @Test
    void deveRetornar400QuandoFromEstiverVazio() throws Exception {
        mockMvc.perform(post("/menu/message")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"from\":\"\",\"message\":\"1\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.erro").value("Requisição inválida"))
                .andExpect(jsonPath("$.detalhes").isArray());
    }

    @Test
    void deveRetornar400QuandoMessageEstiverVazio() throws Exception {
        mockMvc.perform(post("/menu/message")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"from\":\"5582999999999\",\"message\":\"\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.erro").value("Requisição inválida"));
    }

    @Test
    void deveRetornar400QuandoJsonForInvalido() throws Exception {
        mockMvc.perform(post("/menu/message")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("isso nao e json"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.erro").value("JSON inválido ou malformado"));
    }

    @Test
    void deveRetornar400QuandoBodyEstiverAusente() throws Exception {
        mockMvc.perform(post("/menu/message")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400));
    }
}
