package br.com.movapp.activity.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.com.movapp.activity.AdicionarExercicioActivity;
import br.com.movapp.activity.CadastroActivity;
import br.com.movapp.activity.FiltrarActivity;
import br.com.movapp.activity.LoginActivity;
import br.com.movapp.controller.ExercicioController;
import br.com.movapp.model.Exercicio;
import br.com.movapp.retrofit.ImportFromService;
import br.com.movapp.utils.AutoFitGrid;
import br.com.movapp.R;
import br.com.movapp.adapter.RecyclerViewAdapter;
import br.com.movapp.model.ViewExercicio;

public class ExerciciosFragment extends Fragment implements RecyclerViewAdapter.ItemListener, View.OnClickListener{
    private RecyclerView recyclerView;
    private ImageView imageViewBuscar;
    private ImageView imageViewFiltrar;
    public ExercicioController exercicioController;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_exercicios, container, false);
        exercicioController = new ExercicioController(getContext());

        imageViewBuscar = (ImageView) view.findViewById(R.id.imageViewBuscar);

        imageViewFiltrar = (ImageView) view.findViewById(R.id.imageViewFiltrar);
        imageViewFiltrar.setOnClickListener(this);

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);

        List<Exercicio> exercicios = exercicioController.buscarExercicios();
        if(!exercicios.isEmpty()){
            setRecicleViewAdapter(exercicios, container);
        }
        return view;
    }


    private void setRecicleViewAdapter(List<Exercicio> exercicios, @Nullable ViewGroup container) {
        if (exercicios != null) {
            ArrayList<ViewExercicio> arrayList = new ArrayList<>();
            for (Exercicio exercicio : exercicios) {
                arrayList.add(new ViewExercicio(exercicio.getCod(), exercicio.getNome(), exercicio.getImage(), "#09A9FF"));
            }

            RecyclerViewAdapter adapter = new RecyclerViewAdapter(container.getContext(), arrayList, this);
            recyclerView.setAdapter(adapter);

            /**
             AutoFitGrid that auto fits the cells by the column width defined.
             **/
            AutoFitGrid layoutManager = new AutoFitGrid(container.getContext(), 500);
            recyclerView.setLayoutManager(layoutManager);
        }
    }

    @Override
    public void onItemClick(ViewExercicio item) {
        Intent adicionarExercicio = new Intent(getActivity(), AdicionarExercicioActivity.class);
        adicionarExercicio.putExtra("CODEXERCICIO", item.id);
        getActivity().startActivity(adicionarExercicio);

    }

    @Override
    public void onClick(View v) {
        if(v == imageViewFiltrar){
            Intent filtrarItent = new Intent(getActivity(), FiltrarActivity.class);
            startActivity(filtrarItent);
        }

    }
}
