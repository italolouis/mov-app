package br.com.movapp.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import br.com.movapp.R;
import br.com.movapp.helper.ExercicioHelper;

public class AdicionarExercicioActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener, TextWatcher {
    private Button btnAddExercicio;
    private ExercicioHelper exercicioHelper;
    private Spinner spnExercicioDia;
    private Spinner spnExercicioTipoSerie;
    private Spinner spnNumeroSeries;
    private int numeroSerie;
    private TextView textRepeticao;
    private EditText edtRepeticao;
    private TextView textCarga;
    private EditText edtCarga;
    private List<String> arraySeries;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar_exercicio);

        exercicioHelper = new ExercicioHelper(this, this);

        btnAddExercicio = (Button) findViewById(R.id.addExercicio);
        btnAddExercicio.setOnClickListener(this);

        spnExercicioDia = (Spinner) findViewById(R.id.spnExercicioDia);
        ArrayAdapter<CharSequence> adapterDia = getCharSequenceArrayAdapter(R.array.dias_array);
        spnExercicioDia.setAdapter(adapterDia);

        spnExercicioTipoSerie = (Spinner) findViewById(R.id.spnExercicioTipoSerie);
        ArrayAdapter<CharSequence> adapterTipoSerie = getCharSequenceArrayAdapter(R.array.tipoSerie_array);
        spnExercicioTipoSerie.setAdapter(adapterTipoSerie);
        spnExercicioTipoSerie.setOnItemSelectedListener(this);

        spnNumeroSeries = (Spinner) findViewById(R.id.spnNumeroSeries);
        ArrayAdapter<CharSequence> adapterNumSerie = getCharSequenceArrayAdapter(R.array.numSerie_array);
        spnNumeroSeries.setAdapter(adapterNumSerie);
        spnNumeroSeries.setOnItemSelectedListener(this);
    }

    private ArrayAdapter<CharSequence> getCharSequenceArrayAdapter(int resId) {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                resId, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        return adapter;
    }

    @Override
    public void onClick(View v) {
        if(v == btnAddExercicio){

        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(parent.getId() == R.id.spnNumeroSeries){
            numeroSerie = (int) id;
            spnExercicioTipoSerie.setSelection(0);
        } else if(parent.getId() == R.id.spnExercicioTipoSerie){
            if (position != 0){
                onCreateDialogSerie(id);
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public Boolean onCreateDialogSerie(Long idTipoSerie){
        arraySeries = new ArrayList<>();
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setView(createViewRepeticao(idTipoSerie))
                .setTitle(spnExercicioTipoSerie.getSelectedItem().toString())
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setNegativeButton("Cancelar", null)
                .create();
        dialog.show();
        return true;
    }

    public LinearLayout createViewRepeticao(Long idTipoSerie){
        final LinearLayout lm  = new LinearLayout(this);
        lm.setOrientation(LinearLayout.VERTICAL);
        lm.setPadding(30,30,30, 30);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        for(int j=0; j<=numeroSerie; j++) {
            LinearLayout ll = new LinearLayout(this);
            ll.setOrientation(LinearLayout.HORIZONTAL);

            textRepeticao = new TextView(this);
            textRepeticao.setText(returnLabeRepeticao(j, idTipoSerie));
            ll.addView(textRepeticao);

            edtRepeticao = new EditText(this);
            if(idTipoSerie != 3){
                edtRepeticao.setLayoutParams(params);
            }else {
                edtRepeticao.setWidth(300);
            }

            edtRepeticao.setInputType(InputType.TYPE_CLASS_NUMBER);
            edtRepeticao.addTextChangedListener(this);
            ll.addView(edtRepeticao);

            if(idTipoSerie == 3){
                textCarga = new TextView(this);
                textCarga.setText("Carga: ");
                ll.addView(textCarga);

                edtCarga = new EditText(this);
                edtCarga.setLayoutParams(params);
                edtCarga.setInputType(InputType.TYPE_CLASS_NUMBER);
                edtCarga.addTextChangedListener(this);
                ll.addView(edtCarga);
            }

            lm.addView(ll);
        }

        return lm;
    }

    private String returnLabeRepeticao(int j, Long idTipoSerie) {
        String label = "";
        if(idTipoSerie == 1 ||  idTipoSerie == 3){
            label = (j+1)+ "ª Repeticão:";
        }else  if(idTipoSerie == 2){
            label = (j+1)+ "ª Carga (Kg):";
        }
        else  if(idTipoSerie == 4){
            label = (j+1)+ "ª Tempo (min):";
        }
        else  if(idTipoSerie == 5){
            label = (j+1)+ "ª Tempo (seg):";
        }
        return label;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }

    @Override
    public void afterTextChanged(Editable s) {
        String texto = edtRepeticao.getText().toString();
        arraySeries.add(texto);
    }
}
