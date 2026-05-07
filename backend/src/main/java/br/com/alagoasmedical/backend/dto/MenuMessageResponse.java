package br.com.alagoasmedical.backend.dto;

public class MenuMessageResponse {

    private String to;
    private String message;
    private String nextAction;

    public MenuMessageResponse() {}

    public MenuMessageResponse(String to, String message, String nextAction) {
        this.to = to;
        this.message = message;
        this.nextAction = nextAction;
    }

    public String getTo() { return to; }
    public void setTo(String to) { this.to = to; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public String getNextAction() { return nextAction; }
    public void setNextAction(String nextAction) { this.nextAction = nextAction; }
}
