package br.com.movapp.services;

import java.util.List;
import br.com.movapp.model.GrupoMuscular;
import retrofit2.Call;
import retrofit2.http.GET;

public interface GrupoService {
    @GET("grupos")
    Call <List<GrupoMuscular>>  getGrupos();
}
