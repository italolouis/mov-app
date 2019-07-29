package br.com.movapp.services;

import java.util.List;

import br.com.movapp.model.TipoExercicio;
import retrofit2.Call;
import retrofit2.http.GET;

public interface TipoExercicioService {
    @GET("tipoExercicios")
    Call<List<TipoExercicio>> getExercicios();
}
