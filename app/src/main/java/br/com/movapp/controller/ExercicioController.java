package br.com.movapp.controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import br.com.movapp.database.Database;
import br.com.movapp.model.Exercicio;
import br.com.movapp.model.Serie;

public class ExercicioController {
    private Database database;
    private SerieController serieController;

    public ExercicioController(Context context){
        database = new Database(context);
        serieController = new SerieController(context);
    }

    public void insereExercicio(Exercicio exercicio) {
        if(existeExercicio(exercicio)){
            alteraExercicio(exercicio);
        }else{
            insereTabelaExercicio(exercicio);
        }
    }

    private void alteraExercicio(Exercicio exercicio) {
        SQLiteDatabase db = database.getWritableDatabase();

        ContentValues dados = getDadosExercicio(exercicio);

        String[] params = {exercicio.getCod().toString()};
        db.update(Database.TABLE_EXERCICIO, dados, "cod = ?", params);
    }

    private boolean existeExercicio(Exercicio exercicio) {
        SQLiteDatabase db = database.getReadableDatabase();
        String existe = "SELECT cod FROM "+Database.TABLE_EXERCICIO+ " WHERE cod = ? LIMIT 1";
        Cursor cursor = db.rawQuery(existe, new String[]{String.valueOf(exercicio.getCod())});
        int quantidade = cursor.getCount();
        return quantidade > 0;
    }

    private void insereTabelaExercicio(Exercicio exercicio) {
        SQLiteDatabase db = database.getWritableDatabase();
        ContentValues dados = getDadosExercicio(exercicio);
        db.insert(Database.TABLE_EXERCICIO, null, dados);

        inserTabelaExercicioSerie(dados.getAsLong("cod"));
    }

    public void inserTabelaExercicioSerie(Long exercicioCod){
        SQLiteDatabase db = database.getWritableDatabase();
        List<Serie> series = serieController.buscarSeries();
        for (Serie serie : series){
            ContentValues dados = new ContentValues();
            dados.put("exerciciocod", exercicioCod);
            dados.put("seriecod", serie.getCod());
            db.insert(Database.TABLE_EXERCICIO_SERIE, null, dados);
        }
    }

    @NonNull
    private ContentValues getDadosExercicio(Exercicio exercicio) {
        ContentValues dados = new ContentValues();
        dados.put("cod", exercicio.getCod());
        dados.put("exerciciocod", exercicio.getExerciciocod());
        dados.put("descanso", exercicio.getDescanso());
        return dados;
    }

    public Set<Exercicio> buscarExercicioInserido(Long codExercicio){
        SQLiteDatabase db = database.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM  " + Database.TABLE_EXERCICIO + " WHERE cod = ? ", new String[]{String.valueOf(codExercicio)});

        Set<Exercicio> setExercicio = new HashSet<>();
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    Exercicio exercicio = new Exercicio();
                    exercicio.setCod(cursor.getLong(cursor.getColumnIndex("cod")));
                    setExercicio.add(exercicio);
                } while (cursor.moveToNext());
            }
            cursor.close();
        }

        cursor.close();
        db.close();

        return setExercicio;
    }

}
