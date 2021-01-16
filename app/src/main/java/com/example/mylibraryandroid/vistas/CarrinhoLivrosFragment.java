package com.example.mylibraryandroid.vistas;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.mylibraryandroid.R;
import com.example.mylibraryandroid.adaptadores.CarrinhoAdaptador;
import com.example.mylibraryandroid.adaptadores.CatalogoAdaptador;
import com.example.mylibraryandroid.listeners.CarrinhoListener;
import com.example.mylibraryandroid.modelo.Livro;
import com.example.mylibraryandroid.modelo.Singleton;

import java.util.ArrayList;

public class CarrinhoLivrosFragment extends Fragment implements CarrinhoListener, SwipeRefreshLayout.OnRefreshListener {

    private ListView lvCarrinhoLivros;
    private ArrayList<Livro> livrosCarrinho;
    private SwipeRefreshLayout swipeRefreshLayout;

    public CarrinhoLivrosFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);

        View view = inflater.inflate(R.layout.carrinho_livros_fragment, container, false);
        lvCarrinhoLivros = view.findViewById(R.id.lvCarrinhoLivros);

        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(this);

        Singleton.getInstance(getContext()).setCarrinhoListener(this);
        Singleton.getInstance(getContext()).getLivrosCarrinho();

        return view;
    }

    @Override
    public void onRefresh() {
        Singleton.getInstance(getContext()).getLivrosCarrinho();
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onRefreshCarrinhoLivros(ArrayList<Livro> carrinho) {
        if(carrinho != null)
            lvCarrinhoLivros.setAdapter(new CarrinhoAdaptador(getContext(), carrinho));
    }
}