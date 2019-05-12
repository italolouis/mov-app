package br.com.movapp.retrofit;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;

import br.com.movapp.controller.ExercicioController;
import br.com.movapp.controller.UsuarioController;
import br.com.movapp.model.Exercicio;
import br.com.movapp.model.Usuario;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ImportFromService {

    public static  void insetExerciciosFromService(final Context context) {
        final Call<List<Exercicio>> call = new RetrofitInicializador().getExercicioService().getExercicios();
        call.enqueue(new Callback<List<Exercicio>>() {
            @Override
            //Conseguiu conectar com o servidor
            public void onResponse(Call<List<Exercicio>>  call, Response<List<Exercicio>> response) {
                List<Exercicio> exercicios = response.body();
                ExercicioController exercicioController= new ExercicioController(context);
                if(!exercicios.isEmpty()){
                    for (Exercicio exercicio: exercicios){
                        exercicioController.insereExercicio(exercicio);
                    }
                }

            }

            //Nao deu certo a execução
            @Override
            public void onFailure(Call<List<Exercicio>> call, Throwable t) {
                Log.e("onFailure", "Requisicao falhou");
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
