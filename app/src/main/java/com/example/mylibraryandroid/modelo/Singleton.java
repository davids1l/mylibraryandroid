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
import com.example.mylibraryandroid.listeners.BibliotecaListener;
import com.example.mylibraryandroid.listeners.CarrinhoListener;
import com.example.mylibraryandroid.listeners.CatalogoListener;
import com.example.mylibraryandroid.listeners.EditarPerfilListener;
import com.example.mylibraryandroid.listeners.FavoritoListener;
import com.example.mylibraryandroid.listeners.LoginListener;
import com.example.mylibraryandroid.listeners.PerfilListener;
import com.example.mylibraryandroid.listeners.RegistarListener;
import com.example.mylibraryandroid.utils.BibliotecaJsonParser;
import com.example.mylibraryandroid.utils.FavoritoJsonParser;
import com.example.mylibraryandroid.utils.JsonParser;
import com.example.mylibraryandroid.utils.LivroJsonParser;
import com.example.mylibraryandroid.vistas.MenuMainActivity;

import org.json.JSONArray;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Singleton {

    public static final String IP = "http://192.168.0.100";
    private static Singleton instance = null;
    private static RequestQueue volleyQueue = null;
    private static final String mUrlAPILogin = IP + ":8888/web/api/utilizador/login";
    private static final String mUrlAPIRegistar = IP + ":8888/web/api/utilizador/create";
    private static final String mUrlAPICatalogo = IP + ":8888/web/api/livro";
    private static final String mUrlAPIFavorito =  IP + ":8888/web/api/favorito/utilizador/";
    private static final String mUrlAPILeitor = IP + ":8888/web/api/utilizador/";
    private static final String mUrlAPILeitorEmail = IP + ":8888/web/api/user/";
    private static final String mUrlAPIEditarLeitor = IP + ":8888/web/api/utilizador/";
    private static final String mUrlAPIBiblioteca = IP + ":8888/web/api/biblioteca";
    private LoginListener loginListener;
    private RegistarListener registarListener;
    private CatalogoListener catalogoListener;
    private FavoritoListener favoritoListener;
    private CarrinhoListener carrinhoListener;
    private BibliotecaListener bibliotecaListener;
    private PerfilListener perfilListener;
    private EditarPerfilListener editarPerfilListener;
    private BDHelper bdHelper;
    private ArrayList<Livro> catalogo;
    private ArrayList<Favorito> favorito;
    private ArrayList<Integer> carrinho;
    private ArrayList<Biblioteca> bibliotecas;

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
        carrinho = new ArrayList<>();
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

    public void setCarrinhoListener(CarrinhoListener carrinhoListener){
        this.carrinhoListener = carrinhoListener;
    }

    public void setFavoritoListener(FavoritoListener favoritoListener) {
        this.favoritoListener = favoritoListener;
    }

    public void setPerfilListener(PerfilListener perfilListener) {
        this.perfilListener = perfilListener;
    }

    public void setEditarPerfilListener(EditarPerfilListener editarPerfilListener){
        this.editarPerfilListener = editarPerfilListener;
    }

    public void setBibliotecaListener(BibliotecaListener bibliotecaListener){
        this.bibliotecaListener = bibliotecaListener;
    }

    public void loginAPI(final String email, final String password, final Context context) {
        StringRequest req = new StringRequest(Request.Method.POST, mUrlAPILogin, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String[] dados = JsonParser.parserJsonLogin(response);
                if (loginListener != null) {
                    loginListener.onValidateLogin(dados[0], dados[1], email, dados[2]);
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

    public void adicionarLivrosBD(ArrayList<Livro> livros){
        bdHelper.removerAllLivroBD();
        for (Livro l:livros)
            adicionarLivroBD(l);
    }

    public Livro getLivro(int id_livro){
        for (Livro l: catalogo){
            if (l.getId_livro() == id_livro){
                return l;
            }
        }
        return null;
    }

    // Ir buscar os favoritos à base de dados.
    public ArrayList<Favorito> getFavoritosBD() {
        favorito = bdHelper.getAllFavoritosDB();
        return favorito;
    }

    public void adicionarFavoritoBD(Favorito favorito) {
        bdHelper.adicionarFavoritoBD(favorito);
    }

    public void adicionarFavoritosBD(ArrayList<Favorito> favoritos){
        bdHelper.removerAllLivroBD();
        for (Favorito f:favoritos)
            adicionarFavoritoBD(f);
    }

    public Favorito getFavorito(int id_favorito){
        for (Favorito f: favorito){
            if (f.getId_favorito() == id_favorito){
                return f;
            }
        }
        return null;
    }

    public ArrayList<Livro> getLivrosFavoritosBD() {
        ArrayList<Livro> livrosFav = new ArrayList<>();
        for (Favorito f: favorito){
            livrosFav.add(getLivro(f.getId_livro()));
        }
        return livrosFav;
    }

    /** Acesso aos livros pela API **/
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

    public void getFavoritoAPI(final Context context, final String id) {
        if (!FavoritoJsonParser.isConnectionInternet(context)) {
            Toast.makeText(context, R.string.noInternet, Toast.LENGTH_LONG).show();

            if (favoritoListener != null)
                favoritoListener.onRefreshFavoritoLivros(getLivrosFavoritosBD());
        } else {
            JsonArrayRequest req = new JsonArrayRequest(Request.Method.GET, mUrlAPIFavorito + id, null, new Response.Listener<JSONArray>() {

                @Override
                public void onResponse(JSONArray response) {
                    favorito = FavoritoJsonParser.parserJsonFavorito(response);
                    adicionarFavoritosBD(favorito);

                    if (favoritoListener != null)
                        favoritoListener.onRefreshFavoritoLivros(getLivrosFavoritosBD());
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

    /**
     * Acesso aos dados leitor através da API
     **/
    public void getDadosLeitorAPI(final Context context, final String id) {
        StringRequest req = new StringRequest(Request.Method.GET, mUrlAPILeitor + id, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Utilizador utilizador = JsonParser.parserJsonPerfil(response);

                if (perfilListener != null) {
                    perfilListener.onRefreshUtilizador(utilizador);
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        volleyQueue.add(req);
    }

    public void getLeitorEmailAPI(final Context context, final String id) {
        StringRequest req = new StringRequest(Request.Method.GET, mUrlAPILeitorEmail + id, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                String email = JsonParser.parserJsonPerfilEmail(response);

                if (perfilListener != null) {
                    perfilListener.onRefreshEmailUtilizador(email);
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        volleyQueue.add(req);
    }

    public Boolean adicionarCarrinho (int id_livro){

        //verifica se o arrayList já contem o id_livro a inserir
        //se não contem, insere e retorna a true (sucesso na inserção), caso contrário retorna false (já contem)
        if (!carrinho.contains(id_livro)){
            carrinho.add(id_livro);
            return true;
        }

        return false;
    }

    public ArrayList<Livro> getLivrosCarrinho(){
        ArrayList<Livro> livrosCarrinho = new ArrayList<>();

        for(int i = 0; i< carrinho.size(); i++) {
            livrosCarrinho.add(getLivro(carrinho.get(i)));
        }

        return livrosCarrinho;
    }

    public Boolean removerCarrinho(int id_livro){
        for (int i=0; i<carrinho.size(); i++){
            if (carrinho.get(i) == id_livro){
                carrinho.remove(i);
                return true;
            }
        }
        return false;
    }

    public void getBibliotecasAPI(final Context context){
        /*if (!LivroJsonParser.isConnectionInternet(context)){
            Toast.makeText(context, R.string.noInternet, Toast.LENGTH_LONG).show();
        } else {*/
        JsonArrayRequest req = new JsonArrayRequest(Request.Method.GET, mUrlAPIBiblioteca, null, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {
                bibliotecas = BibliotecaJsonParser.parserJsonBibliotecas(response);
                //adicionarFavoritosBD(favorito);

                if (bibliotecaListener != null)
                    bibliotecaListener.onRefreshBibliotecas(bibliotecas);
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        volleyQueue.add(req);
        // }
    }

    public ArrayList<Biblioteca> getBibliotecas(){
        ArrayList<Biblioteca> listaBibliotecas = new ArrayList<>();

        for (int i = 0; i<bibliotecas.size(); i++){
            listaBibliotecas.add(bibliotecas.get(i));
        }

        return listaBibliotecas;
    }

    public void atualizarDadosLeitorAPI(final Context context, final String nome, final String apelido, final String telemovel, final String dia, final String mes, final String ano, final String nif, final String id){
        StringRequest req = new StringRequest(Request.Method.PUT, mUrlAPILeitor + id, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Utilizador utilizador = JsonParser.parserJsonEditarPerfil(response);
                if (editarPerfilListener != null) {
                    editarPerfilListener.onRefreshPerfil();
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
                params.put("primeiro_nome", nome);
                params.put("ultimo_nome", apelido);
                params.put("num_telemovel", telemovel);
                params.put("dta_nascimento", ano + "-" + mes + "-" + dia);
                params.put("nif", nif);
                return params;
            }
        };
        volleyQueue.add(req);
    }


    public void atualizarEmailLeitorAPI(final Context context, final String email, final String id){
        StringRequest req = new StringRequest(Request.Method.PUT, mUrlAPILeitorEmail + id, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String email = JsonParser.parserJsonEditarEmail(response);
                if (editarPerfilListener != null) {
                    editarPerfilListener.onRefreshPerfil();
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
                return params;
            }
        };
        volleyQueue.add(req);
    }
}
