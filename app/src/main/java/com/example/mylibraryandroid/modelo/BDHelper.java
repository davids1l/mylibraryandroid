package com.example.mylibraryandroid.modelo;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.ArrayAdapter;

import java.lang.reflect.Array;
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

    private static final String TABLE_NAME_FAV = "favorito";
    private static final String ID_LIVRO_FAV = "id_livro";
    private static final String ID_FAVORITO_FAV = "id_favorito";
    private static final String ID_UTILIZADOR_FAV = "id_utilizador";
    private static final String DTA_FAV = "dta_favorito";

    private static final String TABLE_NAME_BIB = "biblioteca";
    private static final String ID_BIBLIOTECA = "id_biblioteca";
    private static final String NOME_BIBLIOTECA = "nome";
    private static final String COD_POSTAL_BIB = "cod_postal";

    private static final String TABLE_NAME_AUTOR = "autor";
    private static final String ID_AUTOR = "id_autor";
    private static final String NOME_AUTOR = "nome_autor";
    private static final String ID_PAIS_AUTOR = "id_pais";

    private static final String TABLE_NAME_EDITORA = "editora";
    private static final String ID_EDITORA = "id_editora";
    private static final String DESIGNACAO_EDITORA = "desginacao";
    private static final String ID_PAIS_EDITORA = "id_pais";

    private static final String TABLE_NAME_UTILIZADOR = "utilizador";
    private static final String ID_UTILIZADOR = "id_utilizador";
    private static final String NOME = "nome";
    private static final String APELIDO = "apelido";
    private static final String NUMERO_LEITOR = "numero_leitor";
    private static final String EMAIL = "email";
    private static final String BLOQUEADO = "bloqueado";
    private static final String DATA_BLOQUEADO = "data_bloqueado";
    private static final String DATA_NASCIMENTO = "data_nascimento";
    private static final String NIF = "nif";
    private static final String NUM_TELEMOVEL = "num_telemovel";
    private static final String DATA_REGISTO = "data_registo";
    private static final String FOTO_PERFIL = "foto_perfil";
    private static final String ID_BIBLIOTECA = "id_biblioteca";

    private static final String TABLE_NAME_COM = "comentario";
    private static final String ID_COMENTARIO_COM = "id_comentario";
    private static final String ID_LIVRO_COM = "id_livro";
    private static final String ID_UTILIZADOR_COM = "id_utilizador";
    private static final String COMENTARIO_COM = "comentario";
    private static final String DTA_COM = "dta_comentario";


    private final SQLiteDatabase db;


    public BDHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);

        //inicializar o objeto SQLiteDatabase
        this.db = this.getWritableDatabase();
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        //SQL da criação da tabela autor
        String createTableAutor = "CREATE TABLE "+TABLE_NAME_AUTOR+"( " +
                ID_AUTOR+" INTEGER PRIMARY KEY, " +
                NOME_AUTOR+" TEXT NOT NULL, " +
                ID_PAIS_AUTOR+" INTEGER NOT NULL );";

        db.execSQL(createTableAutor);

        // Sql de criação da tabela favorito
        String createTableFavorito = "CREATE TABLE "+TABLE_NAME_FAV+"( " +
                ID_FAVORITO_FAV+" INTEGER PRIMARY KEY, " +
                DTA_FAV+" NUMERIC NOT NULL, " +
                ID_LIVRO_FAV+" INTEGER NOT NULL, " +
                ID_UTILIZADOR_FAV+" INTEGER NOT NULL );";

        db.execSQL(createTableFavorito);

        //SQL da criação da tabela editoras
        String createTableEditora = "CREATE TABLE "+TABLE_NAME_EDITORA+"( " +
                ID_EDITORA+" INTEGER PRIMARY KEY, " +
                DESIGNACAO_EDITORA+" TEXT NOT NULL, " +
                ID_PAIS_EDITORA+" INTEGER NOT NULL );" ;

        db.execSQL(createTableEditora);


        //SQL da criação da tabela biblioteca
        String createTableBiblioteca = "CREATE TABLE "+TABLE_NAME_BIB+"( " +
                ID_BIBLIOTECA+" INTEGER PRIMARY KEY, " +
                NOME_BIBLIOTECA+" TEXT NOT NULL, " +
                COD_POSTAL_BIB+" TEXT NOT NULL );";

        db.execSQL(createTableBiblioteca);

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
                AUTOR_LIVRO+" INTEGER NOT NULL, " +
                "FOREIGN KEY("+BIBLIOTECA_LIVRO+") REFERENCES "+TABLE_NAME_BIB+"("+ID_BIBLIOTECA+"), " +
                "FOREIGN KEY("+AUTOR_LIVRO+") REFERENCES "+TABLE_NAME_AUTOR+"("+ID_AUTOR+"), " +
                "FOREIGN KEY("+EDITORA_LIVRO+") REFERENCES "+TABLE_NAME_EDITORA+"("+ID_AUTOR+") );";

        db.execSQL(createTableLivro);

        // Sql de criação da tabela favorito
        String createTableFavorito = "CREATE TABLE "+TABLE_NAME_FAV+"( " +
                ID_FAVORITO_FAV+" INTEGER PRIMARY KEY, " +
                ID_LIVRO_FAV+" INTEGER NOT NULL, " +
                ID_UTILIZADOR_FAV+" INTEGER NOT NULL, " +
                DTA_FAV+" NUMERIC NOT NULL );";
        db.execSQL(createTableFavorito);


        //SQL da criação da tabela utilizador
        String createTableUtilizador = "CREATE TABLE "+TABLE_NAME_UTILIZADOR+"( " +
                ID_UTILIZADOR+" INTEGER PRIMARY KEY, " +
                NOME+" TEXT NOT NULL, " +
                APELIDO+" TEXT NOT NULL, " +
                NUMERO_LEITOR+" TEXT NOT NULL, " +
                EMAIL+" TEXT NOT NULL, " +
                //BLOQUEADO + " INTEGER, " +
                //DATA_BLOQUEADO + " TEXT, " +
                DATA_NASCIMENTO+" TEXT NOT NULL, " +
                NIF+" INTEGER NOT NULL, " +
                NUM_TELEMOVEL+" INTEGER NOT NULL, " +
                //DATA_REGISTO + " TEXT NOT NULL, " +
                FOTO_PERFIL+" TEXT NOT NULL);";
                //ID_BIBLIOTECA + " INTEGER);";

        db.execSQL(createTableUtilizador);

        // Sql de criação da tabela comentario
        String createTableComentario = "CREATE TABLE "+TABLE_NAME_COM+"( " +
                ID_COMENTARIO_COM+" INTEGER PRIMARY KEY, " +
                ID_LIVRO_COM+" INTEGER NOT NULL, " +
                ID_UTILIZADOR_COM+" INTEGER NOT NULL, " +
                COMENTARIO_COM+" TEXT NOT NULL, " +
                DTA_COM+" NUMERIC NOT NULL );";
        db.execSQL(createTableComentario);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //eliminar a BD para ser populada pela API
        String deleteTableLivros="DROP TABLE IF EXISTS " + TABLE_NAME;
        db.execSQL(deleteTableLivros);

        String deleteTableFavoritos="DROP TABLE IF EXISTS " + TABLE_NAME_FAV;
        db.execSQL(deleteTableFavoritos);

        String deleteTableBibliotecas="DROP TABLE IF EXISTS " + TABLE_NAME_BIB;
        db.execSQL(deleteTableBibliotecas);

        String deleteTableAutores="DROP TABLE IF EXISTS " + TABLE_NAME_AUTOR;
        db.execSQL(deleteTableAutores);

        String deleteTableEditoras="DROP TABLE IF EXISTS " + TABLE_NAME_EDITORA;
        db.execSQL(deleteTableEditoras);

        String deleteTableUtilizador="DROP TABLE IF EXISTS " + TABLE_NAME_UTILIZADOR;
        db.execSQL(deleteTableUtilizador);

        String deleteTableComentarios="DROP TABLE IF EXISTS " + TABLE_NAME_COM;
        db.execSQL(deleteTableComentarios);

        //criar a BD novamente
        this.onCreate(db);
    }

    public void adicionarLivroBD(Livro livro) {
        ContentValues values = new ContentValues();
        values.put(ID_LIVRO, livro.getId_livro());
        values.put(TITULO_LIVRO, livro.getTitulo());
        values.put(ISBN_LIVRO, livro.getIsbn());
        values.put(ANO_LIVRO, livro.getAno());
        values.put(PAGINAS_LIVRO, livro.getPaginas());
        values.put(GENERO_LIVRO, livro.getGenero());
        values.put(IDIOMA_LIVRO, livro.getIdioma());
        values.put(FORMATO_LIVRO, livro.getFormato());
        values.put(CAPA_LIVRO, livro.getCapa());
        values.put(SINOPSE_LIVRO, livro.getSinopse());
        values.put(EDITORA_LIVRO, livro.getId_editora());
        values.put(BIBLIOTECA_LIVRO, livro.getId_biblioteca());
        values.put(AUTOR_LIVRO, livro.getId_autor());

        this.db.insert(TABLE_NAME, null, values);
    }

    public void removerAllLivroBD(){
        this.db.delete(TABLE_NAME, null, null);
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

    public void adicionarFavoritoBD(Favorito favorito) {
        ContentValues values = new ContentValues();
        values.put(ID_FAVORITO_FAV, favorito.getId_favorito());
        values.put(ID_LIVRO_FAV, favorito.getId_livro());
        values.put(ID_UTILIZADOR_FAV, favorito.getId_utilizador());
        values.put(DTA_FAV, favorito.getDta_favorito());

        this.db.insert(TABLE_NAME_FAV, null, values);
    }

    public boolean removerFavoritoBD(int id) {
        int nRows = this.db.delete(TABLE_NAME_FAV, ID_FAVORITO_FAV+" = ?", new String[]{id + ""});
        return (nRows > 0);
    }

    public void removerAllFavoritoBD(){
        this.db.delete(TABLE_NAME_FAV, null, null);
    }

    public ArrayList<Favorito> getAllFavoritosDB() {
        ArrayList<Favorito> favoritos = new ArrayList<>();

        Cursor cursor = this.db.query(TABLE_NAME_FAV, new String[]{ID_FAVORITO_FAV,ID_LIVRO_FAV, ID_UTILIZADOR_FAV, DTA_FAV},
                null, null, null, null, null);

        if(cursor.moveToFirst()) {
            do {
                Favorito auxFavorito = new Favorito(cursor.getInt(0), cursor.getInt(1), cursor.getInt(2), cursor.getString(3));
                //TODO favoritos.add(auxFavorito);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return favoritos;
    }

    public void adicionarBibliotecaBD(Biblioteca biblioteca){
        ContentValues values = new ContentValues();
        values.put(ID_BIBLIOTECA, biblioteca.getId_biblioteca());
        values.put(NOME_BIBLIOTECA, biblioteca.getNome());
        values.put(COD_POSTAL_BIB, biblioteca.getCod_postal());

        this.db.insert(TABLE_NAME_BIB, null, values);
    }

    public void removerAllBibliotecasDB(){
        this.db.delete(TABLE_NAME_BIB, null, null);
    }

    public ArrayList<Biblioteca> getAllBibliotecasBD() {
        ArrayList<Biblioteca> bibliotecas = new ArrayList<>();

        Cursor cursor = this.db.query(TABLE_NAME_BIB, new String[]{ID_BIBLIOTECA, NOME_BIBLIOTECA, COD_POSTAL_BIB},
                null, null, null, null, null);

        if (cursor.moveToFirst()){
            do {
                Biblioteca auxBiblioteca = new Biblioteca(cursor.getInt(0), cursor.getString(1), cursor.getString(2));
                bibliotecas.add(auxBiblioteca);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return bibliotecas;
    }

    public void adicionarAutorDB(Autor autor){
        ContentValues values = new ContentValues();
        values.put(ID_AUTOR, autor.getId_autor());
        values.put(NOME_AUTOR, autor.getNome_autor());
        values.put(ID_PAIS_AUTOR, autor.getId_pais());

        this.db.insert(TABLE_NAME_AUTOR, null, values);
    }

    public void removerAllAutoresDB(){
        this.db.delete(TABLE_NAME_AUTOR, null, null);
    }

    public ArrayList<Autor> getAllAutoresDB() {
        ArrayList<Autor> autores = new ArrayList<>();

        Cursor cursor = this.db.query(TABLE_NAME_AUTOR, new String[]{ID_AUTOR, NOME_AUTOR, ID_PAIS_AUTOR},
                null, null, null, null, null);

        if (cursor.moveToFirst()){
            do {
                Autor auxAutor = new Autor(cursor.getInt(0), cursor.getString(1), cursor.getInt(2));
                autores.add(auxAutor);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return autores;
    }

    public void adicionarEditoraDB(Editora editora) {
        ContentValues values = new ContentValues();
        values.put(ID_EDITORA, editora.getId_editora());
        values.put(DESIGNACAO_EDITORA, editora.getDesignacao());
        values.put(ID_PAIS_EDITORA, editora.getId_pais());

        this.db.insert(TABLE_NAME_EDITORA, null, values);
    }

    public void removerAllEditorasDB() {
        this.db.delete(TABLE_NAME_EDITORA, null, null);
    }

    public ArrayList<Editora> getAllEditorasDB(){
        ArrayList<Editora> editoras = new ArrayList<>();

        Cursor cursor = this.db.query(TABLE_NAME_EDITORA, new String[]{ID_EDITORA, DESIGNACAO_EDITORA, ID_PAIS_EDITORA},
                null, null, null, null, null);

        if (cursor.moveToFirst()){
            do {
                Editora auxEditora = new Editora(cursor.getInt(0), cursor.getString(1), cursor.getInt(2));
                editoras.add(auxEditora);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return editoras;
    }




    public void adicionarLeitorBD(Utilizador utilizador) {
        ContentValues values = new ContentValues();
        values.put(ID_UTILIZADOR, utilizador.getId());
        values.put(NOME, utilizador.getPrimeiroNome());
        values.put(APELIDO, utilizador.getUltimoNome());
        values.put(NUMERO_LEITOR, utilizador.getNumero());
        values.put(EMAIL, utilizador.getEmail());
        values.put(DATA_NASCIMENTO, utilizador.getDtaNascimento());
        values.put(NIF, utilizador.getNif());
        values.put(NUM_TELEMOVEL, utilizador.getNumTelemovel());
        values.put(FOTO_PERFIL, utilizador.getFoto_perfil());

        this.db.insert(TABLE_NAME_UTILIZADOR, null, values);
    }

    public void removerLeitorBD(){
        this.db.delete(TABLE_NAME_UTILIZADOR, null, null);
    }

    public Utilizador getUtilizadorBD(){
        Utilizador utilizador = null;

        //TODO IR BUSCAR OS DADOS À BD
        Cursor cursor = this.db.query(TABLE_NAME_UTILIZADOR, new String[]{ID_UTILIZADOR,NOME,APELIDO,NUMERO_LEITOR,EMAIL,DATA_NASCIMENTO,NIF,NUM_TELEMOVEL,FOTO_PERFIL}
        ,null,null, null, null,null);
        return utilizador;
    }


    public void adicionarComentarioBD(Comentario comentario) {
        ContentValues values = new ContentValues();
        values.put(ID_COMENTARIO_COM, comentario.getId_comentario());
        values.put(ID_LIVRO_COM, comentario.getId_livro());
        values.put(ID_UTILIZADOR_COM, comentario.getId_utilizador());
        values.put(COMENTARIO_COM, comentario.getComentario());
        values.put(DTA_COM, comentario.getDta_comentario());

        this.db.insert(TABLE_NAME_COM, null, values);
    }

    public boolean removerComentarioBD(int id) {
        int nRows = this.db.delete(TABLE_NAME_COM, ID_COMENTARIO_COM+" = ?", new String[]{id + ""});
        return (nRows > 0);
    }

    public void removerAllComentarioBD(){
        this.db.delete(TABLE_NAME_COM, null, null);
    }

    public ArrayList<Comentario> getAllComentariosDB() {
        ArrayList<Comentario> comentarios = new ArrayList<>();

        Cursor cursor = this.db.query(TABLE_NAME_COM, new String[]{ID_COMENTARIO_COM,ID_LIVRO_COM, ID_UTILIZADOR_COM, COMENTARIO_COM, DTA_COM},
                null, null, null, null, null);

        if(cursor.moveToFirst()) {
            do {
                Comentario auxComentario = new Comentario(cursor.getInt(0), cursor.getInt(1), cursor.getInt(2), cursor.getString(3), cursor.getString(4));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return comentarios;
    }

    //Query to get the autor by it's id
}
