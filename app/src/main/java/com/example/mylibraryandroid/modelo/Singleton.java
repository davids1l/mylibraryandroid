package com.example.mylibraryandroid.modelo;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;


import com.android.volley.AuthFailureError;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.mylibraryandroid.R;
import com.example.mylibraryandroid.listeners.AutorListener;
import com.example.mylibraryandroid.listeners.BibliotecaListener;
import com.example.mylibraryandroid.listeners.CarrinhoListener;
import com.example.mylibraryandroid.listeners.CatalogoListener;
import com.example.mylibraryandroid.listeners.ComentarioListener;
import com.example.mylibraryandroid.listeners.EditarPerfilListener;
import com.example.mylibraryandroid.listeners.EditoraListener;
import com.example.mylibraryandroid.listeners.FavoritoListener;
import com.example.mylibraryandroid.listeners.LivrosDetalhesReqListener;
import com.example.mylibraryandroid.listeners.LoginListener;
import com.example.mylibraryandroid.listeners.PerfilListener;
import com.example.mylibraryandroid.listeners.RegistarListener;
import com.example.mylibraryandroid.listeners.RequisicaoListener;
import com.example.mylibraryandroid.listeners.RequisicaoLivroListener;
import com.example.mylibraryandroid.utils.AutorJsonParser;
import com.example.mylibraryandroid.utils.BibliotecaJsonParser;
import com.example.mylibraryandroid.utils.EditoraJsonParser;
import com.example.mylibraryandroid.utils.ComentarioJsonParser;
import com.example.mylibraryandroid.utils.FavoritoJsonParser;
import com.example.mylibraryandroid.utils.JsonParser;
import com.example.mylibraryandroid.utils.LivroJsonParser;
import com.example.mylibraryandroid.utils.RequisicaoJsonParser;
import com.example.mylibraryandroid.utils.RequisicoesLivrosJsonParser;
import com.example.mylibraryandroid.vistas.CarrinhoLivrosFragment;
import com.example.mylibraryandroid.vistas.DetalhesLivroActivity;
import com.example.mylibraryandroid.vistas.MenuMainActivity;
import com.example.mylibraryandroid.vistas.RequisicoesFragment;

import org.json.JSONArray;

