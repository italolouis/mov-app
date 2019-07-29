package br.com.movapp.retrofit;

import br.com.movapp.services.CategoriaService;
import br.com.movapp.services.DispositivoService;
import br.com.movapp.services.ExercicioService;
import br.com.movapp.services.SerieService;
import br.com.movapp.services.TipoExercicioService;
import br.com.movapp.services.TreinoService;
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

    public TipoExercicioService getTipoExercicioService() {
        return retrofit.create(TipoExercicioService.class);
    }

    public CategoriaService getCategoriasService() {
        return retrofit.create(CategoriaService.class);
    }

    public ExercicioService getExercicioService(){
        return retrofit.create(ExercicioService.class);
    }

    public TreinoService getTreinoService(){
        return retrofit.create(TreinoService.class);
    }

    public SerieService getSerieService() {
        return retrofit.create(SerieService.class);
    }
    public DispositivoService getDispositivoService() {
        return retrofit.create(DispositivoService.class);
    }


}
