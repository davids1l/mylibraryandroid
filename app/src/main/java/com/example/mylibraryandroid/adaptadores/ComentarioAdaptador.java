package com.example.mylibraryandroid.adaptadores;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.mylibraryandroid.R;
import com.example.mylibraryandroid.modelo.Comentario;
import com.example.mylibraryandroid.vistas.MenuMainActivity;

import java.util.ArrayList;

public class ComentarioAdaptador extends BaseAdapter {

    private Context context;
    private LayoutInflater inflater;
    private ArrayList<Comentario> comentarios;
    private String id;

    public ComentarioAdaptador(Context context, ArrayList<Comentario> comentarios) {
        this.context = context;
        this.comentarios = comentarios;
    }


    @Override
    public int getCount() {
        return comentarios.size();
    }

    @Override
    public Object getItem(int position) {
        return comentarios.get(position);
    }

    @Override
    public long getItemId(int position) {
        return comentarios.get(position).getId_comentario();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(inflater == null){
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        if(convertView == null){
            convertView = inflater.inflate(R.layout.item_favorito_fragment, null);

            FavoritoAdaptador.ViewHolderLista viewHolder = (FavoritoAdaptador.ViewHolderLista) convertView.getTag();
            if(viewHolder == null){
                viewHolder = new FavoritoAdaptador.ViewHolderLista(convertView);
                convertView.setTag(viewHolder);
            }
            viewHolder.update(livros.get(position));
        }

        SharedPreferences sharedPreferences = context.getSharedPreferences(MenuMainActivity.PREF_INFO_USER, Context.MODE_PRIVATE);
        id = sharedPreferences.getString(MenuMainActivity.ID, "");

        return convertView;
    }
}
