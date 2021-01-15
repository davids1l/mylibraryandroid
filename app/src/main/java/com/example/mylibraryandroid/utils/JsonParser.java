package com.example.mylibraryandroid.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.example.mylibraryandroid.modelo.Utilizador;

import org.json.JSONException;
import org.json.JSONObject;

public class JsonParser {
    public static String[] parserJsonLogin(String response) {
        String[] dados = new String[2];

        try {
            JSONObject login = new JSONObject(response);

            if (login.getBoolean("success")) {
                dados[0] = login.getString("token");
                dados[1] = login.getString("id");
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return dados;
    }

    public static String parserJsonRegistar(String response){
        String result = null;

        try {
            JSONObject utilizador = new JSONObject(response);

            if (utilizador.getBoolean("success")) {
                result = utilizador.getString("result");
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static Utilizador parserJsonPerfil(String response){
        Utilizador utilizador = null;

        try {
            JSONObject perfil = new JSONObject(response);

            int id_utilizador = perfil.getInt("id_utilizador");
            String primeiro_nome = perfil.getString("primeiro_nome");
            String ultimo_nome = perfil.getString("ultimo_nome");
            String numero = perfil.getString("numero");
            //int bloqueado = perfil.getInt("bloqueado");
            String dta_bloqueado = perfil.getString("dta_bloqueado");
            String dta_nascimento = perfil.getString("dta_nascimento");
            String nif = perfil.getString("nif");
            String num_telemovel = perfil.getString("num_telemovel");
            String dta_registo = perfil.getString("dta_registo");
            String foto_perfil = perfil.getString("foto_perfil");
            //int id_biblioteca = perfil.getInt("id_biblioteca");

            utilizador = new Utilizador(id_utilizador, 0, nif, num_telemovel, primeiro_nome, ultimo_nome,
                    numero, dta_bloqueado, dta_nascimento, dta_registo, foto_perfil, 0);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return utilizador;
    }

    public static boolean isConnectionInternet(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();

        return ni != null && ni.isConnected();
    }

}
