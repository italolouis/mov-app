package br.com.movapp.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import br.com.movapp.R;
import br.com.movapp.controller.TipoExercicioController;
import br.com.movapp.controller.TreinoController;
import br.com.movapp.model.Exercicio;
import br.com.movapp.model.TipoExercicio;
import br.com.movapp.model.Treino;
import br.com.movapp.utils.ImageUtils;

public class ExecutarExercicio extends AppCompatActivity implements View.OnClickListener {
    private TextView execExercicioNome;
    private ImageView execExercicioFoto;
    private TipoExercicioController tipoExercicioController;
    private TreinoController treinoController;
    private List<Exercicio> exercicioList;
    ProgressBar Progressbar;
    TextView ShowText;
    int progressBarValue = 0;
    private int iterator = 0;
    Handler handler = new Handler();
    private Exercicio exercicio;
    ImageView StartProgressBar,StopProgressBar;
    boolean isStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_executar_exercicio);
        isStart = true;
        exercicioList = new ArrayList<>();
        exercicio = new Exercicio();
        treinoController = new TreinoController(this);

        execExercicioNome = (TextView) findViewById(R.id.execExercicioNome);
        execExercicioFoto = (ImageView) findViewById(R.id.execExercicioFoto);
        setDataExercicioSelecionado();

        Progressbar = (ProgressBar)findViewById(R.id.progressBar1);
        ShowText = (TextView)findViewById(R.id.textView1);
        StopProgressBar = (ImageView)findViewById(R.id.button2);
        StopProgressBar.setOnClickListener(this);

        handler = new Handler(){
            public void handleMessage(android.os.Message msg){
                if(isStart) {
                    progressBarValue++;
                }
                Progressbar.setProgress(progressBarValue);
                ShowText.setText(String.valueOf(progressBarValue/60)+":"+String.valueOf(progressBarValue%60));

                handler.sendEmptyMessageDelayed(0, 100);
            }
        };

        handler.sendEmptyMessage(0);
    }

    private void setDataExercicioSelecionado() {
        Bundle extras = this.getIntent().getExtras();
        if(extras != null){

            Long cod = extras.getLong("CODEXERCICIO");
            String dia = extras.getString("DIATREINO");
            int execucao =  extras.getInt("EXECUCAO");

            if(Integer.valueOf(execucao) != null){
                iterator = execucao;
            }

            if(!dia.isEmpty()){
                exercicioList = treinoController.getListExerciciosTreino(dia, cod);
                if(exercicioList.size() > 0){
                    exercicio = exercicioList.get(iterator);
                    execExercicioNome.setText(exercicio.getTipoExercicios().getNome());
                    execExercicioFoto.setImageDrawable(ImageUtils.getGifFromBytes(exercicio.getTipoExercicios().getImage()));
                }
            }
        }
    }

    @Override
    public void onClick(View v) {
         if(v == StopProgressBar){
             isStart = false;
             iterator++;
             Intent descansoItent = new Intent(this, DescansoActivity.class);
             descansoItent.putExtra("EXECUCAO", iterator);
             descansoItent.putExtra("TEMPODESCANSO", exercicio.getDescanso().toString());
             this.startActivity(descansoItent);
         }
    }
}
