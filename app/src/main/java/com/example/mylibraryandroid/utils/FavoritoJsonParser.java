package com.example.mylibraryandroid.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.example.mylibraryandroid.modelo.Favorito;
import com.example.mylibraryandroid.modelo.Livro;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class FavoritoJsonParser {

    public static ArrayList<Favorito> parserJsonFavorito(JSONArray response){
        ArrayList<Favorito> favoritos = new ArrayList<>();

        try{
            for (int i = 0; i < (response.length()-1); i++){
                JSONObject favorito = (JSONObject) response.get(i);
                int id_favorito = favorito.getInt("id_favorito");
                int id_livro = favorito.getInt("id_livro");
                int id_utilizador = favorito.getInt("id_utilizador");
                String dta_favorito = favorito.getString("dta_favorito");

                Favorito auxFavorito = new Favorito(id_favorito, id_livro, id_utilizador, dta_favorito);
                favoritos.add(auxFavorito);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return favoritos;
    }

    public static boolean isConnectionInternet(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();

        return ni != null && ni.isConnected();
    }
}
