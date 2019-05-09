package br.com.movapp.helper;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import br.com.movapp.R;
import br.com.movapp.model.Endereco;
import br.com.movapp.model.Usuario;
import br.com.movapp.retrofit.RetrofitCep;
import br.com.movapp.utils.ImageUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UsuarioHelper {
    private EditText edtCadastroNome;
    private EditText edtCadastroDtNasc;
    private Spinner spinnerGenero;
    private EditText edtCadastroAltura;
    private EditText edtCadastroCelular;
    private EditText edtCadastroEmail;
    private EditText edtCadastroSenha;
    private EditText edtCadastroCep;
    private EditText edtCadastroCidade;
    private EditText edtCadastroRua;
    private EditText edtCadastroNum;
    private EditText edtCadastroComple;
    private EditText edtCadastroBairro;
    private EditText edtCadastroUf;
    private Bitmap imagem;

    private Usuario usuario;
    private Endereco endereco;
    private int mYear, mMonth, mDay;
    private DatePickerDialog.OnDateSetListener mDateSetListener;

    public UsuarioHelper(final Activity activity){
        edtCadastroNome = (EditText) activity.findViewById(R.id.edtCadastroNome);
        edtCadastroCelular = (EditText)activity.findViewById(R.id.edtCadastroCelular);
        edtCadastroDtNasc = (EditText) activity.findViewById(R.id.edtCadastroDtNasc);
        spinnerGenero = (Spinner) activity.findViewById(R.id.spinnerGenero);
        edtCadastroAltura = (EditText)activity.findViewById(R.id.edtCadastroAltura);
        edtCadastroEmail = (EditText) activity.findViewById(R.id.edtCadastroEmail);
        edtCadastroSenha = (EditText) activity.findViewById(R.id.edtCadastroSenha);
        edtCadastroCep = (EditText) activity.findViewById(R.id.edtCadastroCep);
        edtCadastroRua = (EditText) activity.findViewById(R.id.edtCadastroRua);
        edtCadastroNum = (EditText) activity.findViewById(R.id.edtCadastroNum);
        edtCadastroComple = (EditText) activity.findViewById(R.id.edtCadastroComple);
        edtCadastroBairro = (EditText) activity.findViewById(R.id.edtCadastroBairro);
        edtCadastroCidade = (EditText) activity.findViewById(R.id.edtCadastroCidade);
        edtCadastroUf = (EditText) activity.findViewById(R.id.edtCadastroUf);

        //Formata telefone
        if(edtCadastroCelular != null){
            edtCadastroCelular .addTextChangedListener(new PhoneNumberFormattingTextWatcher());
        }

        //Monta array do spinner para gênero
        if(spinnerGenero != null){
            spinnerGenero.setOnItemSelectedListener(null);
            montaListGenero(spinnerGenero,activity);
        }

        //Listener
        if(edtCadastroDtNasc != null){
            edtCadastroDtNasc.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    criaDialogDataNascimento(activity);
                }
            });
        }

        if(edtCadastroCep != null){
            edtCadastroCep.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if(s.length() == 8){
                        String cep = edtCadastroCep.getText().toString();
                        buscaCep(cep);
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {
                }
            });
        }

        usuario = new Usuario();
    }

    public void setImagem(Bitmap img){
        imagem = img;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public Usuario getUsuario() {
        usuario.setNome(edtCadastroNome.getText().toString());
        //usuario.setDtnascimento(LocalDate.parse(edtCadastroDtNasc.getText()));
        usuario.setGenero(spinnerGenero.getSelectedItem().toString().substring(0,1));
        if(!edtCadastroAltura.getText().toString().isEmpty()){
            usuario.setAltura(BigDecimal.valueOf(Double.parseDouble(edtCadastroAltura.getText().toString())));
        }
        usuario.setTelefone(edtCadastroCelular.getText().toString());
        usuario.setEmail(edtCadastroEmail.getText().toString());
        usuario.setSenha(edtCadastroSenha.getText().toString());
        usuario.setEndereco(getDadosEndereco());
        if(imagem != null){
            usuario.setFoto(ImageUtils.getBytesFromBitmap(imagem));
        }
        return usuario;
    }

    private Endereco getDadosEndereco(){
        endereco = new Endereco();
        endereco.setCep(edtCadastroCep.getText().toString());
        endereco.setLogradouro(edtCadastroRua.getText().toString());
        endereco.setNumero(edtCadastroNum.getText().toString());
        endereco.setComplemento(edtCadastroComple.getText().toString());
        endereco.setBairro(edtCadastroBairro.getText().toString());
        endereco.setLocalidade(edtCadastroCidade.getText().toString());
        endereco.setUf(edtCadastroUf.getText().toString());
        return endereco;

    }

    private void setDadosEndereco(Endereco endereco){
        edtCadastroCep.setText(endereco.getCep());
        edtCadastroCidade.setText(endereco.getLocalidade());
        edtCadastroBairro.setText(endereco.getBairro());
        edtCadastroRua.setText(endereco.getLogradouro());
        edtCadastroUf.setText(endereco.getUf());
    }

    public void montaListGenero(Spinner spinnerGenero, Activity activity){
        //Elementos do spinner Genero
        List<String> generos = new ArrayList<String>();
        generos.add("Gênero");
        generos.add("Feminino");
        generos.add("Masculino");

        //Criado adapter para o spinner Genero
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(activity, android.R.layout.simple_spinner_item, generos);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerGenero.setAdapter(dataAdapter);
    }

    public void criaDialogDataNascimento(Activity activity){
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dialog = new DatePickerDialog(
                activity,
                android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                mDateSetListener,
                mYear,mMonth,mDay);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;

                String date = day + "/" + month + "/" + year;
                edtCadastroDtNasc.setText(date);
            }
        };
    }

    private void buscaCep(String cep) {
        Call<Endereco> call = new RetrofitCep().getEndereco().buscarEndereco(cep);
        call.enqueue(new Callback<Endereco>() {
            @Override
            public void onResponse(Call<Endereco> call, Response<Endereco> response) {
                endereco = new Endereco();
                endereco = response.body();
                setDadosEndereco(endereco);
            }

            @Override
            public void onFailure(Call<Endereco> call, Throwable t) {
                String messagem = t.getMessage();
            }
        });
    }
}
