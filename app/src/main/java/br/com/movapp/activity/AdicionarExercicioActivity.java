package br.com.movapp.activity;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import br.com.movapp.R;
import br.com.movapp.controller.ExercicioController;
import br.com.movapp.controller.SerieController;
import br.com.movapp.controller.TipoExercicioController;
import br.com.movapp.controller.TreinoController;
import br.com.movapp.controller.UsuarioController;
import br.com.movapp.model.Exercicio;
import br.com.movapp.model.Serie;
import br.com.movapp.model.TipoExercicio;
import br.com.movapp.model.Treino;
import br.com.movapp.model.Usuario;
import br.com.movapp.retrofit.ImportFromService;
import br.com.movapp.utils.ImageUtils;

public class AdicionarExercicioActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener, TextWatcher {
    private Button btnAddExercicio;
    private TextView textViewNomeExercicio;
    private ImageView imgViewExercicio;
    private TextView textViewDesExercicio;
    private Spinner spnExercicioDia;
    private Spinner spnExercicioTipoSerie;
    private Spinner spnNumeroSeries;
    private int numeroSerie;
    private TextView textRepeticao;
    private EditText edtRepeticao;
    private TextView textCarga;
    private EditText edtCarga;
    private TipoExercicioController tipoExercicioController;
    private TipoExercicio tipoExercicio;
    private EditText edtDescansoSerie;
    private List<Serie> arraySeries;
    private Exercicio exercicio;
    private SerieController serieController;
    private ExercicioController exercicioController;
    private TreinoController treinoController;
    private LinearLayout lm = null;
    private Long codExercicio = null;
    private UsuarioController usuarioController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar_exercicio);

        Bundle extras = getIntent().getExtras();
        codExercicio = extras.getLong("CODEXERCICIO");

        serieController = new SerieController(this);
        tipoExercicioController = new TipoExercicioController(this);

        exercicioController = new ExercicioController(this);
        usuarioController = new UsuarioController(this);
        treinoController = new TreinoController(this);

        textViewNomeExercicio = (TextView) findViewById(R.id.textViewExercicioSel);
        imgViewExercicio = (ImageView) findViewById(R.id.imgViewExercicioSel);
        textViewDesExercicio = (TextView) findViewById(R.id.textViewDetalhesExSel);
        setDataExercicioSelecionado();

        btnAddExercicio = (Button) findViewById(R.id.addExercicio);
        btnAddExercicio.setOnClickListener(this);

        spnExercicioDia = (Spinner) findViewById(R.id.spnExercicioDia);
        ArrayAdapter<CharSequence> adapterDia = getCharSequenceArrayAdapter(R.array.dias_array);
        spnExercicioDia.setAdapter(adapterDia);

        edtDescansoSerie = (EditText)findViewById(R.id.edtDescansoSerie);

        spnExercicioTipoSerie = (Spinner) findViewById(R.id.spnExercicioTipoSerie);
        ArrayAdapter<CharSequence> adapterTipoSerie = getCharSequenceArrayAdapter(R.array.tipoSerie_array);
        spnExercicioTipoSerie.setAdapter(adapterTipoSerie);
        spnExercicioTipoSerie.setOnItemSelectedListener(this);

        spnNumeroSeries = (Spinner) findViewById(R.id.spnNumeroSeries);
        ArrayAdapter<CharSequence> adapterNumSerie = getCharSequenceArrayAdapter(R.array.numSerie_array);
        spnNumeroSeries.setAdapter(adapterNumSerie);
        spnNumeroSeries.setOnItemSelectedListener(this);
    }

    private void setDataExercicioSelecionado() {
        Bundle extras = this.getIntent().getExtras();
        tipoExercicioController = new TipoExercicioController(this);
        tipoExercicio = new TipoExercicio();
        if(extras != null){
            Long cod = extras.getLong("CODEXERCICIO");
            tipoExercicio = tipoExercicioController.buscaExercicio(cod);

            textViewNomeExercicio.setText(tipoExercicio.getNome());
            if(tipoExercicio.getImage() != null){
                imgViewExercicio.setImageDrawable(ImageUtils.getGifFromBytes(tipoExercicio.getImage()));
            }
            textViewDesExercicio.setText(tipoExercicio.getInstrucao());
        }
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
            exercicio = new Exercicio();
            Serie serie = new Serie();
            Usuario usuario = new Usuario();

            exercicio.setSeries(serieController.buscarCodSeries());
            exercicio.setExerciciocod(codExercicio);
            String descansSerie = edtDescansoSerie.getText().toString();
            if(!descansSerie.isEmpty()){
                exercicio.setDescanso(Long.valueOf(edtDescansoSerie.getText().toString()));
            }

            InsereExercicio insereExercicio = new InsereExercicio();
            insereExercicio.execute(exercicio);
            //ImportFromService.insereExerciciosToService(this, exercicio);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(parent.getId() == R.id.spnNumeroSeries){
            numeroSerie = (int) id;
            spnExercicioTipoSerie.setSelection(0);
        } else if(parent.getId() == R.id.spnExercicioTipoSerie){
            if (position != 0){
                onCreateDialogSerie(id, this);
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public Boolean onCreateDialogSerie(Long idTipoSerie, final Context context){
        arraySeries = new ArrayList<>();
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setView(createViewRepeticao(idTipoSerie))
                .setTitle(spnExercicioTipoSerie.getSelectedItem().toString())
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                       arraySeries =  getValuesSerie();
                        if (!arraySeries.isEmpty()) {
                            for (Serie serie : arraySeries) {
                                ImportFromService.insereSerieToService(context, serie);
                            }
                        }
                    }
                })
                .setNegativeButton("Cancelar", null)
                .create();
        dialog.show();
        return true;
    }

    public LinearLayout createViewRepeticao(Long idTipoSerie){
        lm = new LinearLayout(this);
        lm.setOrientation(LinearLayout.VERTICAL);
        lm.setPadding(30,30,30, 30);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        for(int j=0; j<=numeroSerie; j++) {
            LinearLayout  ll = new LinearLayout(this);
            ll.setOrientation(LinearLayout.HORIZONTAL);

            textRepeticao = new TextView(this);
            textRepeticao.setText(returnLabeRepeticao(j, idTipoSerie));
            ll.addView(textRepeticao);

            edtRepeticao = new EditText(this);
            edtRepeticao.setId(R.id.wide);
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
                edtCarga.setId(R.id.wrap);
                edtCarga.setLayoutParams(params);
                edtCarga.setInputType(InputType.TYPE_CLASS_NUMBER);
                edtCarga.addTextChangedListener(this);
                ll.addView(edtCarga);
            }

            lm.addView(ll);
        }
        return lm;
    }

    private List<Serie> getValuesSerie() {
        arraySeries = new ArrayList<>();
        Serie serie = new Serie();
        Long repeticao = null;
        Long carga = null;

        int tipoSerie = spnExercicioTipoSerie.getSelectedItemPosition();
        for (int i = 0; i < lm.getChildCount(); i++) {
            View element = lm.getChildAt(i);

            if (element instanceof LinearLayout) {
                LinearLayout ll = (LinearLayout) element;

                for (int j = 0; j < ll.getChildCount(); j++) {
                    View viewElent = ll.getChildAt(j);

                    if(viewElent instanceof EditText){
                        EditText edt = (EditText)viewElent;
                        Long valor = Long.valueOf(edt.getText().toString());

                        if(tipoSerie == 3){
                            if(edt.getId() == R.id.wide){
                                repeticao = valor;
                            }else if(edt.getId() == R.id.wrap){
                                carga = valor;
                            }

                            if((repeticao != null) && carga != null){
                                serie.setTipo(spnExercicioTipoSerie.getSelectedItem().toString());
                                serie.setRepeticao(repeticao);
                                serie.setCarga(carga);
                                arraySeries.add(serie);
                                repeticao = null;
                                carga = null;
                            }
                        }else{
                            serie = returnSerie(tipoSerie, valor, edt);
                            arraySeries.add(serie);
                        }
                    }
                }
            }
        }
        return  arraySeries;
    }

    private Serie returnSerie(int tipoSerie, Long valor, EditText edt){
        Serie serie = new Serie();
        serie.setTipo(spnExercicioTipoSerie.getSelectedItem().toString());
        if(tipoSerie == 1){
            serie.setRepeticao(valor);
        }else if(tipoSerie == 2){
            serie.setCarga(valor);
        } else if(tipoSerie == 4 || tipoSerie == 5){
            serie.setDuracao(valor);
        }
        return serie;
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

    }

    private class InsereExercicio extends AsyncTask<Exercicio, Void, Exercicio> {

        @Override
        protected Exercicio doInBackground(Exercicio... exercicios) {
            Exercicio exercicio = new Exercicio();
            exercicio = ImportFromService.insereExerciciosToService(exercicioController, exercicios[0]);
            return exercicio;
        }

        @Override
        protected void onPostExecute(Exercicio exercicio) {
            if (exercicio != null) {
                Treino treino = new Treino();
                treino.setDia(spnExercicioDia.getSelectedItem().toString());
                treino.setExercicios(exercicioController.buscarExercicioInserido(exercicio.getCod()));
                treino.setUsuario(usuarioController.buscaCodUsuario());
                ImportFromService.insereTreinoToService(treinoController, treino);

                Intent mainIntent = new Intent(AdicionarExercicioActivity.this, MainActivity.class);
                mainIntent.putExtra("FRAGMENT", 2);
                AdicionarExercicioActivity.this.startActivity(mainIntent);
            }
        }
    }

}
