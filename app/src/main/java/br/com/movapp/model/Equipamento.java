package br.com.movapp.model;

public class Equipamento {

    private Long codequip;
    private String descricao;

    public Long getCodequip() {
        return codequip;
    }

    public void setCodequip(Long codequip) {
        this.codequip = codequip;
    }
    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public String toString() {
        return descricao;
    }
}
