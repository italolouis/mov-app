package br.com.movapp.services;

import br.com.movapp.dto.PessoaSync;
import br.com.movapp.model.Pessoa;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface PessoaService {
    @POST("pessoas")
    Call<Void> insere(@Body Pessoa pessoa);

    @GET("pessoas")
    Call<PessoaSync> lista();

    @GET("pessoas/{codusu}")
    Call<Pessoa> buscaUsuario(@Path("codusu") Long codusu);
}
