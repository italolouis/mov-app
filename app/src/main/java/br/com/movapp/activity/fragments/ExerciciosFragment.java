package br.com.movapp.activity.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.com.movapp.activity.FiltrarActivity;
import br.com.movapp.model.Exercicio;
import br.com.movapp.retrofit.RetrofitInicializador;
import br.com.movapp.utils.AutoFitGrid;
import br.com.movapp.R;
import br.com.movapp.adapter.RecyclerViewAdapter;
import br.com.movapp.model.DataModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ExerciciosFragment extends Fragment implements RecyclerViewAdapter.ItemListener, View.OnClickListener{
    private RecyclerView recyclerView;
    private ImageView imageViewBuscar;
    private ImageView imageViewFiltrar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_exercicios, container, false);
        ArrayList<DataModel> arrayList;

        imageViewBuscar = (ImageView) view.findViewById(R.id.imageViewBuscar);

        imageViewFiltrar = (ImageView) view.findViewById(R.id.imageViewFiltrar);
        imageViewFiltrar.setOnClickListener(this);

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        popularExercicios(container);

        return view;
    }

    private void popularExercicios(@Nullable final ViewGroup container) {
        final Call<List<Exercicio>> call = new RetrofitInicializador().getExercicioService().getExercicios();
        call.enqueue(new Callback<List<Exercicio>>() {
            @Override
            //Conseguiu conectar com o servidor
            public void onResponse(Call<List<Exercicio>>  call, Response<List<Exercicio>> response) {
                List<Exercicio> exercicios = response.body();
                if (exercicios != null) {
                    ArrayList<DataModel> arrayList = new ArrayList<>();
                    for (Exercicio exercicio : exercicios) {
                        arrayList.add(new DataModel(exercicio.getDescricao(), exercicio.getImage(), "#09A9FF"));
                    }

                    RecyclerViewAdapter adapter = new RecyclerViewAdapter(getContext(), arrayList, null);
                    recyclerView.setAdapter(adapter);

                    /**
                     AutoFitGrid that auto fits the cells by the column width defined.
                     **/

                    AutoFitGrid layoutManager = new AutoFitGrid(container.getContext(), 500);
                    recyclerView.setLayoutManager(layoutManager);
                }
            }

            //Nao deu certo a execução
            @Override
            public void onFailure(Call<List<Exercicio>> call, Throwable t) {
                Log.e("onFailure", "Requisicao falhou");
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onItemClick(DataModel item) {
        Toast.makeText(getContext(), item.text + " is clicked", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        if(v == imageViewFiltrar){
            Intent filtrarItent = new Intent(getActivity(), FiltrarActivity.class);
            startActivity(filtrarItent);
        }

    }
}
