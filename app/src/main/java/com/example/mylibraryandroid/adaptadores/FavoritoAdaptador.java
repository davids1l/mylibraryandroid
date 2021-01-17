package com.example.mylibraryandroid.adaptadores;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.mylibraryandroid.R;
import com.example.mylibraryandroid.modelo.Livro;
import com.example.mylibraryandroid.modelo.Singleton;
import com.example.mylibraryandroid.vistas.MenuMainActivity;

import java.util.ArrayList;

public class FavoritoAdaptador extends BaseAdapter {

    private Context context;
    private LayoutInflater inflater;
    private ArrayList<Livro> livros;
    private String id;

    public FavoritoAdaptador(Context context, ArrayList<Livro> livros) {
        this.context = context;
        this.livros = livros;
    }

    @Override
    public int getCount() {
        return livros.size();
    }

    @Override
    public Object getItem(int position) {
        return livros.get(position);
    }

    @Override
    public long getItemId(int position) {
        return livros.get(position).getId_livro();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if(inflater == null){
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        if(convertView == null){
            convertView = inflater.inflate(R.layout.item_favorito_fragment, null);

            ViewHolderLista viewHolder = (ViewHolderLista) convertView.getTag();
            if(viewHolder == null){
                viewHolder = new ViewHolderLista(convertView);
                convertView.setTag(viewHolder);
            }
            viewHolder.update(livros.get(position));
        }

        SharedPreferences sharedPreferences = context.getSharedPreferences(MenuMainActivity.PREF_INFO_USER, Context.MODE_PRIVATE);
        id = sharedPreferences.getString(MenuMainActivity.ID, "");

        Button remove = (Button) convertView.findViewById(R.id.remFavorite);
        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id_utilizador = Integer.parseInt(id);
                Livro itemPos = livros.get(position);
                Singleton.getInstance(context).removerFavoritoAPI(context, id_utilizador, itemPos.getId_livro());
                Toast.makeText(context,"Livro removido dos favoritos!", Toast.LENGTH_SHORT).show();
            }
        });

        return convertView;
    }

    private class ViewHolderLista {
        private TextView tvTitulo, tvAutor, tvIdioma, tvFormato;
        private ImageView imageCapa;

        public ViewHolderLista(View view) {
            tvTitulo = view.findViewById(R.id.tvTitulo);
            tvAutor = view.findViewById(R.id.tvAutor);
            tvIdioma = view.findViewById(R.id.tvIdioma);
            tvFormato = view.findViewById(R.id.tvFormato);
            imageCapa = view.findViewById(R.id.imageCapa);
        }

        public void update(Livro livro) {
            tvTitulo.setText(livro.getTitulo());
            tvAutor.setText(livro.getId_autor()+"");
            tvIdioma.setText(livro.getIdioma());
            tvFormato.setText(livro.getFormato());

            Glide.with(context)
                    .load(livro.getCapa())
                    .placeholder(R.drawable.logoipl)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(imageCapa);
        }
    }
}
