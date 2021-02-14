package com.example.mylibraryandroid.adaptadores;

import android.content.ContentValues;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.mylibraryandroid.R;
import com.example.mylibraryandroid.modelo.Livro;
import com.example.mylibraryandroid.modelo.Singleton;

import java.util.ArrayList;

public class CatalogoAdaptador extends BaseAdapter {

    private Context context;
    private LayoutInflater inflater;
    private ArrayList<Livro> catalogo;
    private String nomeAutor;
    private static final String IP = "http://192.168.8.103";
    private String urlCapas = IP + ":8888/backend/web/imgs/capas/";

    public CatalogoAdaptador(Context context, ArrayList<Livro> catalogo){
        this.context = context;
        this.catalogo = catalogo;
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
        ViewHolderLista viewHolder = null;

        if(inflater == null){
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_catalogo_fragment, null);

            viewHolder = (ViewHolderLista) convertView.getTag();

            if(viewHolder == null){
                viewHolder = new ViewHolderLista(convertView);
                convertView.setTag(viewHolder);
            }

        } else {
            viewHolder = (ViewHolderLista) convertView.getTag();
        }

        viewHolder.update(catalogo.get(position));
        return convertView;
    }

    private class ViewHolderLista {
        private TextView tvTitulo, tvAutor,tvIdioma, tvFormato;
        private ImageView imgCapa;

        public ViewHolderLista(View view){
            tvTitulo = view.findViewById(R.id.tvTitulo);
            tvAutor = view.findViewById(R.id.tvAutor);
            tvIdioma = view.findViewById(R.id.tvIdioma);
            tvFormato = view.findViewById(R.id.tvFormato);
            imgCapa = view.findViewById(R.id.imageCapa);
        }

        public void update(Livro livro){
            nomeAutor = Singleton.getInstance(context).getNomeAutor(livro.getId_autor());

            tvTitulo.setText(livro.getTitulo());
            tvAutor.setText(nomeAutor);
            tvIdioma.setText(livro.getIdioma());
            tvFormato.setText(livro.getFormato());

            Glide.with(context)
                    .load(urlCapas+livro.getCapa())
                    .placeholder(R.drawable.loading_capa)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(imgCapa);
        }
    }


}
