package br.com.movapp.model;

public class GrupoMuscular {
    private Long codgrupo;
    private String descricao;

    public Long getCodgrupo() {
        return codgrupo;
    }

    public void setCodgrupo(Long codgrupo) {
        this.codgrupo = codgrupo;
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