import java.lang.reflect.Array;
import java.lang.reflect.GenericArrayType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Singleton {

    public static final String IP = "http://192.168.1.100";
    private static Singleton instance = null;
    private static RequestQueue volleyQueue = null;
    private static final String mUrlAPILogin = IP + ":8888/backend/web/api/utilizador/login";
    private static final String mUrlAPIRegistar = IP + ":8888/backend/web/api/utilizador/create";
    private static final String mUrlAPICatalogo = IP + ":8888/backend/web/api/livro";
    private static final String mUrlAPIFavorito = IP + ":8888/backend/web/api/favorito/utilizador/";
    private static final String mUrlAPILeitor = IP + ":8888/backend/web/api/utilizador/dadosLeitor/";
    private static final String mUrlAPIEditarLeitor = IP + ":8888/backend/web/api/utilizador/";
    private static final String mUrlAPIBiblioteca = IP + ":8888/backend/web/api/biblioteca";
    private static final String mUrlAPIVerificarRequisicao = IP + ":8888/backend/web/api/requisicao/emrequisicao/";
    private static final String mUrlAPIRequisicao = IP + ":8888/backend/web/api/requisicao/create";
    private static final String mUrlAPITotalReq = IP + ":8888/backend/web/api/requisicao/total/";
    private static final String mUrlAPIRequisicoesUtilizador = IP + ":8888/backend/web/api/requisicao/utilizador/";
    private static final String mUrlAPIRequisicaoTotalLivros = IP + ":8888/backend/web/api/requisicao/livrosreq/";
    private static final String mUrlAPILivrosRequisicao = IP + ":8888/backend/web/api/requisicao/livros/";
    private static final String mUrlAPIDeleteRequisicao = IP + ":8888/backend/web/api/requisicao/";
    private static final String mUrlAPIAutores = IP + ":8888/backend/web/api/autor";
    private static final String mUrlAPIEditora = IP + ":8888/backend/web/api/editora";
    private static final String mUrlAPILeitorEmail = IP + ":8888/backend/web/api/user/";
    private static final String mUrlAPIRemoverFavorito = IP + ":8888/backend/web/api/favorito/";
    private static final String mUrlAPIAdicionarFavorito = IP + ":8888/backend/web/api/favorito";
    private static final String mUrlAPIComentario = IP + ":8888/backend/web/api/favorito/utilizador/";
    private LoginListener loginListener;
    private RegistarListener registarListener;
    private CatalogoListener catalogoListener;
    private FavoritoListener favoritoListener;
    private AutorListener autorListener;
    private BibliotecaListener bibliotecaListener;
    private ComentarioListener comentarioListener;
    private EditoraListener editoraListener;
    private PerfilListener perfilListener;
    private EditarPerfilListener editarPerfilListener;
    private RequisicaoListener requisicaoListener;
    private RequisicaoLivroListener requisicaoLivroListener;
    private CarrinhoListener carrinhoListener;
    private LivrosDetalhesReqListener livrosDetalhesReqListener;
    private BDHelper bdHelper;
    private ArrayList<Livro> catalogo;
    private ArrayList<Favorito> favorito;
    private ArrayList<Integer> carrinho;
    private ArrayList<Biblioteca> bibliotecas;
    private ArrayList<Autor> autores;
    private ArrayList<Editora> editoras;
    private ArrayList<Requisicao> requisicoes;
    private ArrayList<RequisicaoLivro> livrosRequisicao;
    private Integer totalReq;
    public String totalLivrosReq;
    private ArrayList<Comentario> comentario;
    private String emailUtilizador;
    private ArrayList<Livro> livrosDetalhesReq;

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

    public void setAutorListener(AutorListener autorListener) {
        this.autorListener = autorListener;
    }

    public void setFavoritoListener(FavoritoListener favoritoListener) {
        this.favoritoListener = favoritoListener;
    }

    public void setPerfilListener(PerfilListener perfilListener) {
        this.perfilListener = perfilListener;
    }

    public void setEditarPerfilListener(EditarPerfilListener editarPerfilListener) {
        this.editarPerfilListener = editarPerfilListener;
    }

    public void setRequisicaoListener(RequisicaoListener requisicaoListener) {
        this.requisicaoListener = requisicaoListener;
    }

    public void setRequisicaoLivroListener(RequisicaoLivroListener requisicaoLivroListener){
        this.requisicaoLivroListener = requisicaoLivroListener;
    }

    public void setLivrosDetalhesReqListener(LivrosDetalhesReqListener livrosDetalhesReqListener){
        this.livrosDetalhesReqListener = livrosDetalhesReqListener;
    }


    public void setComentarioListener(ComentarioListener comentarioListener) {
        this.comentarioListener = comentarioListener;
    }

    public void setCarrinhoListener(CarrinhoListener carrinhoListener){
        this.carrinhoListener = carrinhoListener;
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

    public void adicionarFavoritosBD(ArrayList<Favorito> favoritos) {
        bdHelper.removerAllFavoritoBD();
        for (Favorito f : favoritos) {
            adicionarFavoritoBD(f);
        }
    }

    public Favorito getFavorito(int id_favorito) {
        for (Favorito f : favorito) {
            if (f.getId_favorito() == id_favorito) {
                return f;
            }
        }
        return null;
    }

    public ArrayList<Livro> getLivrosFavoritosBD() {
        ArrayList<Livro> livrosFav = new ArrayList<>();
        for (Favorito f : favorito) {
            livrosFav.add(getLivro(f.getId_livro()));
        }
        return livrosFav;
    }

    /** Acesso aos livros pela API **/
    public void removerFavoritoBD(int id) {
        Favorito f = getFavorito(id);

        if (f != null) {
            bdHelper.removerFavoritoBD(id);
        }
    }

    public String findFavoritoByIDS(int id_livro, int id_utilizador) {
        for (Favorito f : favorito) {
            if (f.getId_livro() == id_livro && f.getId_utilizador() == id_utilizador) {
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
        for (Comentario c : comentarios) {
            adicionarComentarioBD(c);
        }
    }

    public Comentario getComentario(int id_comentario) {
        for (Comentario c : comentario) {
            if (c.getId_comentario() == id_comentario) {
                return c;
            }
        }
        return null;
    }

    public void removerComentarioBD(int id) {
        Comentario c = getComentario(id);

        if (c != null) {
            bdHelper.removerComentarioBD(id);
        }
    }

    public String findComentariosByIDS(int id_livro, int id_utilizador) {
        for (Comentario c : comentario) {
            if (c.getId_livro() == id_livro && c.getId_utilizador() == id_utilizador) {
                return c.getId_comentario() + "";
            }
        }
        return null;
    }


    public void getCatalogoAPI(final Context context, final String token) {
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
            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("authorization", token);
                    return params;
                }
            };
            volleyQueue.add(req);
        }
    }

    /**
     * Acesso aos favoritos pela API
     **/
    public void getFavoritoAPI(final Context context, final String id, final String token) {
        if (!FavoritoJsonParser.isConnectionInternet(context)) {
            Toast.makeText(context, R.string.noInternet, Toast.LENGTH_LONG).show();

            if (favoritoListener != null)
                favoritoListener.onRefreshFavoritoLivros(getLivrosFavoritosBD());
        } else {
            JsonArrayRequest req = new JsonArrayRequest(Request.Method.GET, mUrlAPIFavorito + id, null, new Response.Listener<JSONArray>() {

                @Override
                public void onResponse(JSONArray response) {
                    favorito = FavoritoJsonParser.parserJsonFavorito(response);
                    if (!favorito.isEmpty()) {
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
            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("authorization", token);
                    return params;
                }
            };
            volleyQueue.add(req);
        }
    }

    public void adicionarFavoritoAPI(final Context context, final int id_utilizador, final int id_livro, final String token) {
        StringRequest req = new StringRequest(Request.Method.POST, mUrlAPIAdicionarFavorito, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Favorito f = FavoritoJsonParser.parserJsonFav(response);
                getFavoritoAPI(context, id_utilizador + "", token);

                if (favoritoListener != null) {
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
                params.put("id_livro", id_livro + "");
                params.put("id_utilizador", id_utilizador + "");
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("authorization", token);
                return params;
            }
        };
        volleyQueue.add(req);
    }

    public void removerFavoritoAPI(final Context context, final int id_utilizador, int id_livro, final String token) {
        final String id_favorito = findFavoritoByIDS(id_livro, id_utilizador);
        StringRequest req = new StringRequest(Request.Method.DELETE, mUrlAPIRemoverFavorito + id_favorito, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Favorito f = FavoritoJsonParser.parserJsonFav(response);
                int id = Integer.parseInt(id_favorito);
                removerFavoritoBD(id);
                getFavoritoAPI(context, id_utilizador + "", token);

                if (favoritoListener != null) {
                    favoritoListener.onRefreshDetalhes();
                    //favoritoListener.onRefreshFavoritoLivros(getLivrosFavoritosBD());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("authorization", token);
                return params;
            }
        };
        volleyQueue.add(req);
    }

    /**
     * Acesso aos dados leitor através da API
     **/
    public void getDadosLeitorAPI(final Context context, final String id, final String token) {
        if (!JsonParser.isConnectionInternet(context)) {
            Toast.makeText(context, R.string.noInternet, Toast.LENGTH_LONG).show();

            /*if (perfilListener != null){
                perfilListener.onRefreshUtilizador(bdHelper.getUtilizadorBD());
            }*/
        } else {
            StringRequest req = new StringRequest(Request.Method.GET, mUrlAPILeitor + id, new Response.Listener<String>() {

                @Override
                public void onResponse(String response) {
                    Utilizador utilizador = JsonParser.parserJsonPerfil(response);

                    adicionarDadosLeitorBD(context, utilizador);

                    if (perfilListener != null) {
                        perfilListener.onRefreshUtilizador(utilizador);
                    }
                }

            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("authorization", token);

                    return params;
                }
            };
            volleyQueue.add(req);
        }
    }


    public void adicionarDadoLeitorBD(Utilizador utilizador) {
        bdHelper.adicionarLeitorBD(utilizador);
    }

    public void adicionarDadosLeitorBD(Context context, Utilizador utilizador) {
        bdHelper.removerLeitorBD();
        adicionarDadoLeitorBD(utilizador);
    }

    /*public Boolean adicionarCarrinho (int id_livro){

        //verifica se o arrayList já contem o id_livro a inserir
        //limita tambem o carrinho a apenas 5 livros
        if (carrinho.size() <= 4) {
            if (!carrinho.contains(id_livro)) {
                carrinho.add(id_livro);
                return true;
            }
        }

        return false;
    }*/

    public ArrayList<Livro> getLivrosCarrinho() {
        ArrayList<Livro> livrosCarrinho = new ArrayList<>();

        for (int i = 0; i < carrinho.size(); i++) {
            livrosCarrinho.add(getLivro(carrinho.get(i)));
        }

        return livrosCarrinho;
    }

    public Boolean removerCarrinho(int id_livro) {
        for (int i = 0; i < carrinho.size(); i++) {
            if (carrinho.get(i) == id_livro) {
                carrinho.remove(i);
                return true;
            }
        }
        return false;
    }

    public Boolean removerAllCarrinho() {
        if (carrinho.removeAll(carrinho))
            return true;

        return false;
    }

    //Método REST CUSTOM que verifica se o livro está em requisição, se não estiver adiciona ao carrinho
    public void verificarEmRequisicao(final Context context, final int id) {
        StringRequest req = new StringRequest(Request.Method.GET, mUrlAPIVerificarRequisicao + id, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //response = false -> livro não está em requisição
                if (response.equals("false")) {
                    if ((totalReq + carrinho.size()) <= 4) {
                        //verifica se o arrayList já contem o id_livro a inserir
                        //limita tambem o carrinho a apenas 5 livros
                        if (!carrinho.contains(id) && carrinho.size() <= 4) {
                            carrinho.add(id);
                            Toast.makeText(context, "Livro adicionado ao carrinho!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(context, "O livro já se encontra no seu carrinho", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(context, "Atingiu o máximo de 5 livros em requisição", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(context, "Este livro já se encontra em requisição, tente mais tarde", Toast.LENGTH_SHORT).show();
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

    public void getBibliotecasAPI(final Context context) {
        if (!LivroJsonParser.isConnectionInternet(context)) {
            Toast.makeText(context, R.string.noInternet, Toast.LENGTH_LONG).show();
        } else {
            JsonArrayRequest req = new JsonArrayRequest(Request.Method.GET, mUrlAPIBiblioteca, null, new Response.Listener<JSONArray>() {

                @Override
                public void onResponse(JSONArray response) {
                    bibliotecas = BibliotecaJsonParser.parserJsonBibliotecas(response);

                    adicionarBibliotecasBD(bibliotecas);

                    if (bibliotecaListener != null) {
                        bibliotecaListener.onRefreshBibliotecas(bibliotecas);
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
    }

    public void totalEmRequisicao(final Context context, final int id){
        StringRequest req = new StringRequest(Request.Method.GET, mUrlAPITotalReq + id, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                totalReq = Integer.parseInt(response) ;
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        volleyQueue.add(req);
    }

    public void adicionarBibliotecaBD(Biblioteca biblioteca){
        bdHelper.adicionarBibliotecaBD(biblioteca);
    }

    public void adicionarBibliotecasBD(ArrayList<Biblioteca> bibliotecas){
        bdHelper.removerAllBibliotecasDB();
        for (Biblioteca b:bibliotecas)
            adicionarBibliotecaBD(b);
    }

    public ArrayList<Biblioteca> getAllBibliotecasBD() {
        return bdHelper.getAllBibliotecasBD();
    }

    public String getNomeBiblioteca(int id_biblioteca) {
        ArrayList<Biblioteca> auxBib = getAllBibliotecasBD();

        for (Biblioteca b : auxBib) {
            if (b.getId_biblioteca() == id_biblioteca) {
                return b.getNome();
            }
        }
        return null;
    }

    public ArrayList<Biblioteca> getBibliotecas() {
        ArrayList<Biblioteca> listaBibliotecas = new ArrayList<>();

        for (int i = 0; i < bibliotecas.size(); i++) {
            listaBibliotecas.add(bibliotecas.get(i));
        }

        return listaBibliotecas;
    }


    public void adicionarAutorBD(Autor autor) {
        bdHelper.adicionarAutorDB(autor);
    }

    public void adicionarAutoresBD(ArrayList<Autor> autores) {
        bdHelper.removerAllAutoresDB();
        for (Autor a : autores)
            adicionarAutorBD(a);
    }

    //
    public void getAutoresAPI(final Context context) {
        if (!LivroJsonParser.isConnectionInternet(context)) {
            Toast.makeText(context, R.string.noInternet, Toast.LENGTH_LONG).show();
        } else {
            JsonArrayRequest req = new JsonArrayRequest(Request.Method.GET, mUrlAPIAutores, null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    autores = AutorJsonParser.parserJsonAutores(response);
                    adicionarAutoresBD(autores);

                    if (autorListener != null)
                        autorListener.onRefreshAutores(autores);
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

    public ArrayList<Autor> getAllAutoresBD() {
        autores = bdHelper.getAllAutoresDB();
        return autores;
    }

    public String getNomeAutor(int id_autor) {
        ArrayList<Autor> auxAutor = getAllAutoresBD();

        for (Autor a : auxAutor) {
            if (a.id_autor == id_autor) {
                return a.getNome_autor();
            }
        }

        return null;
    }


    public void adicionarEditoraBD(Editora editora) {
        bdHelper.adicionarEditoraDB(editora);
    }

    public void adicionarEditorasBD(ArrayList<Editora> editoras) {
        bdHelper.removerAllEditorasDB();
        for (Editora e : editoras)
            adicionarEditoraBD(e);
    }

    public void getEditorasAPI(final Context context) {
        if (!LivroJsonParser.isConnectionInternet(context)) {
            Toast.makeText(context, R.string.noInternet, Toast.LENGTH_LONG).show();
        } else {
            JsonArrayRequest req = new JsonArrayRequest(Request.Method.GET, mUrlAPIEditora, null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    editoras = EditoraJsonParser.parserJsonEditora(response);
                    adicionarEditorasBD(editoras);

                    if (editoraListener != null)
                        editoraListener.onRefreshEditoras(editoras);
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

    public ArrayList<Editora> getAllEditorasBD() {
        return bdHelper.getAllEditorasDB();
    }

    public String getDesignacaoEditora(int id_editora) {
        ArrayList<Editora> auxEditora = getAllEditorasBD();

        for (Editora e : auxEditora) {
            if (e.getId_editora() == id_editora) {
                return e.getDesignacao();
            }
        }

        return null;
    }

    public void atualizarDadosLeitorAPI(final Context context, final String nome, final String apelido, final String telemovel, final String dia, final String mes, final String ano, final String nif, final String id, final String token) {
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
                params.put("dta_nascimento", ano + "-" + mes + "-" + dia);
                params.put("nif", nif);
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("authorization", token);

                return params;
            }
        };
        volleyQueue.add(req);
    }


    public void adicionarRequisicaoAPI(final Context context, final int id_biblioteca, final int id_utilizador) {
        StringRequest req = new StringRequest(Request.Method.POST, mUrlAPIRequisicao, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                removerAllCarrinho();

                if(carrinhoListener != null)
                    carrinhoListener.onRefreshCarrinhoLivros(getLivrosCarrinho());
                //}

                //carrinhoListener.onRefreshDetalhes();

                Toast.makeText(context, "Requisição efetuada com sucesso!", Toast.LENGTH_LONG).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
                Toast.makeText(context, "Ocorreu um erro, tente novamente!", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("id_biblioteca", id_biblioteca + "");
                params.put("id_utilizador", id_utilizador + "");
                params.put("carrinho_size", carrinho.size() + "");


                //params.put("token", token);

                for (int i = 0; i < carrinho.size(); i++) {
                    params.put("id_livro" + i, carrinho.get(i) + "");
                }
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
                    if (!comentario.isEmpty()) {
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

    public void adicionarRequisicaoBD(Requisicao requisicao){
        bdHelper.adicionarRequisicaoBD(requisicao);
    }

    public void adicionarRequisicoesBD(ArrayList<Requisicao> requisicoes) {
        bdHelper.removerAllRequisicoesBD();

        for (Requisicao req : requisicoes) {
            adicionarRequisicaoBD(req);
        }
    }

    public ArrayList<Requisicao> getRequisicoesBD() {
        return bdHelper.getAllRequisicoesBD();
    }

    public Requisicao getRequisicao(int id_requisicao){
        ArrayList<Requisicao> requisicoes = this.getRequisicoesBD();

        for (Requisicao r : requisicoes){
            if (r.getId_requisicao() == id_requisicao){
                return r;
            }
        }
        return null;
    }

    /**
     *
     * Acesso às requisições pela API
     *
     * @param context
     * @param id
     */
    public void getRequisicoesAPI(final Context context, final String token, final String id) {
        if(!RequisicaoJsonParser.isConnectionInternet(context)) {
            Toast.makeText(context, R.string.noInternet, Toast.LENGTH_SHORT).show();

            if (requisicaoListener != null){
                requisicaoListener.onRefreshRequisicao(bdHelper.getAllRequisicoesBD());
            }
        } else {
            JsonArrayRequest req = new JsonArrayRequest(Request.Method.GET, mUrlAPIRequisicoesUtilizador + id, null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    requisicoes = RequisicaoJsonParser.parserJsonRequisicoes(response);

                    adicionarRequisicoesBD(requisicoes);

                    if(requisicaoListener != null)
                        requisicaoListener.onRefreshRequisicao(getRequisicoesBD());

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(context, error.getMessage(), Toast.LENGTH_LONG).show();
                }
            }){
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("authorization", token);
                    return params;
                }
            };

            volleyQueue.add(req);
        }
    }

    public void adicionarLivroRequisicaoBD(RequisicaoLivro requisicaoLivro){
        bdHelper.adicionarRequisicaoLivroBD(requisicaoLivro);
    }

    public void adicionarLivrosRequisicaoBD(ArrayList<RequisicaoLivro> requisicaoLivros){
        bdHelper.removerAllRequisicoesLivroBD();

        for (RequisicaoLivro reqL : requisicaoLivros) {
            adicionarLivroRequisicaoBD(reqL);
        }
    }

    public void getRequisicaoLivrosAPI(final Context context, final String token, final  String id){
        if (!JsonParser.isConnectionInternet(context)){
            Toast.makeText(context, R.string.noInternet, Toast.LENGTH_SHORT).show();

            if (requisicaoLivroListener != null)
                requisicaoLivroListener.onRefreshRequisicaoLivro(bdHelper.getAllRequisicoesLivroBD());
        } else {
            JsonArrayRequest req = new JsonArrayRequest(Request.Method.GET, mUrlAPILivrosRequisicao + id, null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    livrosRequisicao = RequisicoesLivrosJsonParser.parserJsonRequisicoesLivros(response);

                    adicionarLivrosRequisicaoBD(livrosRequisicao);

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });/*{
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("authorization", token);

                    return params;
                }
            };*/

            volleyQueue.add(req);
        }
    }


    public void obterLivrosRequisicao(int id_requisicao){
        ArrayList<Livro> livrosObjectRequisicao = new ArrayList<>();

        livrosDetalhesReq = null;

        if(livrosRequisicao != null){
            for (int i = 0; i < livrosRequisicao.size(); i++){
                if (livrosRequisicao.get(i).getId_requisicao() == id_requisicao){
                    Livro aux = getLivro(livrosRequisicao.get(i).getId_livro());
                    livrosObjectRequisicao.add(aux);
                }
            }
        }

        livrosDetalhesReq = livrosObjectRequisicao;
    }

    public ArrayList<Livro> getLivrosDetalhesReq() {
        return livrosDetalhesReq;
    }

    public int getTotalLivrosPorReq(int id_requisicao) {

        Integer totalLivros = 0;

        if(livrosRequisicao != null){
            for (RequisicaoLivro r : livrosRequisicao) {
                if (r.getId_requisicao() == id_requisicao){
                    totalLivros += 1;
                }
            }
        }

        return totalLivros;
    }

    public void cancelarRequisicaoAPI(final Context context, final String token, final int id){
        if (!JsonParser.isConnectionInternet(context)){
            Toast.makeText(context, R.string.noInternet, Toast.LENGTH_SHORT).show();
        } else {
            StringRequest req = new StringRequest(Request.Method.DELETE, mUrlAPIDeleteRequisicao + id, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    SharedPreferences sharedPreferences = context.getSharedPreferences(MenuMainActivity.PREF_INFO_USER, Context.MODE_PRIVATE);
                    String id_utilizador = sharedPreferences.getString(MenuMainActivity.ID, "");

                    getRequisicoesAPI(context, token, id_utilizador);

                    if (requisicaoListener != null){
                        requisicaoListener.onRefreshRequisicoes();
                    }

                    Toast.makeText(context, "A requisição foi cancelada com sucesso", Toast.LENGTH_SHORT).show();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    //Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
                    Toast.makeText(context, "Ocorreu um erro, tente novamente!", Toast.LENGTH_SHORT).show();
                }
            }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("authorization", token);

                    return params;
                }
            };

            volleyQueue.add(req);
        }

    }

    public void totalLivrosReq(final Context context, final int id){
        StringRequest req = new StringRequest(Request.Method.GET, mUrlAPIRequisicaoTotalLivros + id, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                totalLivrosReq = response;
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
