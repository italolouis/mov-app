package br.com.movapp.services;

import java.util.List;

import br.com.movapp.model.Treino;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface TreinoService {
    @POST("treino")
    Call<Treino> insere(@Body Treino treino);

    @GET("treino")
    Call<List<Treino>> getTreino();

}
