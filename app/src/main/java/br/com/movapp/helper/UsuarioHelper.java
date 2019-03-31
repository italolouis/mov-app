package br.com.movapp.helper;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.text.InputFilter;
import android.text.InputType;
import android.text.method.DigitsKeyListener;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import br.com.movapp.R;
import br.com.movapp.activity.CadastroActivity;
import br.com.movapp.model.Usuario;

public class UsuarioHelper {
    private EditText edtCadastroNome;
    private EditText edtCadastroDtNasc;
    private Spinner spinnerGenero;
    private EditText edtCadastroAltura;
    private EditText edtCadastroCelular;
    private EditText edtCadastroEmail;
    private EditText edtCadastroSenha;

    private Usuario usuario;
    private int mYear, mMonth, mDay;
    private DatePickerDialog.OnDateSetListener mDateSetListener;

    public UsuarioHelper(final CadastroActivity activity){
        edtCadastroNome = (EditText) activity.findViewById(R.id.edtCadastroNome);
        edtCadastroCelular = (EditText)activity.findViewById(R.id.edtCadastroCelular);
        edtCadastroDtNasc = (EditText) activity.findViewById(R.id.edtCadastroDtNasc);
        spinnerGenero = (Spinner) activity.findViewById(R.id.spinnerGenero);
        edtCadastroAltura = (EditText)activity.findViewById(R.id.edtCadastroAltura);
        edtCadastroEmail = (EditText) activity.findViewById(R.id.edtCadastroEmail);
        edtCadastroSenha = (EditText) activity.findViewById(R.id.edtCadastroSenha);

        //Formata telefone
        edtCadastroCelular .addTextChangedListener(new PhoneNumberFormattingTextWatcher());

        //Monta array do spinner para gênero
        spinnerGenero.setOnItemSelectedListener(null);
        montaListGenero(spinnerGenero,activity);

        //Listener
        edtCadastroDtNasc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                criaDialogDataNascimento(activity);
            }
        });

        usuario = new Usuario();
    }


    public Usuario getUsuario(){
        usuario.setNome(edtCadastroNome.getText().toString());
       //usuario.setDtNascimento(Date.valueOf(edtCadastroDtNasc.getText().toString()));
        usuario.setGenero(spinnerGenero.getSelectedItem().toString().substring(0,1));
        usuario.setAltura(Float.valueOf(edtCadastroAltura.getText().toString()));
        usuario.setTelefone(edtCadastroCelular.getText().toString());
        usuario.setEmail(edtCadastroEmail.getText().toString());
        usuario.setSenha(edtCadastroSenha.getText().toString());
        return usuario;
    }

    public void montaListGenero(Spinner spinnerGenero, Activity activity){
        //Elementos do spinner Genero
        List<String> generos = new ArrayList<String>();
        generos.add("Não especificado");
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
}
