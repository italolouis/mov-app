package br.com.movapp.model;

public class Serie {
    private Long cod;
    private String tipo;
    private Long repeticao;
    private Long carga;
    private Long duracao;

    public Long getCod() {
        return cod;
    }

    public void setCod(Long cod) {
        this.cod = cod;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Long getRepeticao() {
        return repeticao;
    }

    public void setRepeticao(Long repeticao) {
        this.repeticao = repeticao;
    }

    public Long getCarga() {
        return carga;
    }

    public void setCarga(Long carga) {
        this.carga = carga;
    }

    public Long getDuracao() {
        return duracao;
    }

    public void setDuracao(Long duracao) {
        this.duracao = duracao;
    }
}