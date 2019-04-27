package br.com.movapp.retrofit;
import br.com.movapp.services.EnderecoService;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class RetrofitCep {
    private final Retrofit retrofitCep;

    public  RetrofitCep(){
        //Criação do interceptador
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        //Criação do cliente
        OkHttpClient.Builder client = new OkHttpClient.Builder();
        client.addInterceptor(interceptor);

        retrofitCep = new Retrofit.Builder()
                .baseUrl("http://viacep.com.br/ws/")
                .addConverterFactory(JacksonConverterFactory.create())
                .client(client.build())
                .build();
    }

    public EnderecoService getEndereco() {
        return retrofitCep.create(EnderecoService.class);
    }
}
