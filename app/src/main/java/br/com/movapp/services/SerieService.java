package br.com.movapp.services;

import java.util.List;

import br.com.movapp.model.Serie;
import br.com.movapp.model.TipoExercicio;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface SerieService {
    @POST("serie")
    Call<Serie> insere(@Body Serie serie);

    @GET("serie")
    Call<List<Serie>> getSeries();
}
