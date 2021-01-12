package com.example.mylibraryandroid.modelo;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.mylibraryandroid.R;
import com.example.mylibraryandroid.listeners.CatalogoListener;
import com.example.mylibraryandroid.listeners.LoginListener;
import com.example.mylibraryandroid.utils.JsonParser;
import com.example.mylibraryandroid.utils.LivroJsonParser;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Singleton {
    private static Singleton instance = null;
    private static RequestQueue volleyQueue = null;
    private static final String mUrlAPILogin = "http://192.168.1.100:8888/web/api/utilizador/login";
    private static final String mUrlAPICatalogo = "http://192.168.1.97:8888/web/api/livro";
    private LoginListener loginListener;
    private CatalogoListener catalogoListener;

    private BDHelper bdHelper;
    private ArrayList<Livro> livros;

    public static synchronized Singleton getInstance(Context context) {
        if (instance == null) {
            instance = new Singleton(context);
            volleyQueue = Volley.newRequestQueue(context);
        }
        return instance;
    }

    private Singleton(Context context) {
        // Construtor
        livros =  new ArrayList<>();
        bdHelper = new BDHelper(context);
    }

    public void setLoginListener(LoginListener loginListener) {
        this.loginListener = loginListener;
    }

    public void setCatalogoListener(CatalogoListener catalogoListener) {
        this.catalogoListener = catalogoListener;
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


    /** Acesso aos livro pela BD **/
    //TODO
    public ArrayList<Livro> getLivrosBD(){
        catalogo =
    }


    /** Acesso aos livros pela API **/
    public void getCatalogoAPI(final Context context) {
        if (!LivroJsonParser.isConnectionInternet(context)) {
            Toast.makeText(context, R.string.noInternet, Toast.LENGTH_LONG).show();
            //TODO: no internet -> ir buscar dados à BD local
            if (catalogoListener != null)
                catalogoListener.onRefreshCatalogoLivros(bdHelper.getAllLivrosDB());
        } else {
            JsonArrayRequest req = new JsonArrayRequest(Request.Method.GET, mUrlAPICatalogo, null, new Response.Listener<JSONArray>() {

                @Override
                public void onResponse(JSONArray response) {
                    livros = LivroJsonParser.parserJsonCatalogo(response);
                    //TODO: adicionar livros recebidos pela API à BD

                    if (catalogoListener != null)
                        catalogoListener.onRefreshCatalogoLivros(bdHelper.getAllLivrosDB());
                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
            volleyQueue.add(req);
        }
    }

}
