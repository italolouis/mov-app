package br.com.movapp.controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.util.ArraySet;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import br.com.movapp.database.Database;
import br.com.movapp.model.Exercicio;
import br.com.movapp.model.Serie;
import br.com.movapp.model.TipoExercicio;
import br.com.movapp.model.Treino;

public class TreinoController {
    private Database database;

    public TreinoController(Context context){
        database = new Database(context);
    }

    public void insereTreino(Treino treino) {
        if(existeTreino(treino)){
            alteraTreino(treino);
        }else{
            insereTabelaTreino(treino);
        }
    }

    private void alteraTreino(Treino treino) {
        SQLiteDatabase db = database.getWritableDatabase();

        ContentValues dados = getDadosTreino(treino);

        String[] params = {treino.getCod().toString()};
        db.update(Database.TABLE_TREINO, dados, "cod = ?", params);
    }

    private boolean existeTreino(Treino treino) {
        SQLiteDatabase db = database.getReadableDatabase();
        String existe = "SELECT cod FROM "+Database.TABLE_TREINO+ " WHERE cod = ? LIMIT 1";
        Cursor cursor = db.rawQuery(existe, new String[]{String.valueOf(treino.getCod())});
        int quantidade = cursor.getCount();
        return quantidade > 0;
    }

    private void insereTabelaTreino(Treino treino) {
        SQLiteDatabase db = database.getWritableDatabase();
        ContentValues dados = getDadosTreino(treino);
        db.insert(Database.TABLE_TREINO, null, dados);
        inserTabelaTreinoExercicio(treino);
    }

    public void inserTabelaTreinoExercicio(Treino treino){
        SQLiteDatabase db = database.getWritableDatabase();
        Long codTreino = treino.getCod();
        for (Exercicio exercicio : treino.getExercicios()){
            ContentValues dados = new ContentValues();
            dados.put("treinocod", codTreino);
            dados.put("exercicioscod", exercicio.getCod());
            db.insert(Database.TABLE_TREINO_EXERCICIOS, null, dados);
        }
    }

    @NonNull
    private ContentValues getDadosTreino(Treino treino) {
        ContentValues dados = new ContentValues();
        dados.put("cod", treino.getCod());
        dados.put("dia", treino.getDia());
        dados.put("usuariocod", treino.getUsuario().getCodusu());
        dados.put("exercicioscod", getExercicioCod(treino).getCod());
        return dados;
    }

    private Exercicio getExercicioCod(Treino treino){
        Exercicio returnExercicio = new Exercicio();
        for (Exercicio exercicio: treino.getExercicios()){
            returnExercicio.setCod(exercicio.getCod());
        }
        return returnExercicio;
    }

    public List<String> getDiasTrino(){
        SQLiteDatabase db = database.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT DISTINCT dia FROM "
                + Database.TABLE_TREINO , null);

