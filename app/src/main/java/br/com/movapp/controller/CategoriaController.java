package br.com.movapp.controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import br.com.movapp.database.Database;
import br.com.movapp.model.Categoria;

public class CategoriaController {
    private Database database;

    public CategoriaController(Context context){
        database = new Database(context);
    }

    public void insereCategoria(Categoria categoria, Long parentid) {
        if(existeCategoria(categoria)){
            alteraCategoria(categoria, parentid);
        }else{
            insereTabelaCategoria(categoria, parentid);
        }
    }

    private void alteraCategoria(Categoria categoria, Long parentId) {
        SQLiteDatabase db = database.getWritableDatabase();

        ContentValues dados = getDadosCategoria(categoria, parentId);

        String[] params = {categoria.getCod().toString()};
        db.update(Database.TABLE_CATEGORIA, dados, "cod = ?", params);
    }

    private boolean existeCategoria(Categoria categoria) {
        SQLiteDatabase db = database.getReadableDatabase();
        String existe = "SELECT cod FROM "+Database.TABLE_CATEGORIA+ " WHERE cod = ? LIMIT 1";
        Cursor cursor = db.rawQuery(existe, new String[]{String.valueOf(categoria.getCod())});
        int quantidade = cursor.getCount();
        return quantidade > 0;
    }

    private void insereTabelaCategoria(Categoria categoria, Long parentId) {
        SQLiteDatabase db = database.getWritableDatabase();
        ContentValues dados = getDadosCategoria(categoria, parentId);
        db.insert(Database.TABLE_CATEGORIA, null, dados);
    }

    @NonNull
    private ContentValues getDadosCategoria(Categoria categoria, Long parentId) {
        ContentValues dados = new ContentValues();
        dados.put("cod", categoria.getCod());
        dados.put("nome", categoria.getNome());
        dados.put("parentid", parentId);
        return dados;
    }


    public List<Categoria> buscarCategorias(){
        SQLiteDatabase db = database.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM  " + Database.TABLE_CATEGORIA +" WHERE parentid IS NULL", null);

        List<Categoria> arrayCategoria = new ArrayList<>();
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    Categoria categoria = new Categoria();
                    categoria.setCod(cursor.getLong(cursor.getColumnIndex("cod")));
                    categoria.setNome(cursor.getString(cursor.getColumnIndex("nome")));
                    arrayCategoria.add(categoria);
                } while (cursor.moveToNext());
            }
            cursor.close();
        }

        cursor.close();
        db.close();

        return arrayCategoria;
    }

    public List<Categoria> buscarSubCategorias(Long parentId){
        SQLiteDatabase db = database.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM  " + Database.TABLE_CATEGORIA +" WHERE parentid = ?" , new String[]{String.valueOf(parentId)});

        List<Categoria> arraySubCategoria = new ArrayList<>();
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    Categoria categoria = new Categoria();
                    categoria.setCod(cursor.getLong(cursor.getColumnIndex("cod")));
                    categoria.setNome(cursor.getString(cursor.getColumnIndex("nome")));
                    arraySubCategoria.add(categoria);
                } while (cursor.moveToNext());
            }
            cursor.close();
        }
        cursor.close();
        db.close();

        return arraySubCategoria;
    }

}
