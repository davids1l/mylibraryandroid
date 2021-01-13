package com.example.mylibraryandroid.adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.mylibraryandroid.modelo.Livro;

import java.util.ArrayList;

public class CatalogoAdaptador extends BaseAdapter {

    private Context context;
    private LayoutInflater inflater;
    private ArrayList<Livro> catalogo;

    public CatalogoAdaptador(Context context, ArrayList<Livro> livros){
        this.context = context;
        this.catalogo = livros;
    }

    @Override
    public int getCount() {
        return catalogo.size();
    }

    @Override
    public Object getItem(int position) {
        return catalogo.get(position);
    }

    @Override
    public long getItemId(int position) {
        return catalogo.get(position).getId_livro();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
}
