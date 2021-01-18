package com.example.mylibraryandroid.adaptadores;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.mylibraryandroid.R;
import com.example.mylibraryandroid.modelo.Comentario;
import com.example.mylibraryandroid.modelo.Livro;
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
            convertView = inflater.inflate(R.layout.item_comentario_fragment, null);

            ComentarioAdaptador.ViewHolderLista viewHolder = (ComentarioAdaptador.ViewHolderLista) convertView.getTag();
            if(viewHolder == null){
                viewHolder = new ComentarioAdaptador.ViewHolderLista(convertView);
                convertView.setTag(viewHolder);
            }
            viewHolder.update(comentarios.get(position));
        }

        SharedPreferences sharedPreferences = context.getSharedPreferences(MenuMainActivity.PREF_INFO_USER, Context.MODE_PRIVATE);
        id = sharedPreferences.getString(MenuMainActivity.ID, "");

        return convertView;
    }

    private class ViewHolderLista {
        private TextView tvUtilizadorCom, tvComentario, tvDtaCom;
        private ImageView imgPerfil;

        public ViewHolderLista(View view) {
            tvUtilizadorCom = view.findViewById(R.id.tvUtilizadorCom);
            tvComentario = view.findViewById(R.id.tvComentario);
            tvDtaCom = view.findViewById(R.id.tvDtaCom);
            imgPerfil = view.findViewById(R.id.imgPerfil);
        }

        public void update(Comentario comentario) {
            tvUtilizadorCom.setText(comentario.getId_utilizador());
            tvComentario.setText(comentario.getComentario());
            tvDtaCom.setText(comentario.getDta_comentario());

            Glide.with(context)
                    .load(comentario.getId_utilizador())
                    .placeholder(R.drawable.logoipl)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(imgPerfil);
        }
    }

}
