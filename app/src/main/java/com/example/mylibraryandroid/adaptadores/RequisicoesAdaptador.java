package com.example.mylibraryandroid.adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.mylibraryandroid.R;
import com.example.mylibraryandroid.modelo.Requisicao;

import java.util.ArrayList;

public class RequisicoesAdaptador extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;
    private ArrayList<Requisicao> requisicoes;

    public void RequisicoesAdaptador(Context context, ArrayList<Requisicao> requisicoes) {
        this.context = context;
        this.requisicoes = requisicoes;
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
            convertView = inflater.inflate(R.layout.item_requisicao_fragment, null);

            ViewHolderLista viewHolder = (ViewHolderLista) convertView.getTag();

            if(viewHolder == null){
                viewHolder = new ViewHolderLista(convertView);
                convertView.setTag(viewHolder);
            }
            viewHolder.update(requisicoes.get(position));
        }
        return convertView;
    }

    private class ViewHolderLista {
        private TextView tvNumReq, tvTotalLivros, tvEstadoReq, tvBibReq;

        public ViewHolderLista(View view) {
            tvNumReq = view.findViewById(R.id.tvNumReq);
            tvTotalLivros = view.findViewById(R.id.tvTotalLivros);
            tvEstadoReq = view.findViewById(R.id.tvEstadoReq);
            tvBibReq = view.findViewById(R.id.tvBibReq);
        }

        public void update(Requisicao requisicao) {
            tvNumReq.setText("#"+requisicao.getId_bib_levantamento());
            tvTotalLivros.setText(requisicao.get);
        }
    }

}