        List<String> listDia = new ArrayList<>();
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    listDia.add(cursor.getString(cursor.getColumnIndex("dia")));
                } while (cursor.moveToNext());
            }
            cursor.close();
        }

        cursor.close();
        db.close();

        return listDia;
    }

    public List<TipoExercicio> buscarExerciciosdoDia(String dia){
        SQLiteDatabase db = database.getReadableDatabase();

        Cursor cursor = db.rawQuery("select * from " +
                Database.TABLE_TREINO + " t, " +
                Database.TABLE_TREINO_EXERCICIOS + " te, " +
                Database.TABLE_EXERCICIO + " ex, " +
                Database.TABLE_TIPOEXERCICIO + " tpe "+
                "where t.cod = te.treinocod " +
                "and ex.cod = te.treinocod " +
                "and ex.exerciciocod = tpe.cod " +
                "and t.dia = ? ", new String[] {dia});

        List<TipoExercicio> arrayExercicios = new ArrayList<>();
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    TipoExercicio tipoExercicio = new TipoExercicio();
                    tipoExercicio.setCod(cursor.getLong(cursor.getColumnIndex("cod")));
                    tipoExercicio.setNome(cursor.getString(cursor.getColumnIndex("nome")));
                    tipoExercicio.setInstrucao(cursor.getString(cursor.getColumnIndex("instrucao")));
                    tipoExercicio.setImage(cursor.getBlob(cursor.getColumnIndex("image")));
                    arrayExercicios.add(tipoExercicio);
                } while (cursor.moveToNext());
            }
            cursor.close();
        }

        cursor.close();
        db.close();
        return arrayExercicios;
    }

    public List<Exercicio> getListExerciciosTreino(String dia, Long codExericicio){
        SQLiteDatabase db = database.getReadableDatabase();
        String[] params = new String[]{dia};

        StringBuffer sql = new StringBuffer();
        sql.append("select ")
                .append("t.cod as treinocod, ")
                .append("ex.cod as exerciciocod, ")
                .append("tpe.cod as codexercicio, ")
                .append("tpe.nome as nomeexercicio, ")
                .append("tpe.instrucao as instrucao, ")
                .append("tpe.image as imageexercicio, ")
               // .append("se.cod as codserie, ")
              //  .append("se.duracao,")
              //  .append("se.repeticao, ")
              //  .append("se.carga, ")
              //  .append("se.tipo as tiposerie, ")
                .append("ex.descanso ")
                .append(" from ")
                .append(Database.TABLE_TREINO + " t, " +
                        Database.TABLE_TREINO_EXERCICIOS + " te, " +
                        Database.TABLE_EXERCICIO + " ex, " +
                       // Database.TABLE_EXERCICIO_SERIE + " es, "+
                        //Database.TABLE_SERIE + " se, "+
                        Database.TABLE_TIPOEXERCICIO + " tpe ")
                .append("where t.cod = te.treinocod ")
                .append("and ex.cod = te.treinocod ")
                //.append("and es.exerciciocod = ex.cod ")
                //.append("and se.cod = es.seriecod ")
                .append("and ex.exerciciocod = tpe.cod " )
                .append("and t.dia = ? ");

        if(codExericicio != null && codExericicio != 0){
            sql.append("and tpe.cod = ?" );
            params = new String[]{dia, codExericicio.toString()};
        }

        Cursor cursor = db.rawQuery(sql.toString(), params);

        List<Exercicio> exercicioList = new ArrayList<>();
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    TipoExercicio tipoExercicio = new TipoExercicio();
                    tipoExercicio.setCod(cursor.getLong(cursor.getColumnIndex("codexercicio")));
                    tipoExercicio.setNome(cursor.getString(cursor.getColumnIndex("nomeexercicio")));
                    tipoExercicio.setInstrucao(cursor.getString(cursor.getColumnIndex("instrucao")));
                    tipoExercicio.setImage(cursor.getBlob(cursor.getColumnIndex("imageexercicio")));

                    /*Serie serie = new Serie();
                    Set<Serie> arraySerie = new HashSet<Serie>();
                    serie.setCod(cursor.getLong(cursor.getColumnIndex("codserie")));
                    serie.setTipo(cursor.getString(cursor.getColumnIndex("tiposerie")));
                    serie.setDuracao(cursor.getLong(cursor.getColumnIndex("duracao")));
                    serie.setCarga(cursor.getLong(cursor.getColumnIndex("carga")));
                    serie.setRepeticao(cursor.getLong(cursor.getColumnIndex("repeticao")));
                    arraySerie.add(serie);*/

                    Exercicio exercicio  = new Exercicio();
                    exercicio.setCod(cursor.getLong(cursor.getColumnIndex("exerciciocod")));
                    exercicio.setTipoExercicios(tipoExercicio);
                   // exercicio.setSeries(arraySerie);
                    exercicio.setDescanso(cursor.getLong(cursor.getColumnIndex("descanso")));
                    exercicioList.add(exercicio);
                } while (cursor.moveToNext());
            }
            cursor.close();
        }

        cursor.close();
        db.close();
        return exercicioList;
    }
}
