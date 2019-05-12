package br.com.movapp.controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;

import java.math.BigDecimal;

import br.com.movapp.database.Database;
import br.com.movapp.model.Usuario;

public class UsuarioController {
    private Database database;

    public UsuarioController(Context context){
        database = new Database(context);
    }

    private void insere(Usuario usuario) {
        SQLiteDatabase db = database.getWritableDatabase();
        ContentValues dados = getDadosPessoa(usuario);
        db.insert(Database.TABLE_USUARIO, null, dados);
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

    public void deletaUsuarios() {
        SQLiteDatabase db = database.getWritableDatabase();
        db.delete("usuario", null, null);
    }

    public Usuario buscaUsuario() {
        Usuario usuario = new Usuario();
        SQLiteDatabase db = database.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM usuario", null);
        if(c.moveToFirst()){
            usuario.setCodusu(c.getLong(c.getColumnIndex("codusu")));
            usuario.setNome(c.getString(c.getColumnIndex("nome")));
            usuario.setFoto(c.getBlob(c.getColumnIndex("foto")));
            usuario.setGenero(c.getString(c.getColumnIndex("genero")));
            usuario.setAltura(BigDecimal.valueOf(c.getInt(c.getColumnIndex("altura"))));
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
        SQLiteDatabase db = database.getReadableDatabase();
        String existe = "SELECT codusu FROM usuario LIMIT 1";
        Cursor cursor = db.rawQuery(existe, null);
        int quantidade = cursor.getCount();
        return quantidade > 0;
    }

    private boolean existe(Usuario usuario) {
        SQLiteDatabase db = database.getReadableDatabase();
        String existe = "SELECT codusu FROM usuario WHERE codusu = ? LIMIT 1";
        Cursor cursor = db.rawQuery(existe, new String[]{String.valueOf(usuario.getCodusu())});
        int quantidade = cursor.getCount();
        return quantidade > 0;
    }

    public void altera(Usuario usuario) {
        SQLiteDatabase db = database.getWritableDatabase();

        ContentValues dados = getDadosPessoa(usuario);

        String[] params = {usuario.getCodusu().toString()};
        db.update(Database.TABLE_USUARIO, dados, "codusu = ?", params);
    }
}
