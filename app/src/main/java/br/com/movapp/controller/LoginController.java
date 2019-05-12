package br.com.movapp.controller;

import java.io.IOException;

import br.com.movapp.model.Usuario;
import br.com.movapp.retrofit.RetrofitInicializador;
import retrofit2.Call;
import retrofit2.Response;

public class LoginController {

    public static boolean autenticaLogin(UsuarioController usuarioController, String mEmail, String mPassword){
        Call<Usuario> call = new RetrofitInicializador().getUsuarioSerice().autenticaUsuario(mEmail, mPassword);
        try {
            // Simulate network access.
            Thread.sleep(2000);
            //Chamada sincrona
            Response<Usuario> response = call.execute();
            Usuario usuario = response.body();
            if(usuario != null){
                usuarioController.deletaUsuarios();
                usuarioController.insereUsuario(usuario);
                return true;
            }
        } catch (IOException e){
            return false;
        }catch (InterruptedException e) {
            return false;
        }
        return  false;
    }
}
