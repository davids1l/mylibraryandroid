package com.example.mylibraryandroid.utils;

import com.example.mylibraryandroid.modelo.Autor;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class AutorJsonParser {

    public static ArrayList<Autor> parserJsonAutores(JSONArray response){
        ArrayList<Autor> autores = new ArrayList<>();

        try {
            for (int i=0; i<response.length(); i++){
                JSONObject autor = (JSONObject) response.get(i);
                int id_autor = autor.getInt("id_autor");
                String nome_autor = autor.getString("nome_autor");
                int id_pais = autor.getInt("id_pais");

                Autor auxAutor = new Autor(id_autor, nome_autor, id_pais);
                autores.add(auxAutor);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return autores;
    }

}
