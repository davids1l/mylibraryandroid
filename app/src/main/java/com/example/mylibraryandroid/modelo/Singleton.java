package com.example.mylibraryandroid.modelo;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.mylibraryandroid.listeners.LoginListener;
import com.example.mylibraryandroid.listeners.RegistarListener;
import com.example.mylibraryandroid.utils.JsonParser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Singleton {
    private static Singleton instance = null;
    private static RequestQueue volleyQueue = null;
    private static final String mUrlAPILogin = "http://192.168.1.77:8888/web/api/utilizador/login";
    private static final String mUrlAPIRegistar = "http://192.168.1.77:8888/web/api/utilizador/create";
    private LoginListener loginListener;
    private RegistarListener registarListener;

    public static synchronized Singleton getInstance(Context context) {
        if (instance == null) {
            instance = new Singleton(context);
            volleyQueue = Volley.newRequestQueue(context);
        }
        return instance;
    }

    private Singleton(Context context) {
        // Construtor
    }

    public void setLoginListener(LoginListener loginListener) {
        this.loginListener = loginListener;
    }

    public void setRegistarListener(RegistarListener registarListener){
        this.registarListener = registarListener;
    }

    public void loginAPI(final String email, final String password, final Context context) {
        StringRequest req = new StringRequest(Request.Method.POST, mUrlAPILogin, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String token = JsonParser.parserJsonLogin(response);
                if(loginListener != null){
                    loginListener.onValidateLogin(token, email);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("email", email);
                params.put("password", password);
                return params;
            }
        };
        volleyQueue.add(req);
    }

    public void registarAPI(final String primeiro_nome, final String ultimo_nome, final String email, final String dta_nascimento, final String nif, final String num_telemovel, final String password, final Context context){
        StringRequest req = new StringRequest(Request.Method.POST, mUrlAPIRegistar, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String result = JsonParser.parserJsonRegistar(response);
                if(registarListener != null){
                    registarListener.onValidateRegisto(result);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("primeiro_nome", primeiro_nome);
                params.put("ultimo_nome", ultimo_nome);
                params.put("email", email);
                params.put("dta_nascimento", dta_nascimento);
                params.put("nif", nif);
                params.put("num_telemovel", num_telemovel);
                params.put("password", password);
                return params;
            }
        };
        volleyQueue.add(req);
    }

}
