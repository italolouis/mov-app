package br.com.movapp.services;

import retrofit2.Call;
import retrofit2.http.Header;
import retrofit2.http.POST;

/**
 * Created by alura on 12/8/16.
 */
public interface DispositivoService {
    @POST("firebase/dispositivo")
    Call<Void> enviaToken(@Header("token") String token);

}
