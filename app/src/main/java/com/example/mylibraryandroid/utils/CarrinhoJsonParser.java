package com.example.mylibraryandroid.utils;

import com.example.mylibraryandroid.modelo.Carrinho;
import com.example.mylibraryandroid.modelo.Singleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CarrinhoJsonParser {

    public static String parserJsonCarrinho(ArrayList<Integer> carrinho){
        /**
         * 1- receber arrayList com os livros no carrinho
         * 2- para cada livro fazer o parse para JSON
         * */

        JSONObject carrinhoJsonObj = new JSONObject();

        JSONArray carrinhoArrayJson = new JSONArray();

        try{
            for (int i = 0; i<carrinho.size(); i++){
                JSONObject livro = new JSONObject();
                livro.put("id_livro", carrinho.get(i));
                carrinhoArrayJson.put(i, livro);
            }

            carrinhoJsonObj.put("livros_carrinho", carrinhoArrayJson);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        String carrinhoJsonString = carrinhoJsonObj.toString();

        return carrinhoJsonString;
    }
}
