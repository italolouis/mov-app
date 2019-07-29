package br.com.movapp.model;

import java.util.Set;

public class Treino {

    private Long cod;
    private String dia;

    private Usuario usuario;

    private Set<Exercicio> exercicio;

    public Long getCod() {
        return cod;
    }

    public void setCod(Long cod) {
        this.cod = cod;
    }

    public String getDia() {
        return dia;
    }

    public void setDia(String dia) {
        this.dia = dia;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Set<Exercicio> getExercicios() {
        return exercicio;
    }

    public void setExercicios(Set<Exercicio> exercicio) {
        this.exercicio = exercicio;
    }

}
