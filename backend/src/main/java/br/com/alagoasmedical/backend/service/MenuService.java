package br.com.alagoasmedical.backend.service;

import br.com.alagoasmedical.backend.dto.MenuMessageRequest;
import br.com.alagoasmedical.backend.dto.MenuMessageResponse;
import org.springframework.stereotype.Service;

@Service
public class MenuService {

    private static final String MENU_PRINCIPAL = """
            Olá! Bem-vindo à *Alagoas Medical* 👋

            Como podemos te ajudar hoje? Escolha uma opção:

            1️⃣ - Curativos e coberturas
            2️⃣ - Terapia por pressão negativa
            3️⃣ - Produtos hospitalares
            4️⃣ - Falar com atendente

            Responda com o número da opção desejada.""";

    public MenuMessageResponse processar(MenuMessageRequest request) {
        String entrada = request.getMessage().trim();

        return switch (entrada) {
            case "1" -> new MenuMessageResponse(
                    request.getFrom(),
                    "Você escolheu *Curativos e coberturas*.\n\nTemos uma ampla linha de curativos e coberturas especializadas para tratamento de feridas. Em breve um atendente entrará em contato com mais detalhes!",
                    "AGUARDAR_ATENDENTE"
            );
            case "2" -> new MenuMessageResponse(
                    request.getFrom(),
                    "Você escolheu *Terapia por pressão negativa*.\n\nTrabalha com TPN? Temos equipamentos e insumos disponíveis. Em breve um atendente entrará em contato!",
                    "AGUARDAR_ATENDENTE"
            );
            case "3" -> new MenuMessageResponse(
                    request.getFrom(),
                    "Você escolheu *Produtos hospitalares*.\n\nContamos com um portfólio completo de produtos hospitalares. Em breve um atendente entrará em contato!",
                    "AGUARDAR_ATENDENTE"
            );
            case "4" -> new MenuMessageResponse(
                    request.getFrom(),
                    "Perfeito! Estamos te encaminhando para um atendente humano. Aguarde um momento, em breve alguém da nossa equipe irá te atender! 😊",
                    "TRANSFERIR_HUMANO"
            );
            default -> new MenuMessageResponse(
                    request.getFrom(),
                    MENU_PRINCIPAL,
                    "AGUARDAR_OPCAO"
            );
        };
    }
}
