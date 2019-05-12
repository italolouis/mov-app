package br.com.movapp.controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import br.com.movapp.database.Database;
import br.com.movapp.model.Exercicio;

public class ExercicioController {
    private Database database;

    public ExercicioController(Context context){
        database = new Database(context);
    }

    public void insereExercicio(Exercicio exercicio) {
        if(existe(exercicio)){
            altera(exercicio);
        }else{
            insere(exercicio);
        }
    }

    private void altera(Exercicio exercicio) {
        SQLiteDatabase db = database.getWritableDatabase();

        ContentValues dados = getDadosExercicio(exercicio);

        String[] params = {exercicio.getCod().toString()};
        db.update(Database.TABLE_EXERCICIO, dados, "cod = ?", params);
    }

    private boolean existe(Exercicio exercicio) {
        SQLiteDatabase db = database.getReadableDatabase();
        String existe = "SELECT cod FROM "+Database.TABLE_EXERCICIO+ " WHERE cod = ? LIMIT 1";
        Cursor cursor = db.rawQuery(existe, new String[]{String.valueOf(exercicio.getCod())});
        int quantidade = cursor.getCount();
        return quantidade > 0;
    }

    private void insere(Exercicio exercicio) {
        SQLiteDatabase db = database.getWritableDatabase();
        ContentValues dados = getDadosExercicio(exercicio);
        db.insert(Database.TABLE_EXERCICIO, null, dados);
    }


    @NonNull
    private ContentValues getDadosExercicio(Exercicio exercicio) {
        ContentValues dados = new ContentValues();
        dados.put("cod", exercicio.getCod());
        dados.put("nome", exercicio.getNome());
        dados.put("descricao", exercicio.getDescricao());
        dados.put("image", exercicio.getImage());
        if(exercicio.getGrupo()!= null){
            dados.put("grupo", exercicio.getGrupo().getCodgrupo());
            dados.put("equipamento", exercicio.getEquipamento().getCodequip());
        }
        return dados;
    }

    public List<Exercicio> buscarExercicios(){
        SQLiteDatabase db = database.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM  " + Database.TABLE_EXERCICIO, null);

        List<Exercicio> arrayExercios = new ArrayList<>();
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    Exercicio exercicio = new Exercicio();
                    exercicio.setCod(cursor.getLong(cursor.getColumnIndex("cod")));
                    exercicio.setNome(cursor.getString(cursor.getColumnIndex("nome")));
                    exercicio.setDescricao(cursor.getString(cursor.getColumnIndex("descricao")));
                    exercicio.setImage(cursor.getBlob(cursor.getColumnIndex("image")));
                    //exercicio.setGrupo(cursor.getInt(cursor.getColumnIndex("grupo")));
                    arrayExercios.add(exercicio);
                } while (cursor.moveToNext());
            }
            cursor.close();
        }

        cursor.close();
        db.close();

        return arrayExercios;
    }


    public Exercicio buscaExercicio(Long cod) {
        Exercicio exercicio = new Exercicio();
        SQLiteDatabase db = database.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM exercicio WHERE cod = ?", new String[]{String.valueOf(cod)});
        if(cursor.moveToFirst()){
            exercicio.setCod(cursor.getLong(cursor.getColumnIndex("cod")));
            exercicio.setNome(cursor.getString(cursor.getColumnIndex("nome")));
            exercicio.setDescricao(cursor.getString(cursor.getColumnIndex("descricao")));
            exercicio.setImage(cursor.getBlob(cursor.getColumnIndex("image")));

        }
        return exercicio;
    }
}
