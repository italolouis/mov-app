package br.com.movapp.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Set;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Categoria {
    private Long cod;
    private String nome;
    private Set<TipoExercicio> tipoExercicios;
    private Set<Categoria> subCategorias;

    public Long getCod() {
        return cod;
    }

    public void setCod(Long cod) {
        this.cod = cod;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Set<TipoExercicio> getTipoExercicios() {
        return tipoExercicios;
    }

    public void setTipoExercicios(Set<TipoExercicio> tipoExercicios) {
        this.tipoExercicios = tipoExercicios;
    }

    public Set<Categoria> getSubCategorias() {
        return subCategorias;
    }

    public void setSubCategorias(Set<Categoria> subCategorias) {
        this.subCategorias = subCategorias;
    }
}
