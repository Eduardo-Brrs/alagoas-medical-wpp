package br.com.alagoasmedical.backend.controller;

import br.com.alagoasmedical.backend.dto.MenuMessageRequest;
import br.com.alagoasmedical.backend.dto.MenuMessageResponse;
import br.com.alagoasmedical.backend.service.MenuService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/menu")
public class MenuController {

    private final MenuService menuService;

    public MenuController(MenuService menuService) {
        this.menuService = menuService;
    }

    @PostMapping("/message")
    public ResponseEntity<MenuMessageResponse> processarMensagem(
            @RequestBody @Valid MenuMessageRequest request) {
        MenuMessageResponse response = menuService.processar(request);
        return ResponseEntity.ok(response);
    }
}
