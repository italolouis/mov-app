package br.com.movapp.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;

import java.util.List;

import br.com.movapp.model.Pessoa;

public class PessoaDAO extends SQLiteOpenHelper {
    public PessoaDAO(Context context) {
        super(context, "Pessoa", null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE pessoa(" +
                "codusu INTEGER PRIMARY KEY, " +
                "nome TEXT NOT NULL, "+
                "dtNascimento DATE, " +
                "genero TEXT, " +
                "altura NUMERIC, " +
                "celular TEXT, " +
                "email TEXT NOT NULL, " +
                "senha TEXT, "+
                "bloqueio TEXT);";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public void inserePessoa(Pessoa pessoa) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues dados = getDadosPessoa(pessoa);
        db.insert("pessoa", null, dados);
    }

    @NonNull
    private ContentValues getDadosPessoa(Pessoa pessoa) {
        ContentValues dados = new ContentValues();
        dados.put("nome", pessoa.getNome());
       // dados.put("dtNascimento", pessoa.getDtNascimento().toString());
        dados.put("genero", pessoa.getGenero());
        dados.put("altura", pessoa.getAltura());
        dados.put("celular", pessoa.getTelefone());
        dados.put("email", pessoa.getEmail());
        dados.put("senha", pessoa.getSenha());
       // dados.put("bloqueio", pessoa.getBloqueio());
        return dados;
    }

    public boolean isUser(String email, String senha) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM pessoa WHERE email = ? AND senha = ?", new String[]{email,senha});
        int resultados = c.getCount();
        c.close();
        return resultados > 0;
    }


    public void sincroniza(List<Pessoa> pessoas) {
        for(Pessoa pessoa: pessoas){
            if(existe(pessoa)){
                altera(pessoa);
            }else{
                inserePessoa(pessoa);
            }

        }
    }

    private boolean existe(Pessoa pessoa) {
        SQLiteDatabase db = getReadableDatabase();
        String existe = "SELECT codusu FROM pessoa WHERE codusu = ? LIMIT 1";
        Cursor cursor = db.rawQuery(existe, new String[]{String.valueOf(pessoa.getCodusu())});
        int quantidade = cursor.getCount();
        return quantidade > 0;
    }

    public void altera(Pessoa pessoa) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues dados = getDadosPessoa(pessoa);

        String[] params = {pessoa.getCodusu().toString()};
        db.update("pessoa", dados, "codusu = ?", params);
    }
}
