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
import br.com.movapp.model.Serie;
import br.com.movapp.model.TipoExercicio;

public class TipoExercicioController {
    private Database database;

    public TipoExercicioController(Context context){
        database = new Database(context);
    }

    public void insereExercicio(TipoExercicio tipoExercicio) {
        if(existe(tipoExercicio)){
            altera(tipoExercicio);
        }else{
            insere(tipoExercicio);
        }
    }

    private void altera(TipoExercicio tipoExercicio) {
        SQLiteDatabase db = database.getWritableDatabase();

        ContentValues dados = getDadosExercicio(tipoExercicio);

        String[] params = {tipoExercicio.getCod().toString()};
        db.update(Database.TABLE_TIPOEXERCICIO, dados, "cod = ?", params);
    }

    private boolean existe(TipoExercicio tipoExercicio) {
        SQLiteDatabase db = database.getReadableDatabase();
        String existe = "SELECT cod FROM "+Database.TABLE_TIPOEXERCICIO+ " WHERE cod = ? LIMIT 1";
        Cursor cursor = db.rawQuery(existe, new String[]{String.valueOf(tipoExercicio.getCod())});
        int quantidade = cursor.getCount();
        return quantidade > 0;
    }

    private void insere(TipoExercicio tipoExercicio) {
        SQLiteDatabase db = database.getWritableDatabase();
        ContentValues dados = getDadosExercicio(tipoExercicio);
        db.insert(Database.TABLE_TIPOEXERCICIO, null, dados);
    }

    public void insereExercicioCategoria(Categoria categoria, Long tipoExercicio) {
        SQLiteDatabase db = database.getWritableDatabase();
        ContentValues dados = getDadosExercicioCategoria(categoria, tipoExercicio);
        db.insert(Database.TABLE_TIPOEXERCICIO_CATEGORIA, null, dados);
    }


    @NonNull
    private ContentValues getDadosExercicio(TipoExercicio tipoExercicio) {
        ContentValues dados = new ContentValues();
        dados.put("cod", tipoExercicio.getCod());
        dados.put("nome", tipoExercicio.getNome());
        dados.put("instrucao", tipoExercicio.getInstrucao());
        dados.put("image", tipoExercicio.getImage());
        return dados;
    }

    @NonNull
    private ContentValues getDadosExercicioCategoria(Categoria categoria, Long tipoExercicio) {
        ContentValues dados = new ContentValues();
        dados.put("exerciciocod", tipoExercicio);
        dados.put("categoriacod", categoria.getCod());
        return dados;
    }

    public List<TipoExercicio> buscarExercicios(){
        SQLiteDatabase db = database.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM  " + Database.TABLE_TIPOEXERCICIO, null);

        List<TipoExercicio> arrayExercios = new ArrayList<>();
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    TipoExercicio tipoExercicio = new TipoExercicio();
                    tipoExercicio.setCod(cursor.getLong(cursor.getColumnIndex("cod")));
                    tipoExercicio.setNome(cursor.getString(cursor.getColumnIndex("nome")));
                    tipoExercicio.setInstrucao(cursor.getString(cursor.getColumnIndex("instrucao")));
                    tipoExercicio.setImage(cursor.getBlob(cursor.getColumnIndex("image")));
                    arrayExercios.add(tipoExercicio);
                } while (cursor.moveToNext());
            }
            cursor.close();
        }

        cursor.close();
        db.close();
        return arrayExercios;
    }

    public TipoExercicio buscaExercicio(Long cod) {
        TipoExercicio tipoExercicio = new TipoExercicio();
        SQLiteDatabase db = database.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " +Database.TABLE_TIPOEXERCICIO+ " WHERE cod = ?", new String[]{String.valueOf(cod)});
        if(cursor.moveToFirst()){
            tipoExercicio.setCod(cursor.getLong(cursor.getColumnIndex("cod")));
            tipoExercicio.setNome(cursor.getString(cursor.getColumnIndex("nome")));
            tipoExercicio.setInstrucao(cursor.getString(cursor.getColumnIndex("instrucao")));
            tipoExercicio.setImage(cursor.getBlob(cursor.getColumnIndex("image")));

        }
        return tipoExercicio;
    }

    public TipoExercicio buscaCodExercicio(Long cod) {
        TipoExercicio tipoExercicio = new TipoExercicio();
        SQLiteDatabase db = database.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " +Database.TABLE_TIPOEXERCICIO+ " WHERE cod = ?", new String[]{String.valueOf(cod)});
        if(cursor.moveToFirst()){
            tipoExercicio.setCod(cursor.getLong(cursor.getColumnIndex("cod")));
        }
        return tipoExercicio;
    }

}
