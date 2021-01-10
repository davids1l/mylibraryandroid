package com.example.mylibraryandroid.modelo;

import android.content.Context;
import android.widget.Toast;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.mylibraryandroid.listeners.LoginListener;
import com.example.mylibraryandroid.utils.JsonParser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Singleton {
    private static Singleton instance = null;
    private static RequestQueue volleyQueue = null;
    private static final String mUrlAPILogin = "http://localhost:8888/web/api/utilizador/login";
    private LoginListener loginListener;

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

}
