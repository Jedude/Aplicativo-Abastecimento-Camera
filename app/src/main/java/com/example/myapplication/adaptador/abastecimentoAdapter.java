package com.example.myapplication.adaptador;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.modelo.abastecimento;
import com.example.myapplication.modelo.abastecimentoDAO;

public class abastecimentoAdapter extends RecyclerView.Adapter {
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        ConstraintLayout elementoPrincipalXML = (ConstraintLayout) LayoutInflater.from(parent.getContext()).inflate(
                R.layout.item_abastecimento, parent, false
        );
        abastecimentoViewHolder gaveta = new abastecimentoViewHolder(elementoPrincipalXML);
        return gaveta;

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        abastecimento a = abastecimentoDAO.obterInstancia().obterLista().get(position);
        abastecimentoViewHolder gaveta = (abastecimentoViewHolder) holder;
        gaveta.atualizaGaveta(a);
    }

    @Override
    public int getItemCount() {
        return abastecimentoDAO.obterInstancia().obterLista().size();
    }
}
