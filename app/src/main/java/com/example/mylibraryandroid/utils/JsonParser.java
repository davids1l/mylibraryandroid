package com.example.mylibraryandroid.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import org.json.JSONException;
import org.json.JSONObject;

public class JsonParser {
    public static String parserJsonLogin(String response) {
        String token = null;

        try {
            JSONObject login = new JSONObject(response);

            if (login.getBoolean("success")) {
                token = login.getString("token");
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return token;
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
