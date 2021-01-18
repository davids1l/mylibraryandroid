package com.example.mylibraryandroid.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.example.mylibraryandroid.modelo.Comentario;
import com.example.mylibraryandroid.modelo.Favorito;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ComentarioJsonParser {
    public static ArrayList<Comentario> parserJsonComentario(JSONArray response){
        ArrayList<Comentario> comentarios = new ArrayList<>();

        try{
            for (int i = 0; i < response.length(); i++){
                JSONObject comentario = (JSONObject) response.get(i);
                if(comentario.getString("id_comentario").equals("false")) {
                    return comentarios;
                } else {
                    int id_comentario = comentario.getInt("id_comentario");
                    int id_livro = comentario.getInt("id_livro");
                    int id_utilizador = comentario.getInt("id_utilizador");
                    String comentarioc = comentario.getString("comentario");
                    String dta_comentario = comentario.getString("dta_comentario");

                    Comentario auxComentario = new Comentario(id_comentario, id_livro, id_utilizador, comentarioc, dta_comentario);
                    comentarios.add(auxComentario);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return comentarios;
    }

    public static Comentario parserJsonCom(String response){
        Comentario auxComentario = null;

        try{
            JSONObject comentario = new JSONObject(response);
            int id_comentario = comentario.getInt("id_comentario");
            int id_livro = comentario.getInt("id_livro");
            int id_utilizador = comentario.getInt("id_utilizador");
            String comentarioc = comentario.getString("comentario");
            String dta_comentario = comentario.getString("dta_comentario");

            auxComentario = new Comentario(id_comentario, id_livro, id_utilizador, comentarioc, dta_comentario);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return auxComentario;
    }

    public static boolean isConnectionInternet(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();

        return ni != null && ni.isConnected();
    }
}
