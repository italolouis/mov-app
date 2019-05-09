package br.com.movapp.services;

import java.util.List;

import br.com.movapp.model.Exercicio;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ExercicioService {
    @GET("exercicios")
    Call<List<Exercicio>> getExercicios();
}
