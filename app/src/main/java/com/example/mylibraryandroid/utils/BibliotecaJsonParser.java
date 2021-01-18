package com.example.mylibraryandroid.utils;

import com.example.mylibraryandroid.modelo.Biblioteca;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class BibliotecaJsonParser {

    public static ArrayList<Biblioteca> parserJsonBibliotecas(JSONArray response){
        ArrayList<Biblioteca> bibliotecas = new ArrayList<>();

        try{
            for (int i=0; i<response.length(); i++){
                JSONObject biblioteca = (JSONObject) response.get(i);
                int id_biblioteca = biblioteca.getInt("id_biblioteca");
                String nome = biblioteca.getString("nome");
                String cod_postal = biblioteca.getString("cod_postal");

                Biblioteca auxBiblioteca = new Biblioteca(id_biblioteca, nome, cod_postal);
                bibliotecas.add(auxBiblioteca);
            }
        }catch (JSONException e){
            e.printStackTrace();
        }
        return bibliotecas;
    }
}
