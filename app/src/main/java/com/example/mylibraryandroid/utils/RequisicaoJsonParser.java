package com.example.mylibraryandroid.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.example.mylibraryandroid.modelo.Requisicao;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class RequisicaoJsonParser {

    public static ArrayList<Requisicao> parserJsonRequisicoes(JSONArray response) {
        ArrayList<Requisicao> requisicoes = new ArrayList<>();

        try{
            for(int i = 0; i < response.length(); i++){
                JSONObject requisicao = (JSONObject) response.get(i);
                int id_requisicao = requisicao.getInt("id_requisicao");
                String dta_levantamento = requisicao.getString("dta_levantamento");
                String dta_entrega = requisicao.getString("dta_entrega");
                String estado = requisicao.getString("estado");
                int id_utilizado_req = requisicao.getInt("id_utilizador");
                int id_bib_levantamento = requisicao.getInt("id_bib_levantamento");

                Requisicao auxRequisicao = new Requisicao(id_requisicao, dta_levantamento, dta_entrega, estado, id_utilizado_req, id_bib_levantamento);
                requisicoes.add(auxRequisicao);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return requisicoes;
    }

    public static boolean isConnectionInternet(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();

        return ni != null && ni.isConnected();
    }

}
