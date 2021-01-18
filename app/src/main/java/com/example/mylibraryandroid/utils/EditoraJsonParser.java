package com.example.mylibraryandroid.utils;

import com.example.mylibraryandroid.modelo.Editora;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class EditoraJsonParser {

    public static ArrayList<Editora> parserJsonEditora(JSONArray response){
        ArrayList<Editora> editoras = new ArrayList<>();

        try {
            for(int i=0; i<response.length(); i++){
                JSONObject editora = (JSONObject) response.get(i);
                int id_editora = editora.getInt("id_editora");
                String designacao = editora.getString("designacao");
                int id_pais = editora.getInt("id_pais");

                Editora auxEditora = new Editora(id_editora, designacao, id_pais);
                editoras.add(auxEditora);
            }
        } catch (JSONException e){
            e.printStackTrace();
        }
        return editoras;
    }

}
