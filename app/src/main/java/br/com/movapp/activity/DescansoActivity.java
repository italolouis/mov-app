package br.com.movapp.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import br.com.movapp.R;

import static android.view.animation.Animation.RELATIVE_TO_SELF;

public class DescansoActivity extends AppCompatActivity implements View.OnClickListener {
    private int myProgress = 0;
    private ProgressBar progressBarView;
    ImageView btnFinish;
    TextView tv_time;
    int progress;
    CountDownTimer countDownTimer;
    int endTime = 300;
    String timer;
    private int execucao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_descanso);

        progressBarView = (ProgressBar) findViewById(R.id.view_progress_bar);
        btnFinish = (ImageView)findViewById(R.id.btnFinishDescanso);
        tv_time= (TextView)findViewById(R.id.tv_timer);

        RotateAnimation makeVertical = new RotateAnimation(0, -90, RELATIVE_TO_SELF, 0.5f, RELATIVE_TO_SELF, 0.5f);
        makeVertical.setFillAfter(true);
        progressBarView.startAnimation(makeVertical);
        progressBarView.setSecondaryProgress(endTime);
        progressBarView.setProgress(0);

        timer =  getTempoDescanso();
        fn_countdown(this);

        btnFinish.setOnClickListener(this);

    }

    private String getTempoDescanso(){
        String tempo = new String();
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            tempo = extras.getString("TEMPODESCANSO");
            execucao = extras.getInt("EXECUCAO");
        }
        return tempo;
    }

    public void fn_countdown(final Activity activity) {
        if (timer.length()>0) {
            myProgress = 0;

            try {
                countDownTimer.cancel();
            } catch (Exception e) {

            }

            String timeInterval = timer;
            progress = 1;
            endTime = Integer.parseInt(timeInterval); // up to finish time

            countDownTimer = new CountDownTimer(endTime * 1000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    setProgress(progress, endTime);
                    progress = progress + 1;
                    int seconds = (int) (millisUntilFinished / 1000) % 60;
                    int minutes = (int) ((millisUntilFinished / (1000 * 60)) % 60);
                    int hours = (int) ((millisUntilFinished / (1000 * 60 * 60)) % 24);
                    String newtime = hours + ":" + minutes + ":" + seconds;

                    if (newtime.equals("0:0:0")) {
                        tv_time.setText("00:00:00");
                    } else if ((String.valueOf(hours).length() == 1) && (String.valueOf(minutes).length() == 1) && (String.valueOf(seconds).length() == 1)) {
                        tv_time.setText("0" + hours + ":0" + minutes + ":0" + seconds);
                    } else if ((String.valueOf(hours).length() == 1) && (String.valueOf(minutes).length() == 1)) {
                        tv_time.setText("0" + hours + ":0" + minutes + ":" + seconds);
                    } else if ((String.valueOf(hours).length() == 1) && (String.valueOf(seconds).length() == 1)) {
                        tv_time.setText("0" + hours + ":" + minutes + ":0" + seconds);
                    } else if ((String.valueOf(minutes).length() == 1) && (String.valueOf(seconds).length() == 1)) {
                        tv_time.setText(hours + ":0" + minutes + ":0" + seconds);
                    } else if (String.valueOf(hours).length() == 1) {
                        tv_time.setText("0" + hours + ":" + minutes + ":" + seconds);
                    } else if (String.valueOf(minutes).length() == 1) {
                        tv_time.setText(hours + ":0" + minutes + ":" + seconds);
                    } else if (String.valueOf(seconds).length() == 1) {
                        tv_time.setText(hours + ":" + minutes + ":0" + seconds);
                    } else {
                        tv_time.setText(hours + ":" + minutes + ":" + seconds);
                    }

                }

                @Override
                public void onFinish() {
                    setProgress(progress, endTime);
                    Intent execucaoItent = new Intent(activity, ExecutarExercicio.class);
                    execucaoItent.putExtra("EXECUCAO", execucao+1);
                    activity.startActivity(execucaoItent);
                }
            };
            countDownTimer.start();
        }
    }

    public void setProgress(int startTime, int endTime) {
        progressBarView.setMax(endTime);
        progressBarView.setSecondaryProgress(endTime);
        progressBarView.setProgress(startTime);
    }


    @Override
    public void onClick(View v) {
        if(v == btnFinish){
            setProgress(progress, endTime);
            Intent execucaoItent = new Intent(this, ExecutarExercicio.class);
            execucaoItent.putExtra("EXECUCAO", execucao+1);
            this.startActivity(execucaoItent);
        }
    }
}
