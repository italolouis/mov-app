package br.com.movapp.services;

import java.util.List;

import br.com.movapp.model.Categoria;
import retrofit2.Call;
import retrofit2.http.GET;

public interface CategoriaService {
    @GET("categorias")
    Call<List<Categoria>> getCategorias();
}
