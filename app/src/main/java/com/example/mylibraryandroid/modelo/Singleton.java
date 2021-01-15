package com.example.mylibraryandroid.modelo;

import android.content.Context;
import android.content.SharedPreferences;
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
import com.example.mylibraryandroid.listeners.FavoritoListener;
import com.example.mylibraryandroid.listeners.LoginListener;
import com.example.mylibraryandroid.listeners.PerfilListener;
import com.example.mylibraryandroid.listeners.RegistarListener;
import com.example.mylibraryandroid.utils.JsonParser;
import com.example.mylibraryandroid.utils.LivroJsonParser;
import com.example.mylibraryandroid.vistas.MenuMainActivity;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Singleton {
    private static Singleton instance = null;
    private static RequestQueue volleyQueue = null;
    private static final String mUrlAPILogin = "http://192.168.0.101:8888/web/api/utilizador/login";
    private static final String mUrlAPIRegistar = "http://192.168.0.101:8888/web/api/utilizador/create";
    private static final String mUrlAPICatalogo = "http://192.168.0.101:8888/web/api/livro";
    private static final String mUrlAPIFavorito = "http://192.168.0.101:8888/web/api/favorito";
    private static final String mUrlAPILeitor = "http://192.168.0.101:8888/web/api/utilizador/";
    private LoginListener loginListener;
    private RegistarListener registarListener;
    private CatalogoListener catalogoListener;
    private FavoritoListener favoritoListener;
    private PerfilListener perfilListener;
    private BDHelper bdHelper;
    private ArrayList<Livro> catalogo;
    private ArrayList<Livro> favorito;

    public static synchronized Singleton getInstance(Context context) {
        if (instance == null) {
            instance = new Singleton(context);
            volleyQueue = Volley.newRequestQueue(context);
        }
        return instance;
    }

    private Singleton(Context context) {
        // Construtor
        catalogo = new ArrayList<>();
        favorito = new ArrayList<>();
        bdHelper = new BDHelper(context);
    }

    public void setLoginListener(LoginListener loginListener) {
        this.loginListener = loginListener;
    }

    public void setRegistarListener(RegistarListener registarListener) {
        this.registarListener = registarListener;
    }

    public void setCatalogoListener(CatalogoListener catalogoListener) {
        this.catalogoListener = catalogoListener;
    }

    public void setFavoritoListener(FavoritoListener favoritoListener) {
        this.favoritoListener = favoritoListener;
    }

    public void setPerfilListener(PerfilListener perfilListener){
        this.perfilListener = perfilListener;
    }

    public void loginAPI(final String email, final String password, final Context context) {
        StringRequest req = new StringRequest(Request.Method.POST, mUrlAPILogin, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String[] dados = JsonParser.parserJsonLogin(response);
                if (loginListener != null) {
                    loginListener.onValidateLogin(dados[0], dados[1], email);
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

    public void registarAPI(final String primeiro_nome, final String ultimo_nome, final String email, final String dta_nascimento, final String nif, final String num_telemovel, final String password, final Context context) {
        StringRequest req = new StringRequest(Request.Method.POST, mUrlAPIRegistar, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String result = JsonParser.parserJsonRegistar(response);
                if (registarListener != null) {
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


    /**
     * Acesso aos livro pela BD
     **/
    public ArrayList<Livro> getCatalogoBD() {
        catalogo = bdHelper.getAllLivrosDB();
        return catalogo;
    }

    public void adicionarLivroBD(Livro livro) {
        bdHelper.adicionarLivroBD(livro);
    }

    public void adicionarLivrosBD(ArrayList<Livro> livros) {
        bdHelper.removerAllLivroBD();
        for (Livro l : livros)
            adicionarLivroBD(l);
    }

    public Livro getLivro(int id_livro) {
        for (Livro l : catalogo) {
            if (l.getId_livro() == id_livro) {
                return l;
            }
        }
        return null;
    }

    // Ir buscar os favoritos à base de dados.
    public ArrayList<Livro> getFavoritosBD() {
        favorito = bdHelper.getAllFavoritosDB();
        return favorito;
    }


    /**
     * Acesso aos livros pela API
     **/
    public void getCatalogoAPI(final Context context) {
        if (!LivroJsonParser.isConnectionInternet(context)) {
            Toast.makeText(context, R.string.noInternet, Toast.LENGTH_LONG).show();

            if (catalogoListener != null)
                catalogoListener.onRefreshCatalogoLivros(bdHelper.getAllLivrosDB());
        } else {
            JsonArrayRequest req = new JsonArrayRequest(Request.Method.GET, mUrlAPICatalogo, null, new Response.Listener<JSONArray>() {

                @Override
                public void onResponse(JSONArray response) {
                    catalogo = LivroJsonParser.parserJsonCatalogo(response);
                    adicionarLivrosBD(catalogo);

                    if (catalogoListener != null)
                        catalogoListener.onRefreshCatalogoLivros(catalogo);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });/*{
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put("token", token);
                    return params;
                }
            };*/
            volleyQueue.add(req);
        }
    }

    public void getFavoritoAPI(final Context context) {
        if (!LivroJsonParser.isConnectionInternet(context)) {
            Toast.makeText(context, R.string.noInternet, Toast.LENGTH_LONG).show();
            //TODO: no internet -> ir buscar dados à BD local
            if (favoritoListener != null)
                favoritoListener.onRefreshFavoritoLivros(bdHelper.getAllFavoritosDB());
        } else {
            JsonArrayRequest req = new JsonArrayRequest(Request.Method.GET, mUrlAPIFavorito, null, new Response.Listener<JSONArray>() {

                @Override
                public void onResponse(JSONArray response) {
                    favorito = LivroJsonParser.parserJsonCatalogo(response);
                    //TODO: adicionar livros recebidos pela API à BD

                    if (favoritoListener != null)
                        favoritoListener.onRefreshFavoritoLivros(bdHelper.getAllFavoritosDB());
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

    /**
     * Acesso aos dados leitor através da API
     **/
    public void getDadosLeitorAPI(final Context context, final String id) {
        StringRequest req = new StringRequest(Request.Method.GET, mUrlAPILeitor + id, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Utilizador utilizador = JsonParser.parserJsonPerfil(response);

                if(perfilListener != null){
                    perfilListener.onRefreshUtilizador(utilizador);
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });/*{
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put("token", token);
                    return params;
                }
            };*/
        volleyQueue.add(req);
    }
}
