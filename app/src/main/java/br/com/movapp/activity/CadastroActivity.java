package br.com.movapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import br.com.movapp.R;
import br.com.movapp.dao.PessoaDAO;
import br.com.movapp.helper.PessoaHelper;
import br.com.movapp.model.Pessoa;
import br.com.movapp.retrofit.RetrofitInicializador;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CadastroActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btn_cadastro;
    private PessoaHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        helper = new PessoaHelper(this);

        btn_cadastro = (Button) findViewById(R.id.btn_cadastro);
        btn_cadastro.setOnClickListener(CadastroActivity.this);
    }

    @Override
    public void onClick(View v) {
        if(v == btn_cadastro){
            Pessoa pessoa = helper.getPessoa();
            /*PessoaDAO pessoaDAO = new PessoaDAO(this);
            pessoaDAO.inserePessoa(pessoa);
            pessoaDAO.close();*/

            Call call = new RetrofitInicializador().getUsuarioSerice().insere(pessoa);
            //Metodo assimilar ao execute, mas vai fazer a execução assincrona
            call.enqueue(new Callback() {
                @Override
                //Conseguiu conectar com o servidor
                public void onResponse(Call call, Response response) {
                    if(response.code() == 201){
                        Log.i("onResponde", "Requisicao com sucesso");
                        Intent loginIntent = new Intent(CadastroActivity.this, LoginActivity.class);
                        CadastroActivity.this.startActivity(loginIntent);
                    }
                }

                //Nao deu certo a execução
                @Override
                public void onFailure(Call call, Throwable t) {
                    Log.e("onFailure", "Requisicao falhou");
                    Toast.makeText(CadastroActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();

                }
            });

        }
    }



}
