package br.com.alagoasmedical.backend.dto;

import java.util.List;

public class ErroResponse {

    private int status;
    private String erro;
    private List<String> detalhes;

    public ErroResponse(int status, String erro, List<String> detalhes) {
        this.status = status;
        this.erro = erro;
        this.detalhes = detalhes;
    }

    public int getStatus() { return status; }
    public String getErro() { return erro; }
    public List<String> getDetalhes() { return detalhes; }
}
