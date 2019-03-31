package br.com.movapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import br.com.movapp.R;
import br.com.movapp.dao.UsuarioDAO;
import br.com.movapp.helper.UsuarioHelper;
import br.com.movapp.model.Usuario;
import br.com.movapp.retrofit.RetrofitInicializador;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CadastroActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btn_cadastro;
    private UsuarioHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        helper = new UsuarioHelper(this);

        btn_cadastro = (Button) findViewById(R.id.btn_cadastro);
        btn_cadastro.setOnClickListener(CadastroActivity.this);
    }

    @Override
    public void onClick(View v) {
        if(v == btn_cadastro){
            Usuario usuario = helper.getUsuario();
            UsuarioDAO usuarioDAO = new UsuarioDAO(this);
            usuarioDAO.insereUsuario(usuario);
            usuarioDAO.close();

            Call call = new RetrofitInicializador().getUsuarioSerice().insere(usuario);
            //Metodo assimilar ao execute, mas vai fazer a execução assincrona
            call.enqueue(new Callback() {
                @Override
                //Conseguiu conectar com o servidor
                public void onResponse(Call call, Response response) {
                    Log.i("onResponde", "Requisicao com sucesso");

                    Intent loginIntent = new Intent(CadastroActivity.this, LoginActivity.class);
                    CadastroActivity.this.startActivity(loginIntent);

                }

                //Nao deu certo a execução
                @Override
                public void onFailure(Call call, Throwable t) {
                    Log.e("onFailure", "Requisicao falhou");

                }
            });

           // Toast.makeText(CadastroActivity.this, "Usuario " + usuario.getNome() + " salvo!", Toast.LENGTH_SHORT).show();

        }
    }



}
