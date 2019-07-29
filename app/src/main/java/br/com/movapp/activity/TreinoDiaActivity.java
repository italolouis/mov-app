package br.com.movapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import br.com.movapp.R;
import br.com.movapp.adapter.TreinoViewAdapter;
import br.com.movapp.controller.TreinoController;
import br.com.movapp.model.TipoExercicio;
import br.com.movapp.model.Treino;

public class TreinoDiaActivity extends AppCompatActivity implements View.OnClickListener {
    RecyclerView recyclerView;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager layoutManager;
    private TreinoController treinoController;
    private List<TipoExercicio> exercicioList;
    private String diaTreino;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_treino_dia);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        treinoController  = new TreinoController(this);

        recyclerView = (RecyclerView) findViewById(R.id.recycleViewContainer);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        diaTreino = getDay();
        exercicioList = treinoController.buscarExerciciosdoDia(diaTreino);

        mAdapter = new TreinoViewAdapter(this,this, exercicioList, diaTreino);
        recyclerView.setAdapter(mAdapter);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(this);
    }

    private String getDay(){
        String dia = new String();
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            dia = extras.getString("DIA");
        }
        return dia;
    }

    @Override
    public void onClick(View v) {
        Intent executarExercicio = new Intent(this, ExecutarExercicio.class);
        executarExercicio.putExtra("DIATREINO", diaTreino);
        this.startActivity(executarExercicio);

    }
}
