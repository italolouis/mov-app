package br.com.movapp.services;

import java.util.List;

import br.com.movapp.model.Equipamento;
import retrofit2.Call;
import retrofit2.http.GET;

public interface EquipamentoService {

    @GET("equipamentos")
    Call<List<Equipamento>> getEquipamentos();
}
