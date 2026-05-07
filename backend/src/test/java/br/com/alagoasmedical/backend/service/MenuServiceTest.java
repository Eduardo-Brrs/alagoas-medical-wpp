package br.com.alagoasmedical.backend.service;

import br.com.alagoasmedical.backend.dto.MenuMessageRequest;
import br.com.alagoasmedical.backend.dto.MenuMessageResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MenuServiceTest {

    private MenuService menuService;

    @BeforeEach
    void setUp() {
        menuService = new MenuService();
    }

    @Test
    void deveRetornarMenuPrincipalParaMensagemDesconhecida() {
        MenuMessageRequest request = new MenuMessageRequest("5582999999999", "oi");
        MenuMessageResponse response = menuService.processar(request);

        assertEquals("5582999999999", response.getTo());
        assertEquals("AGUARDAR_OPCAO", response.getNextAction());
        assertTrue(response.getMessage().contains("Alagoas Medical"));
        assertTrue(response.getMessage().contains("1"));
        assertTrue(response.getMessage().contains("4"));
    }

    @Test
    void deveResponderOpcao1CurativosECoberturas() {
        MenuMessageRequest request = new MenuMessageRequest("5582999999999", "1");
        MenuMessageResponse response = menuService.processar(request);

        assertEquals("5582999999999", response.getTo());
        assertEquals("AGUARDAR_ATENDENTE", response.getNextAction());
        assertTrue(response.getMessage().contains("Curativos e coberturas"));
    }

    @Test
    void deveResponderOpcao2TerapiaPorPressaoNegativa() {
        MenuMessageRequest request = new MenuMessageRequest("5582999999999", "2");
        MenuMessageResponse response = menuService.processar(request);

        assertEquals("5582999999999", response.getTo());
        assertEquals("AGUARDAR_ATENDENTE", response.getNextAction());
        assertTrue(response.getMessage().contains("TPN"));
    }

    @Test
    void deveResponderOpcao3ProdutosHospitalares() {
        MenuMessageRequest request = new MenuMessageRequest("5582999999999", "3");
        MenuMessageResponse response = menuService.processar(request);

        assertEquals("5582999999999", response.getTo());
        assertEquals("AGUARDAR_ATENDENTE", response.getNextAction());
        assertTrue(response.getMessage().contains("produtos hospitalares"));
    }

    @Test
    void deveResponderOpcao4TransferirParaAtendente() {
        MenuMessageRequest request = new MenuMessageRequest("5582999999999", "4");
        MenuMessageResponse response = menuService.processar(request);

        assertEquals("5582999999999", response.getTo());
        assertEquals("TRANSFERIR_HUMANO", response.getNextAction());
        assertTrue(response.getMessage().contains("atendente"));
    }

    @Test
    void deveRetornarMenuParaMensagemVaziaComEspacos() {
        MenuMessageRequest request = new MenuMessageRequest("5582999999999", "   ");
        MenuMessageResponse response = menuService.processar(request);

        assertEquals("AGUARDAR_OPCAO", response.getNextAction());
    }

    @Test
    void devePreservarNumeroDoRemetente() {
        String numero = "5511988887777";
        MenuMessageRequest request = new MenuMessageRequest(numero, "2");
        MenuMessageResponse response = menuService.processar(request);

        assertEquals(numero, response.getTo());
    }
}
