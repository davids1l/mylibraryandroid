package com.example.mylibraryandroid.adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.mylibraryandroid.R;
import com.example.mylibraryandroid.modelo.Livro;
import com.example.mylibraryandroid.modelo.RequisicaoLivro;
import com.example.mylibraryandroid.modelo.Singleton;

import java.util.ArrayList;

public class RequisicoesLivrosAdaptador extends BaseAdapter {

    private Context context;
    private LayoutInflater inflater;
    private ArrayList<Livro> requisicaoLivros;

    public RequisicoesLivrosAdaptador(Context context, ArrayList<Livro> requisicaoLivros){
        this.context = context;
        this.requisicaoLivros = requisicaoLivros;
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (inflater == null){
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        if (convertView == null){
            convertView = inflater.inflate(R.layout.item_requisicao_detalhes_fragment, null);

           ViewHolderLista viewHolder = (ViewHolderLista) convertView.getTag();

           if (viewHolder == null){
               viewHolder = new ViewHolderLista(convertView);
               convertView.setTag(viewHolder);
           }

           viewHolder.update(requisicaoLivros.get(position));
        }
        return convertView;
    }

    private class ViewHolderLista {
        private TextView tvTitulo, tvAutor;
        private ImageView imgCapaDetalhesReq;

        public ViewHolderLista(View view) {
            tvTitulo = view.findViewById(R.id.tvTitulo);
            tvAutor = view.findViewById(R.id.tvAutor);
            imgCapaDetalhesReq = view.findViewById(R.id.imgCapaDetalhesReq);
        }

        public void update(Livro requisicaoLivro) {
            String nomeAutor = Singleton.getInstance(context).getNomeAutor(requisicaoLivro.getId_autor());

            tvTitulo.setText(requisicaoLivro.getTitulo());
            tvAutor.setText(nomeAutor);

            Glide.with(context)
                    .load(requisicaoLivro.getCapa())
                    .placeholder(R.drawable.loading_capa)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(imgCapaDetalhesReq);
        }
    }
}
