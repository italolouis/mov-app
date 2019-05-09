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
        super(context, "movimente", null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE usuario(" +
                "codusu INTEGER PRIMARY KEY, " +
                "nome TEXT NOT NULL, "+
                "foto BLOB NOT NULL, "+
                "dtnascimento DATE, " +
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

    public void insere(Usuario usuario) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues dados = getDadosPessoa(usuario);
        db.insert("usuario", null, dados);
    }

    @NonNull
    private ContentValues getDadosPessoa(Usuario usuario) {
        ContentValues dados = new ContentValues();
        dados.put("codusu", usuario.getCodusu());
        dados.put("foto", usuario.getFoto());
        dados.put("nome", usuario.getNome());
       // dados.put("dtNascimento", usuario.getDtNascimento().toString());
        dados.put("genero", usuario.getGenero());
        dados.put("altura", usuario.getAltura().doubleValue());
        dados.put("celular", usuario.getTelefone());
        dados.put("email", usuario.getEmail());
        dados.put("senha", usuario.getSenha());
       // dados.put("bloqueio", usuario.getBloqueio());
        return dados;
    }

    public boolean autenticaUsuario(String email, String senha) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM usuario WHERE email = ? AND senha = ?", new String[]{email,senha});
        int resultados = c.getCount();
        c.close();
        return resultados > 0;
    }

    public void deletaUsuarios() {
        SQLiteDatabase db = getWritableDatabase();
        db.delete("usuario", null, null);
    }

    public Usuario buscaUsuario() {
        Usuario usuario = new Usuario();
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM usuario", null);
        if(c.moveToFirst()){
            usuario.setCodusu(c.getLong(c.getColumnIndex("codusu")));
            usuario.setNome(c.getString(c.getColumnIndex("nome")));
            usuario.setFoto(c.getBlob(c.getColumnIndex("foto")));

        }
        return usuario;
    }

    public void insereUsuario(Usuario usuario) {
        if(existe(usuario)){
            altera(usuario);
        }else{
            insere(usuario);
        }
    }

    public boolean existeUsuario() {
        SQLiteDatabase db = getReadableDatabase();
        String existe = "SELECT codusu FROM usuario LIMIT 1";
        Cursor cursor = db.rawQuery(existe, null);
        int quantidade = cursor.getCount();
        return quantidade > 0;
    }

    private boolean existe(Usuario usuario) {
        SQLiteDatabase db = getReadableDatabase();
        String existe = "SELECT codusu FROM usuario WHERE codusu = ? LIMIT 1";
        Cursor cursor = db.rawQuery(existe, new String[]{String.valueOf(usuario.getCodusu())});
        int quantidade = cursor.getCount();
        return quantidade > 0;
    }

    public void altera(Usuario usuario) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues dados = getDadosPessoa(usuario);

        String[] params = {usuario.getCodusu().toString()};
        db.update("usuario", dados, "codusu = ?", params);
    }
}
