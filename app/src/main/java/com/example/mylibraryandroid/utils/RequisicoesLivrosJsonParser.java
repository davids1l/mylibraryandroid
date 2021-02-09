package com.example.mylibraryandroid.utils;

import com.example.mylibraryandroid.modelo.Requisicao;
import com.example.mylibraryandroid.modelo.RequisicaoLivro;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.GenericArrayType;
import java.util.ArrayList;

public class RequisicoesLivrosJsonParser {

    public static ArrayList<RequisicaoLivro> parserJsonRequisicoesLivros (JSONArray response){
        ArrayList<RequisicaoLivro> requisicoesLivros = new ArrayList<>();

        try{
            for(int i = 0; i < response.length(); i++){
                JSONObject requisicaoLivro = (JSONObject) response.get(i);
                int id_livro = requisicaoLivro.getInt("id_livro");
                int id_requisicao = requisicaoLivro.getInt("id_requisicao");

                RequisicaoLivro aux = new RequisicaoLivro(id_livro, id_requisicao);
                requisicoesLivros.add(aux);
            }

        } catch (JSONException e){
            e.printStackTrace();
        }
        return requisicoesLivros;
    }

}
