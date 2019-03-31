package br.com.movapp.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;

import br.com.movapp.model.Usuario;

public class UsuarioDAO extends SQLiteOpenHelper {
    public UsuarioDAO(Context context) {
        super(context, "Usuario", null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE usuario(" +
                "codUsuario INTEGER PRIMARY KEY, " +
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

    public void insereUsuario(Usuario usuario) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues dados = getDadosUsuario(usuario);
        db.insert("Usuario", null, dados);
    }

    @NonNull
    private ContentValues getDadosUsuario(Usuario usuario) {
        ContentValues dados = new ContentValues();
        dados.put("nome", usuario.getNome());
       // dados.put("dtNascimento", usuario.getDtNascimento().toString());
       // dados.put("genero", usuario.getGenero());
        dados.put("altura", usuario.getAltura());
        dados.put("celular", usuario.getTelefone());
        dados.put("email", usuario.getEmail());
        dados.put("senha", usuario.getSenha());
       // dados.put("bloqueio", usuario.getBloqueio());
        return dados;
    }

    public boolean isUser(String email, String senha) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM usuario WHERE email = ? AND senha = ?", new String[]{email,senha});
        int resultados = c.getCount();
        c.close();
        return resultados > 0;
    }


}
