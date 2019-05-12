package br.com.movapp.helper;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import br.com.movapp.R;
import br.com.movapp.controller.ExercicioController;
import br.com.movapp.model.Exercicio;
import br.com.movapp.utils.ImageUtils;

public class ExercicioHelper {
    private TextView textViewNomeExercicio;
    private ImageView imgViewExercicio;
    private TextView textViewDesExercicio;
    private ExercicioController exercicioController;
    private Exercicio exercicio;

    public ExercicioHelper(Activity activity, Context context){
        textViewNomeExercicio = (TextView) activity.findViewById(R.id.textViewExercicioSel);
        imgViewExercicio = (ImageView) activity.findViewById(R.id.imgViewExercicioSel);
        textViewDesExercicio = (TextView) activity.findViewById(R.id.textViewDetalhesExSel);
        setDataExercicioSelecionado(activity, context);
        
    }

    private void setDataExercicioSelecionado(Activity activity, Context context) {
        Bundle extras = activity.getIntent().getExtras();
        exercicioController = new ExercicioController(context);
        exercicio = new Exercicio();
        if(extras != null){
            Long cod = extras.getLong("CODEXERCICIO");
            exercicio = exercicioController.buscaExercicio(cod);

            textViewNomeExercicio.setText(exercicio.getNome());
            imgViewExercicio.setImageDrawable(ImageUtils.getGifFromBytes(exercicio.getImage()));
            textViewDesExercicio.setText(exercicio.getDescricao());
        }
    }
}
