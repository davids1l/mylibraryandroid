package com.example.mylibraryandroid.vistas;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.example.mylibraryandroid.R;
import com.example.mylibraryandroid.adaptadores.CatalogoAdaptador;
import com.example.mylibraryandroid.adaptadores.ComentarioAdaptador;
import com.example.mylibraryandroid.adaptadores.FavoritoAdaptador;
import com.example.mylibraryandroid.listeners.ComentarioListener;
import com.example.mylibraryandroid.modelo.Comentario;
import com.example.mylibraryandroid.modelo.Livro;
import com.example.mylibraryandroid.modelo.Singleton;

import java.util.ArrayList;

public class ComentarioLivrosFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, ComentarioListener {

    private ListView lvComentarios;
    private ArrayList<Comentario> comentarios;
    private SwipeRefreshLayout swipeRefreshLayout;
    //private String id;
    private String token;
    private int indexID_LIVRO;

    public ComentarioLivrosFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);

        SharedPreferences sharedPreferences = getContext().getSharedPreferences(MenuMainActivity.PREF_INFO_USER, Context.MODE_PRIVATE);
        //id = sharedPreferences.getString(MenuMainActivity.ID, "");
        token = sharedPreferences.getString(MenuMainActivity.TOKEN, "");

        View view = inflater.inflate(R.layout.comentario_livros_fragment, container, false);
        lvComentarios = view.findViewById(R.id.lvComentarios);

        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(this);

        Singleton.getInstance(getContext()).setComentarioListener(this);
        Singleton.getInstance(getContext()).getComentarioAPI(getContext(), token);

        Bundle args = getArguments();
        indexID_LIVRO = args.getInt("index", 0);

        comentarios = Singleton.getInstance(getContext()).getComentariosLivro(indexID_LIVRO);

        if(comentarios.isEmpty()) {
            Toast.makeText(getContext(), R.string.semComentarios, Toast.LENGTH_SHORT).show();
        }

        return view;
    }

    @Override
    public void onRefresh() {
        Singleton.getInstance(getContext()).getComentarioAPI(getContext(), token);
        swipeRefreshLayout.setRefreshing(false);
        if(comentarios.isEmpty()) {
            Toast.makeText(getContext(), R.string.semComentarios, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRefreshComentarios(ArrayList<Comentario> comentarios) {
        ArrayList<Comentario> comentariosLivro = new ArrayList<>();
        if(comentarios != null) {
            comentariosLivro = Singleton.getInstance(getContext()).getComentariosLivro(indexID_LIVRO);
            lvComentarios.setAdapter(new ComentarioAdaptador(getContext(), comentariosLivro));
        }
    }
}