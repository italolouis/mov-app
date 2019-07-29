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

import java.util.ArrayList;
import java.util.List;

import br.com.movapp.activity.AdicionarExercicioActivity;
import br.com.movapp.activity.FiltrarActivity;
import br.com.movapp.controller.TipoExercicioController;
import br.com.movapp.model.TipoExercicio;
import br.com.movapp.utils.AutoFitGrid;
import br.com.movapp.R;
import br.com.movapp.adapter.ExerciciosViewAdapter;
import br.com.movapp.model.ViewExercicio;

public class ExerciciosFragment extends Fragment implements ExerciciosViewAdapter.ItemListener, View.OnClickListener{
    private RecyclerView recyclerView;
    private ImageView imageViewBuscar;
    private ImageView imageViewFiltrar;
    public TipoExercicioController tipoExercicioController;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_exercicios, container, false);
        tipoExercicioController = new TipoExercicioController(getContext());

        imageViewBuscar = (ImageView) view.findViewById(R.id.imageViewBuscar);

        imageViewFiltrar = (ImageView) view.findViewById(R.id.imageViewFiltrar);
        imageViewFiltrar.setOnClickListener(this);

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);

        List<TipoExercicio> tipoExercicios = tipoExercicioController.buscarExercicios();
        if(!tipoExercicios.isEmpty()){
            setRecicleViewAdapter(tipoExercicios, container);
        }
        return view;
    }


    private void setRecicleViewAdapter(List<TipoExercicio> tipoExercicios, @Nullable ViewGroup container) {
        if (tipoExercicios != null) {
            ArrayList<ViewExercicio> arrayList = new ArrayList<>();
            for (TipoExercicio tipoExercicio : tipoExercicios) {
                arrayList.add(new ViewExercicio(tipoExercicio.getCod(), tipoExercicio.getNome(), tipoExercicio.getImage(), "#09A9FF"));
            }

            ExerciciosViewAdapter adapter = new ExerciciosViewAdapter(container.getContext(), arrayList, this);
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
