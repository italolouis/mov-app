package br.com.movapp.services;

import java.util.List;

import br.com.movapp.model.Exercicio;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ExercicioService {
    @POST("exercicios")
    Call<Exercicio> insere(@Body Exercicio exercicio);

    @GET("exercicios")
    Call<List<Exercicio>> getExercicios();

}
