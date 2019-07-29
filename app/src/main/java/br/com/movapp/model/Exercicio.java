package br.com.movapp.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Set;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Exercicio {
    private Long cod;
    private Long exerciciocod;
    private TipoExercicio tipoExercicios;
    private Set<Serie> series;
    private Long descanso;

    public Long getCod() {
        return cod;
    }

    public void setCod(Long cod) {
        this.cod = cod;
    }

    public Long getExerciciocod() {
        return exerciciocod;
    }

    public void setExerciciocod(Long exerciciocod) {
        this.exerciciocod = exerciciocod;
    }

    public Set<Serie> getSeries() {
        return series;
    }

    public void setSeries(Set<Serie> series) {
        this.series = series;
    }

    public TipoExercicio getTipoExercicios() {
        return tipoExercicios;
    }

    public void setTipoExercicios(TipoExercicio tipoExercicios) {
        this.tipoExercicios = tipoExercicios;
    }

    public Long getDescanso() {
        return descanso;
    }

    public void setDescanso(Long descanso) {
        this.descanso = descanso;
    }

}
