package br.com.movapp.retrofit;

import br.com.movapp.services.DispositivoService;
import br.com.movapp.services.EquipamentoService;
import br.com.movapp.services.ExercicioService;
import br.com.movapp.services.GrupoService;
import br.com.movapp.services.UsuarioService;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class RetrofitInicializador {
    private final Retrofit retrofit;

    public  RetrofitInicializador(){
        //Criação do interceptador
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        //Criação do cliente
        OkHttpClient.Builder client = new OkHttpClient.Builder();
        client.addInterceptor(interceptor);

        retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.0.10:8080/")
                .addConverterFactory(JacksonConverterFactory.create())
                .client(client.build())
                .build();
    }

    public UsuarioService getUsuarioSerice() {
        return retrofit.create(UsuarioService.class);
    }

    public GrupoService getGrupoSerice() {
        return retrofit.create(GrupoService.class);
    }

    public EquipamentoService getEquipamentoService() {
        return retrofit.create(EquipamentoService.class);
    }

    public ExercicioService getExercicioService() {
        return retrofit.create(ExercicioService.class);
    }
    public DispositivoService getDispositivoService() {
        return retrofit.create(DispositivoService.class);
    }


}
