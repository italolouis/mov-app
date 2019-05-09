package br.com.movapp.controller;

import java.io.IOException;

import br.com.movapp.dao.UsuarioDAO;
import br.com.movapp.model.Usuario;
import br.com.movapp.retrofit.RetrofitInicializador;
import retrofit2.Call;
import retrofit2.Response;

public class LoginController {

    public static boolean autenticaLogin(UsuarioDAO usuarioDAO, String mEmail, String mPassword){
        Call<Usuario> call = new RetrofitInicializador().getUsuarioSerice().autenticaUsuario(mEmail, mPassword);
        try {
            // Simulate network access.
            Thread.sleep(2000);
            //Chamada sincrona
            Response<Usuario> response = call.execute();
            Usuario usuario = response.body();
            if(usuario != null){
                usuarioDAO.deletaUsuarios();
                usuarioDAO.insereUsuario(usuario);
                usuarioDAO.close();
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
