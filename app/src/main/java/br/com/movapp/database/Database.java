package br.com.movapp.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Database extends SQLiteOpenHelper {
    private static final String NOME_BANCO = "movimente";
    public static final String TABLE_USUARIO = "usuario";
    public static final String TABLE_CATEGORIA = "categoria";
    public static final String TABLE_TIPOEXERCICIO_CATEGORIA = "tipoexercicio_categoria";
    public static final String TABLE_TIPOEXERCICIO = "tipoexercicio";
    public static final String TABLE_SERIE = "serie";
    public static final String TABLE_EXERCICIO = "exercicio";
    public static final String TABLE_EXERCICIO_SERIE = "exercicio_serie";
    public static final String TABLE_TREINO = "treino";
    public static final String TABLE_TREINO_EXERCICIOS = "treino_exercicios";

    public Database(Context context) {
        super(context, NOME_BANCO, null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String tableUsuario = "CREATE TABLE " +TABLE_USUARIO+ "(" +
                "codusu INTEGER PRIMARY KEY, " +
                "nome TEXT NOT NULL, "+
                "foto BLOB , "+
                "dtnascimento DATE, " +
                "genero TEXT, " +
                "altura NUMERIC, " +
                "celular TEXT, " +
                "email TEXT NOT NULL, " +
                "senha TEXT, "+
                "bloqueio TEXT);";
        db.execSQL(tableUsuario);

        String tableCategoria = "CREATE TABLE " +TABLE_CATEGORIA+ "(" +
                "cod INTEGER PRIMARY KEY, " +
                "nome TEXT, "+
                "parentid INTEGER, "+
                "foreign key (parentid) references "+ TABLE_CATEGORIA+ "(cod));";
        db.execSQL(tableCategoria);

        String tableTipoExercicios = "CREATE TABLE " +TABLE_TIPOEXERCICIO+ "(" +
                "cod INTEGER PRIMARY KEY, " +
                "nome VARCHAR(45), "+
                "instrucao TEXT, "+
                "image BLOB);";
        db.execSQL(tableTipoExercicios);

        String tableTipoExercicioCatergoria = "CREATE TABLE " +TABLE_TIPOEXERCICIO_CATEGORIA+ "(" +
                "tipexercicio INTEGER, " +
                "categoriacod INTEGER, "+
                "primary key (tipexercicio, categoriacod), "+
                "foreign key (tipexercicio) references "+ TABLE_TIPOEXERCICIO + "(cod), " +
                "foreign key (categoriacod) references "+ TABLE_CATEGORIA + "(cod));";
        db.execSQL(tableTipoExercicioCatergoria);

        String tableSerie = "CREATE TABLE " +TABLE_SERIE+ "(" +
                "cod INTEGER, " +
                "tipo TEXT, "+
                "repeticao INTEGER, "+
                "carga INTEGER, "+
                "duracao INTEGER);";
        db.execSQL(tableSerie);

        String tableExercicio = "CREATE TABLE " +TABLE_EXERCICIO+ "(" +
                "cod INTEGER, " +
                "exerciciocod INTEGER, "+
                "descanso INTEGER, "+
                "foreign key (exerciciocod) references "+TABLE_EXERCICIO+  "(cod));";
        db.execSQL(tableExercicio);

        String tableExercicioSerie = "CREATE TABLE " +TABLE_EXERCICIO_SERIE+ "(" +
                "exerciciocod INTEGER, " +
                "seriecod INTEGER, "+
                "foreign key (exerciciocod) references "+ TABLE_EXERCICIO+ "(cod) ,"+
                "foreign key (seriecod) references "+TABLE_SERIE+ "(cod));";
        db.execSQL(tableExercicioSerie);

        String tableTreino = "CREATE TABLE " +TABLE_TREINO+ "(" +
                "cod INTEGER, " +
                "dia TEXT, "+
                "usuariocod INTEGER, "+
                "exercicioscod INTEGER, "+
                "primary key (usuariocod, exercicioscod), "+
                "foreign key (usuariocod) references "+ TABLE_USUARIO+ "(codusu) ,"+
                "foreign key (exercicioscod) references "+TABLE_EXERCICIO+ "(cod));";
        db.execSQL(tableTreino);

        String tableTreinoExercicios = "CREATE TABLE " +TABLE_TREINO_EXERCICIOS+ "(" +
                "treinocod INTEGER, " +
                "exercicioscod TEXT, "+
                "foreign key (treinocod) references "+ TABLE_TREINO+ "(cod) ,"+
                "foreign key (exercicioscod) references "+TABLE_EXERCICIO+ "(cod));";
        db.execSQL(tableTreinoExercicios);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
