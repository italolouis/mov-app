package br.com.movapp.controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import br.com.movapp.database.Database;
import br.com.movapp.model.Serie;

public class SerieController {
    private Database database;

    public SerieController(Context context){
        database = new Database(context);
    }

    public void insereSerie(Serie serie) {
        if(existeCategoria(serie)){
            alteraCategoria(serie);
        }else{
            insereTabelaSerie(serie);
        }
    }

    private void alteraCategoria(Serie serie) {
        SQLiteDatabase db = database.getWritableDatabase();

        ContentValues dados = getDadosSerie(serie);

        String[] params = {serie.getCod().toString()};
        db.update(Database.TABLE_SERIE, dados, "cod = ?", params);
    }

    private boolean existeCategoria(Serie serie) {
        SQLiteDatabase db = database.getReadableDatabase();
        String existe = "SELECT cod FROM "+Database.TABLE_SERIE+ " WHERE cod = ? LIMIT 1";
        Cursor cursor = db.rawQuery(existe, new String[]{String.valueOf(serie.getCod())});
        int quantidade = cursor.getCount();
        return quantidade > 0;
    }

    private void insereTabelaSerie(Serie serie) {
        SQLiteDatabase db = database.getWritableDatabase();
        ContentValues dados = getDadosSerie(serie);
        db.insert(Database.TABLE_SERIE, null, dados);
    }

    @NonNull
    private ContentValues getDadosSerie(Serie serie) {
        ContentValues dados = new ContentValues();
        dados.put("cod", serie.getCod());
        dados.put("tipo", serie.getTipo());
        dados.put("repeticao", serie.getRepeticao());
        dados.put("carga", serie.getCarga());
        dados.put("duracao", serie.getDuracao());
        return dados;
    }

    public List<Serie> buscarSeries(){
        SQLiteDatabase db = database.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM  " + Database.TABLE_SERIE , null);

        List<Serie> listSerie = new ArrayList<>();
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    Serie serie = new Serie();
                    serie.setCod(cursor.getLong(cursor.getColumnIndex("cod")));
                    serie.setTipo(cursor.getString(cursor.getColumnIndex("tipo")));
                    serie.setTipo(cursor.getString(cursor.getColumnIndex("repeticao")));
                    listSerie.add(serie);
                } while (cursor.moveToNext());
            }
            cursor.close();
        }

        cursor.close();
        db.close();

        return listSerie;
    }

    public Serie ultimaSerie(){
        SQLiteDatabase db = database.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM  " + Database.TABLE_SERIE +" ORDER BY cod DESC LIMIT 1", null);
        Serie serie = new Serie();
        if(cursor.moveToFirst()){
            serie.setCod(cursor.getLong(cursor.getColumnIndex("cod")));
        }
        return serie;
    }

    public Set<Serie> buscarCodSeries(){
        SQLiteDatabase db = database.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM  " + Database.TABLE_SERIE , null);

        Set<Serie> setSerie = new HashSet<>();
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    Serie serie = new Serie();
                    serie.setCod(cursor.getLong(cursor.getColumnIndex("cod")));
                    setSerie.add(serie);
                } while (cursor.moveToNext());
            }
            cursor.close();
        }

        cursor.close();
        db.close();

        return setSerie;
    }

    public void deletaSeries() {
        SQLiteDatabase db = database.getWritableDatabase();
        db.delete(Database.TABLE_SERIE, null, null);
    }
}
