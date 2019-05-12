package br.com.movapp.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Database extends SQLiteOpenHelper {
    private static final String NOME_BANCO = "movimente";
    public static final String TABLE_USUARIO = "usuario";
    public static final String TABLE_GRUPO_MUSCULAR = "grupo_muscular";
    public static final String TABLE_EQUIPAMENTO = "equipamento";
    public static final String TABLE_EXERCICIO = "exercicio";

    public Database(Context context) {
        super(context, NOME_BANCO, null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String tableUsuario = "CREATE TABLE " +TABLE_USUARIO+ "(" +
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
        db.execSQL(tableUsuario);

        String tableGrupo = "CREATE TABLE " +TABLE_GRUPO_MUSCULAR+ "(" +
                "codgrupo INTEGER PRIMARY KEY, " +
                "descricao TEXT);";
        db.execSQL(tableGrupo);

        String tableEquipamento = "CREATE TABLE " +TABLE_EQUIPAMENTO+"(" +
                "codequip INTEGER PRIMARY KEY, " +
                "descricao TEXT);";
        db.execSQL(tableEquipamento);

        String tableExercicios = "CREATE TABLE " +TABLE_EXERCICIO+ "(" +
                "cod INTEGER PRIMARY KEY, " +
                "nome VARCHAR(45), "+
                "descricao TEXT, "+
                "image BLOB, " +
                "grupo INTEGER, " +
                "equipamento INTEGER, "+
                "FOREIGN KEY (grupo) REFERENCES " +TABLE_GRUPO_MUSCULAR +"(codgrupo), " +
                "FOREIGN KEY (equipamento) REFERENCES " +TABLE_EQUIPAMENTO+"(codequip));";
        db.execSQL(tableExercicios);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
