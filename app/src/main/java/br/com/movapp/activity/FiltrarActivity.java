package br.com.movapp.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import br.com.movapp.R;
import br.com.movapp.model.Equipamento;
import br.com.movapp.model.GrupoMuscular;
import br.com.movapp.retrofit.RetrofitInicializador;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FiltrarActivity extends AppCompatActivity {
    private ListView listViewGrupo ;
    private ListView  listViewEquipamentos;

    SparseBooleanArray sparseBooleanArray ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filtrar);

        listViewGrupo = (ListView)findViewById(R.id.listViewGrupo);
        popularGrupos();

        listViewEquipamentos= (ListView)findViewById(R.id.listViewEquipamentos);
        popularEquipamentos();

        listViewGrupo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(FiltrarActivity.this, "ListView Selected Values = " +  Long.toString(id) ,  Toast.LENGTH_LONG).show();
            }
        });

        listViewEquipamentos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(FiltrarActivity.this, "ListView Selected Values = " +  Long.toString(id) ,  Toast.LENGTH_LONG).show();
            }
        });
    }

    private void popularGrupos() {
        Call<List<GrupoMuscular>> call = new RetrofitInicializador().getGrupoSerice().getGrupos();
        call.enqueue(new Callback<List<GrupoMuscular>>() {
            @Override
            //Conseguiu conectar com o servidor
            public void onResponse(Call<List<GrupoMuscular>>  call, Response<List<GrupoMuscular>> response) {
                List<GrupoMuscular> myList = response.body();
                ArrayAdapter<GrupoMuscular> adapter = new ArrayAdapter<GrupoMuscular>
                        (FiltrarActivity.this,
                                android.R.layout.simple_list_item_multiple_choice,
                                android.R.id.text1, myList);

                listViewGrupo.setAdapter(adapter);
            }

            //Nao deu certo a execução
            @Override
            public void onFailure(Call<List<GrupoMuscular>> call, Throwable t) {
                Log.e("onFailure", "Requisicao falhou");
                Toast.makeText(FiltrarActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void popularEquipamentos() {
        Call<List<Equipamento>> call = new RetrofitInicializador().getEquipamentoService().getEquipamentos();
        call.enqueue(new Callback<List<Equipamento>>() {
            @Override
            //Conseguiu conectar com o servidor
            public void onResponse(Call<List<Equipamento>>  call, Response<List<Equipamento>> response) {
                List<Equipamento> myList = response.body();
                ArrayAdapter<Equipamento> adapter = new ArrayAdapter<Equipamento>
                        (FiltrarActivity.this,
                                android.R.layout.simple_list_item_multiple_choice,
                                android.R.id.text1, myList);

                listViewEquipamentos.setAdapter(adapter);
            }

            //Nao deu certo a execução
            @Override
            public void onFailure(Call<List<Equipamento>> call, Throwable t) {
                Log.e("onFailure", "Requisicao falhou");
                Toast.makeText(FiltrarActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
