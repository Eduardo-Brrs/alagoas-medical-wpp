package br.com.alagoasmedical.backend.dto;

import jakarta.validation.constraints.NotBlank;

public class MenuMessageRequest {

    @NotBlank(message = "O campo 'from' é obrigatório")
    private String from;

    @NotBlank(message = "O campo 'message' é obrigatório")
    private String message;

    public MenuMessageRequest() {}

    public MenuMessageRequest(String from, String message) {
        this.from = from;
        this.message = message;
    }

    public String getFrom() { return from; }
    public void setFrom(String from) { this.from = from; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
}
