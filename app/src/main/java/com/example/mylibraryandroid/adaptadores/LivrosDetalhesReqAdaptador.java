package com.example.mylibraryandroid.adaptadores;

import android.content.Context;
import android.text.Layout;
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
import com.example.mylibraryandroid.modelo.Singleton;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class LivrosDetalhesReqAdaptador extends BaseAdapter {

    private Context context;
    private ArrayList<Livro> livrosDetalhesReq;
    private LayoutInflater inflater;
    private String nomeAutor;
    private static final String IP = "http://192.168.8.103";
    private String urlCapas = IP + ":8888/backend/web/imgs/capas/";

    public LivrosDetalhesReqAdaptador(Context context, ArrayList<Livro> livrosDetalhesReq) {
        this.context = context;
        this.livrosDetalhesReq = livrosDetalhesReq;
    }

    @Override
    public int getCount() {
        return livrosDetalhesReq.size();
    }

    @Override
    public Object getItem(int position) {
        return livrosDetalhesReq.get(position);
    }

    @Override
    public long getItemId(int position) {
        return livrosDetalhesReq.get(position).getId_livro();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolderLista viewHolder = null;

        if (inflater == null){
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        if (convertView == null){
            convertView = inflater.inflate(R.layout.item_requisicao_detalhes_fragment, null);

            viewHolder = (ViewHolderLista) convertView.getTag();

            if (viewHolder == null){
                viewHolder = new ViewHolderLista(convertView);
                convertView.setTag(viewHolder);
            }

        } else {
            viewHolder = (ViewHolderLista) convertView.getTag();
        }

        viewHolder.update(livrosDetalhesReq.get(position));
        return convertView;
    }

    private class ViewHolderLista {
        private TextView tvTitulo, tvAutor;
        private ImageView imgCapaDetalhesReq;

        public ViewHolderLista(View view){
            tvTitulo = view.findViewById(R.id.tvTitulo);
            tvAutor = view.findViewById(R.id.tvAutor);
            imgCapaDetalhesReq = view.findViewById(R.id.imgCapaDetalhesReq);
        }

        public void update(Livro livroDetalhesReq){
            nomeAutor = Singleton.getInstance(context).getNomeAutor(livroDetalhesReq.getId_autor());

            tvTitulo.setText(livroDetalhesReq.getTitulo());
            tvAutor.setText(nomeAutor);

            Glide.with(context)
                    .load(urlCapas+livroDetalhesReq.getCapa())
                    .placeholder(R.drawable.loading_capa)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(imgCapaDetalhesReq);
        }
    }
}
