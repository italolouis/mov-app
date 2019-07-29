package br.com.movapp.retrofit;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import br.com.movapp.controller.CategoriaController;
import br.com.movapp.controller.ExercicioController;
import br.com.movapp.controller.TipoExercicioController;
import br.com.movapp.controller.SerieController;
import br.com.movapp.controller.TreinoController;
import br.com.movapp.model.Categoria;
import br.com.movapp.model.Exercicio;
import br.com.movapp.model.Serie;
import br.com.movapp.model.TipoExercicio;
import br.com.movapp.model.Treino;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ImportFromService {


    public static void inicializaGetFromService(Context context){
        getTipoExerciciosFromService(context);
        getCategoriesFromService(context);
        getSeriesFromService(context);
        getExerciciosFromService(context);
    }

    public static void getTipoExerciciosFromService(final Context context) {
        final Call<List<TipoExercicio>> call = new RetrofitInicializador().getTipoExercicioService().getExercicios();
        call.enqueue(new Callback<List<TipoExercicio>>() {
            @Override
            //Conseguiu conectar com o servidor
            public void onResponse(Call<List<TipoExercicio>>  call, Response<List<TipoExercicio>> response) {
                List<TipoExercicio> tipoExercicios = response.body();
                TipoExercicioController tipoExercicioController = new TipoExercicioController(context);
                if(tipoExercicios != null){
                    for (TipoExercicio tipoExercicio : tipoExercicios){
                        tipoExercicioController.insereExercicio(tipoExercicio);

                        if(tipoExercicio.getCategorias() != null){
                            if(!tipoExercicio.getCategorias().isEmpty()){
                                for(Categoria categoria: tipoExercicio.getCategorias()){
                                    tipoExercicioController.insereExercicioCategoria(categoria, tipoExercicio.getCod());
                                }
                            }
                        }
                    }
                }
            }

            //Nao deu certo a execução
            @Override
            public void onFailure(Call<List<TipoExercicio>> call, Throwable t) {
                Log.e("onFailure", "Requisicao falhou");
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static  void getCategoriesFromService(final Context context) {
        final Call<List<Categoria>> call = new RetrofitInicializador().getCategoriasService().getCategorias();
        call.enqueue(new Callback<List<Categoria>>() {
            @Override
            //Conseguiu conectar com o servidor
            public void onResponse(Call<List<Categoria>>  call, Response<List<Categoria>> response) {
                List<Categoria> categorias = response.body();
                CategoriaController categoriaController= new CategoriaController(context);
                if(!categorias.isEmpty()){
                    for (Categoria categoria: categorias){
                        if(!categoria.getSubCategorias().isEmpty()){
                            categoriaController.insereCategoria(categoria, null);

                            Long parentId = categoria.getCod();
                            for(Categoria subCategorias: categoria.getSubCategorias()){
                                categoriaController.insereCategoria(subCategorias, parentId);
                            }
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Categoria>> call, Throwable t) {
                Log.e("onFailure", "Requisicao falhou");
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static  void getSeriesFromService(final Context context) {
        final Call<List<Serie>> call = new RetrofitInicializador().getSerieService().getSeries();
        call.enqueue(new Callback<List<Serie>>() {
            @Override
            //Conseguiu conectar com o servidor
            public void onResponse(Call<List<Serie>>  call, Response<List<Serie>> response) {
                List<Serie> series = response.body();
                SerieController serieController= new SerieController(context);
                if(!series.isEmpty()){
                    for (Serie serie: series){
                        serieController.insereSerie(serie);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Serie>> call, Throwable t) {
                Log.e("onFailure", "Requisicao falhou");
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static void insereSerieToService(final Context context, Serie serie) {
        final SerieController serieController = new SerieController(context);

        final Call<Serie> call = new RetrofitInicializador().getSerieService().insere(serie);
        call.enqueue(new Callback<Serie>() {
            @Override
            //Conseguiu conectar com o servidor
            public void onResponse(Call<Serie>  call, Response<Serie> response) {
                Serie serie = response.body();
                serieController.insereSerie(serie);
            }

            //Nao deu certo a execução
            @Override
            public void onFailure(Call<Serie> call, Throwable t) {
                Log.e("onFailure", "Requisicao falhou");
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static Exercicio insereExerciciosToService(final ExercicioController exercicioController, final Exercicio exercicio){
        Exercicio exercicioResp = new Exercicio();
        Call<Exercicio> call = new RetrofitInicializador().getExercicioService().insere(exercicio);
        try {
            Thread.sleep(2000);

            Response<Exercicio> response = call.execute();
            exercicioResp = response.body();

            if(exercicioResp != null){
                exercicioController.insereExercicio(exercicioResp);
                return exercicioResp;
            }
        } catch (IOException e){
            return null;
        }catch (InterruptedException e) {
            return null;
        }
        return exercicioResp;
    }

    public static void getExerciciosFromService(final Context context) {
        final ExercicioController exercicioController = new ExercicioController(context);
        Call<List<Exercicio>> call = new RetrofitInicializador().getExercicioService().getExercicios();
        call.enqueue(new Callback<List<Exercicio>>() {
            @Override
            public void onResponse(Call<List<Exercicio>>  call, Response<List<Exercicio>> response) {
                List<Exercicio> exercicioResponse = response.body();
                for (Exercicio exercicio: exercicioResponse){
                    exercicioController.insereExercicio(exercicio);
                }
            }

            @Override
            public void onFailure(Call<List<Exercicio>> call, Throwable t) {
                Log.e("onFailure", "Requisicao falhou");
            }
        });
    }

    public static void insereTreinoToService(final TreinoController treinoController, Treino treino) {
        Call<Treino> call = new RetrofitInicializador().getTreinoService().insere(treino);
        call.enqueue(new Callback<Treino>() {
            @Override
            public void onResponse(Call<Treino>  call, Response<Treino> response) {
                Treino treinoResponse = response.body();
                treinoController.insereTreino(treinoResponse);
            }

            @Override
            public void onFailure(Call<Treino> call, Throwable t) {
                Log.e("onFailure", "Requisicao falhou");
            }
        });
    }

    public static void getTreinoFromService(final Context context) {
        final TreinoController treinoController = new TreinoController(context);
        Call<List<Treino>> call = new RetrofitInicializador().getTreinoService().getTreino();
        call.enqueue(new Callback<List<Treino>>() {
            @Override
            public void onResponse(Call<List<Treino>>  call, Response<List<Treino>> response) {
                List<Treino> treinoResponse = response.body();
                for (Treino treino: treinoResponse){
                    treinoController.insereTreino(treino);
                }
            }

            @Override
            public void onFailure(Call<List<Treino>> call, Throwable t) {
                Log.e("onFailure", "Requisicao falhou");
            }
        });
    }
}
