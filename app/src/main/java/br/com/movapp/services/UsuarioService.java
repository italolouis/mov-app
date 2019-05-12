package br.com.movapp.services;
import br.com.movapp.model.Usuario;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface UsuarioService {
    @POST("usuarios")
    Call<Usuario> insere(@Body Usuario usuario);

    @GET("usuarios/{codusu}")
    Call<Usuario> buscaUsuario(@Path("codusu") Long codusu);

    @GET("usuarios")
    Call<Usuario> autenticaUsuario(
            @Query("email") String email,
            @Query("senha") String senha
    );
}
