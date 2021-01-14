package com.example.mylibraryandroid.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.example.mylibraryandroid.modelo.Livro;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class LivroJsonParser {

    public static ArrayList<Livro> parserJsonCatalogo(JSONArray response){
        ArrayList<Livro> catalogo = new ArrayList<>();

        try{
            for (int i = 0; i < (response.length()-1); i++){
                JSONObject livro = (JSONObject) response.get(i);
                int id_livro = livro.getInt("id_livro");
                String titulo = livro.getString("titulo");
                int isbn = livro.getInt("isbn");
                int ano = livro.getInt("ano");
                int paginas = livro.getInt("paginas");
                String genero = livro.getString("genero");
                String idioma = livro.getString("idioma");
                String formato = livro.getString("formato");
                String capa = livro.getString("capa");
                String sinopse = livro.getString("sinopse");
                int id_editora = livro.getInt("id_editora");
                int id_biblioteca = livro.getInt("id_biblioteca");
                int id_autor = livro.getInt("id_autor");

                Livro auxLivro = new Livro(id_livro, titulo, isbn, ano, paginas, genero, idioma, formato, capa, sinopse, id_editora, id_biblioteca, id_autor);
                catalogo.add(auxLivro);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return catalogo;
    }

    public static Livro parserJsonLivro(String response){
        Livro auxLivro = null;

        try {
            JSONObject livro = new JSONObject(response);
            int id_livro = livro.getInt("id_livro");
            String titulo = livro.getString("titulo");
            int isbn = livro.getInt("isbn");
            int ano = livro.getInt("ano");
            int paginas = livro.getInt("paginas");
            String genero = livro.getString("genero");
            String idioma = livro.getString("idioma");
            String formato = livro.getString("formato");
            String capa = livro.getString("capa");
            String sinopse = livro.getString("sinopse");
            int id_editora = livro.getInt("id_editora");
            int id_biblioteca = livro.getInt("id_biblioteca");
            int id_autor = livro.getInt("id_autor");

            auxLivro = new Livro(id_livro, titulo, isbn, ano, paginas, genero, idioma, formato, capa, sinopse, id_editora, id_biblioteca, id_autor);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return auxLivro;
    }

    public static boolean isConnectionInternet(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();

        return ni != null && ni.isConnected();
    }

}
