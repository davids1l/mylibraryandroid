package com.example.mylibraryandroid.modelo;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;


import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

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
import com.example.mylibraryandroid.listeners.ComentarioListener;
import com.example.mylibraryandroid.listeners.EditarPerfilListener;
import com.example.mylibraryandroid.listeners.FavoritoListener;
import com.example.mylibraryandroid.listeners.LoginListener;
import com.example.mylibraryandroid.listeners.PerfilListener;
import com.example.mylibraryandroid.listeners.RegistarListener;
import com.example.mylibraryandroid.utils.ComentarioJsonParser;
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

    public static final String IP = "http://192.168.1.100";
    private static Singleton instance = null;
    private static RequestQueue volleyQueue = null;
    private static final String mUrlAPILogin = IP + ":8888/web/api/utilizador/login";
    private static final String mUrlAPIRegistar = IP + ":8888/web/api/utilizador/create";
    private static final String mUrlAPICatalogo = IP + ":8888/web/api/livro";
    private static final String mUrlAPIFavorito =  IP + ":8888/web/api/favorito/utilizador/";
    private static final String mUrlAPIRemoverFavorito =  IP + ":8888/web/api/favorito/";
    private static final String mUrlAPIAdicionarFavorito =  IP + ":8888/web/api/favorito";
    private static final String mUrlAPILeitor = IP + ":8888/web/api/utilizador/";
    private static final String mUrlAPIEditarLeitor = IP + ":8888/web/api/utilizador/";
    private static final String mUrlAPIComentario = IP + ":8888/web/api/favorito/utilizador/";
    private LoginListener loginListener;
    private RegistarListener registarListener;
    private CatalogoListener catalogoListener;
    private FavoritoListener favoritoListener;
    private ComentarioListener comentarioListener;
    private PerfilListener perfilListener;
    private EditarPerfilListener editarPerfilListener;
    private BDHelper bdHelper;
    private ArrayList<Livro> catalogo;
    private ArrayList<Favorito> favorito;
    private ArrayList<Comentario> comentario;
    private ArrayList<Livro> carrinho;

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

    public void setFavoritoListener(FavoritoListener favoritoListener) {
        this.favoritoListener = favoritoListener;
    }

    public void setPerfilListener(PerfilListener perfilListener) {
        this.perfilListener = perfilListener;
    }

    public void setEditarPerfilListener(EditarPerfilListener editarPerfilListener){
        this.editarPerfilListener = editarPerfilListener;
    }

    public void setComentarioListener(ComentarioListener comentarioListener) {
        this.comentarioListener = comentarioListener;
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
    public ArrayList<Favorito> getFavoritosBD() {
        favorito = bdHelper.getAllFavoritosDB();
        return favorito;
    }

    public void adicionarFavoritoBD(Favorito favorito) {
        bdHelper.adicionarFavoritoBD(favorito);
    }

    public void adicionarFavoritosBD(ArrayList<Favorito> favoritos){
        bdHelper.removerAllFavoritoBD();
        for (Favorito f: favoritos) {
            adicionarFavoritoBD(f);
        }
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

    public void removerFavoritoBD(int id) {
        Favorito f = getFavorito(id);

        if(f != null) {
            bdHelper.removerFavoritoBD(id);
        }
    }

    public String findFavoritoByIDS(int id_livro, int id_utilizador) {
        for (Favorito f: favorito) {
            if(f.getId_livro() == id_livro && f.getId_utilizador() == id_utilizador) {
                return f.getId_favorito() + "";
            }
        }
        return null;
    }

    public ArrayList<Comentario> getComentariosBD() {
        comentario = bdHelper.getAllComentariosDB();
        return comentario;
    }

    public void adicionarComentarioBD(Comentario comentario) {
        bdHelper.adicionarComentarioBD(comentario);
    }

    public void adicionarComentariosBD(ArrayList<Comentario> comentarios) {
        bdHelper.removerAllComentarioBD();
        for (Comentario c: comentarios) {
            adicionarComentarioBD(c);
        }
    }

    public Comentario getComentario(int id_comentario) {
        for(Comentario c: comentario) {
            if(c.getId_comentario() == id_comentario) {
                return c;
            }
        }
        return null;
    }

    public void removerComentarioBD(int id) {
        Comentario c = getComentario(id);

        if(c != null) {
            bdHelper.removerComentarioBD(id);
        }
    }

    public String findComentariosByIDS(int id_livro, int id_utilizador) {
        for(Comentario c: comentario) {
            if(c.getId_livro() == id_livro && c.getId_utilizador() == id_utilizador) {
                return c.getId_comentario() + "";
            }
        }
        return null;
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

    /**
     * Acesso aos favoritos pela API
     **/
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
                    if(!favorito.isEmpty()) {
                        adicionarFavoritosBD(favorito);
                    }

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

    public void adicionarFavoritoAPI(final Context context, final int id_utilizador, final int id_livro) {
        StringRequest req = new StringRequest(Request.Method.POST, mUrlAPIAdicionarFavorito, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Favorito f = FavoritoJsonParser.parserJsonFav(response);
                getFavoritoAPI(context, id_utilizador+"");

                if(favoritoListener != null){
                    favoritoListener.onRefreshDetalhes();
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
                params.put("id_livro", id_livro+"");
                params.put("id_utilizador", id_utilizador+"");
                return params;
            }
        };
        volleyQueue.add(req);
    }

    public void removerFavoritoAPI(final Context context, final int id_utilizador, int id_livro) {
        final String id_favorito = findFavoritoByIDS(id_livro, id_utilizador);
        StringRequest req = new StringRequest(Request.Method.DELETE, mUrlAPIRemoverFavorito + id_favorito, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Favorito f = FavoritoJsonParser.parserJsonFav(response);
                int id = Integer.parseInt(id_favorito);
                removerFavoritoBD(id);
                getFavoritoAPI(context, id_utilizador+"");

                if (favoritoListener != null){
                    favoritoListener.onRefreshDetalhes();
                    //favoritoListener.onRefreshFavoritoLivros(getLivrosFavoritosBD());
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

    public void adicionarCarrinho (int id_livro){
        //ArrayList<Livro> carrinhoLivros = new ArrayList<>();

        Livro livro =  getLivro(id_livro);

        if (!favorito.contains(livro)){
            carrinho.add(getLivro(id_livro));
        }
    }

    public ArrayList<Livro> getLivrosCarrinho(){
        ArrayList<Livro> livrosCarrinho = new ArrayList<>();

        for(int i = 0; i< carrinho.size(); i++) {
            livrosCarrinho.add(carrinho.get(i));
        }

        return livrosCarrinho;
    }

    public void atualizarDadosLeitorAPI(final Context context, final String nome, final String apelido, final String telemovel, final String dia, final String nif, final String id){
        StringRequest req = new StringRequest(Request.Method.PUT, mUrlAPIEditarLeitor + id, new Response.Listener<String>() {
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
                params.put("dta_nascimento", dia);
                params.put("nif", nif);
                return params;
            }
        };
        volleyQueue.add(req);
    }

    /**
     * Acesso aos comentarios pela API
     **/
    public void getComentarioAPI(final Context context, final String id) {
        if (!ComentarioJsonParser.isConnectionInternet(context)) {
            Toast.makeText(context, R.string.noInternet, Toast.LENGTH_LONG).show();

            if (comentarioListener != null)
                comentarioListener.onRefreshComentarios(getComentariosBD());
        } else {
            JsonArrayRequest req = new JsonArrayRequest(Request.Method.GET, mUrlAPIComentario + id, null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    comentario = ComentarioJsonParser.parserJsonComentario(response);
                    if(!comentario.isEmpty()) {
                        adicionarComentariosBD(comentario);
                    }

                    if (comentarioListener != null)
                        comentarioListener.onRefreshComentarios(getComentariosBD());
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
}
