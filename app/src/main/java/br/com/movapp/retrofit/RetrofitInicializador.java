package br.com.movapp.retrofit;

import br.com.movapp.model.Usuario;
import br.com.movapp.services.UsuarioService;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class RetrofitInicializador {
    private final Retrofit retrofit;

    public  RetrofitInicializador(){
        retrofit = new Retrofit.Builder().baseUrl("http://192.168.0.10:8080/")
                .addConverterFactory(JacksonConverterFactory.create()).build();
    }

    public UsuarioService getUsuarioSerice() {
        return retrofit.create(UsuarioService.class);
    }
}
