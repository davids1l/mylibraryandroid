package com.example.mylibraryandroid.modelo;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class BDHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "mylibraryBD";
    private static final int DB_VERSION = 1;

    private static final String TABLE_NAME = "livro";
    private static final String ID_LIVRO = "id_livro";
    private static final String TITULO_LIVRO = "titulo";
    private static final String ISBN_LIVRO = "isbn";
    private static final String ANO_LIVRO = "ano";
    private static final String PAGINAS_LIVRO = "livro";
    private static final String GENERO_LIVRO = "genero";
    private static final String IDIOMA_LIVRO = "idioma";
    private static final String FORMATO_LIVRO = "formato";
    private static final String CAPA_LIVRO = "capa";
    private static final String SINOPSE_LIVRO = "sinopse";
    private static final String EDITORA_LIVRO = "id_editora";
    private static final String BIBLIOTECA_LIVRO = "id_biblioteca";
    private static final String AUTOR_LIVRO = "id_autor";

    private final SQLiteDatabase db;


    public BDHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);

        //inicializar o objeto SQLiteDatabase
        this.db = this.getWritableDatabase();
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        //SQL de criação da tabela livro
        String createTableLivro = "CREATE TABLE "+TABLE_NAME+"( " +
                ID_LIVRO+" INTEGER PRIMARY KEY, " +
                TITULO_LIVRO+" TEXT NOT NULL, " +
                ISBN_LIVRO+" INTEGER NOT NULL, " +
                ANO_LIVRO+" INTEGER NOT NULL, " +
                PAGINAS_LIVRO+" INTEGER NOT NULL, " +
                GENERO_LIVRO+" TEXT NOT NULL, " +
                IDIOMA_LIVRO+" TEXT NOT NULL, " +
                FORMATO_LIVRO+" TEXT NOT NULL, " +
                CAPA_LIVRO+" TEXT NOT NULL, " +
                SINOPSE_LIVRO+" TEXT NOT NULL, " +
                EDITORA_LIVRO+" INTEGER NOT NULL, " +
                BIBLIOTECA_LIVRO+" INTEGER NOT NULL, " +
                AUTOR_LIVRO+" INTEGER NOT NULL );";

        db.execSQL(createTableLivro);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //eliminar a BD para ser populada pela API
        String deleteTableLivros="DROP TABLE IF EXISTS " + TABLE_NAME;
        db.execSQL(deleteTableLivros);

        //criar a BD novamente
        this.onCreate(db);
    }

    public ArrayList<Livro> getAllLivrosDB() {
        ArrayList<Livro> livros = new ArrayList<>();

        Cursor cursor = this.db.query(TABLE_NAME, new String[]{ID_LIVRO, TITULO_LIVRO, ISBN_LIVRO, ANO_LIVRO, PAGINAS_LIVRO, GENERO_LIVRO,
                IDIOMA_LIVRO, FORMATO_LIVRO, CAPA_LIVRO, SINOPSE_LIVRO, EDITORA_LIVRO, BIBLIOTECA_LIVRO, AUTOR_LIVRO},
                null, null, null, null, null);

        if (cursor.moveToFirst()){
            do {
                Livro auxLivro = new Livro(cursor.getInt(0),cursor.getString(1), cursor.getInt(2), cursor.getInt(3), cursor.getInt(4), cursor.getString(5),
                        cursor.getString(6), cursor.getString(7), cursor.getString(8), cursor.getString(9), cursor.getInt(10),
                        cursor.getInt(11), cursor.getInt(12));

                livros.add(auxLivro);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return livros;
    }
}
