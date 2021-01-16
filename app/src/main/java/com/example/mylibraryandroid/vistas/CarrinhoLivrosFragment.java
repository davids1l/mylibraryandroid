package com.example.mylibraryandroid.vistas;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.mylibraryandroid.R;
import com.example.mylibraryandroid.adaptadores.CarrinhoAdaptador;
import com.example.mylibraryandroid.adaptadores.CatalogoAdaptador;
import com.example.mylibraryandroid.listeners.CarrinhoListener;
import com.example.mylibraryandroid.modelo.Livro;
import com.example.mylibraryandroid.modelo.Singleton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

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

        final View view = inflater.inflate(R.layout.carrinho_livros_fragment, container, false);
        lvCarrinhoLivros = view.findViewById(R.id.lvCarrinhoLivros);

        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(this);

        Singleton.getInstance(getContext()).setCarrinhoListener(this);
        livrosCarrinho =  Singleton.getInstance(getContext()).getLivrosCarrinho();

        lvCarrinhoLivros.setAdapter(new CatalogoAdaptador(getContext(), livrosCarrinho));

        FloatingActionButton fab =  view.findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext());
                View viewDialog = getLayoutInflater().inflate(R.layout.dialog_finalizar_requisicao, null);
                Spinner spinner = (Spinner) viewDialog.findViewById(R.id.spinnerBibliotecas);
                Button button = (Button) viewDialog.findViewById(R.id.btnFinalizar);
                Toast.makeText(getContext(), "Clicou no FAB", Toast.LENGTH_LONG).show();
                dialogBuilder.setView(viewDialog);
                AlertDialog dialog = dialogBuilder.create();
                dialog.show();
            }
        });

        if (livrosCarrinho.isEmpty()){
            fab.setClickable(false);
            fab.setEnabled(false);
            Toast.makeText(getContext(), "O carrinho está vazio", Toast.LENGTH_LONG).show();
        }



        //TODO: Fazer o onItemClick para mostrar os detalhes do livro



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