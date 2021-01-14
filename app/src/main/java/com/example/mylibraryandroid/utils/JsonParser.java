package com.example.mylibraryandroid.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Console;
import java.util.HashMap;
import java.util.Map;

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

    public static boolean isConnectionInternet(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();

        return ni != null && ni.isConnected();
    }

}
