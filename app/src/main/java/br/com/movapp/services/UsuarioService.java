package br.com.movapp.services;

import br.com.movapp.model.Usuario;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UsuarioService {
    @POST("usuarios")
    Call<Void> insere(@Body Usuario usuario);
}
