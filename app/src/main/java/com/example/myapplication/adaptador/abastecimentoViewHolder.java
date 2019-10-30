package com.example.myapplication.adaptador;

import android.os.Build;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.modelo.abastecimento;
import com.example.myapplication.telaLista;

import java.text.DateFormat;

public class abastecimentoViewHolder extends RecyclerView.ViewHolder {

    private TextView tvDescricao;
    private TextView tvData;
    private ConstraintLayout clPai;
    private String idDoAbastecimento;
    private ImageView imageView;


    public abastecimentoViewHolder(@NonNull View itemView) {
        super(itemView);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((telaLista) v.getContext()).modificaAbastecimento(v, idDoAbastecimento);
            }
        });


        tvDescricao = itemView.findViewById(R.id.tvDescricao);
        tvData = itemView.findViewById(R.id.tvData);
        clPai = (ConstraintLayout) itemView;
        imageView= itemView.findViewById(R.id.imagem);
//        int ipiranga=R.drawable.ipiranga;
//        int texaco=R.drawable.texaco;
//        int petrobras=R.drawable.petrobras_logo;
//        int shell=R.drawable.shell;
//        int outros=R.drawable.outrosP;
//        imageView.setImageResource(ipiranga);
//
    }

    public void atualizaGaveta(abastecimento a){
        idDoAbastecimento = a.getId();
        int outros=R.drawable.outros;
        int ipiranga=R.drawable.ipiranga;
        int texaco=R.drawable.texaco;
        int petrobras=R.drawable.petrobras_logo;
        int shell=R.drawable.shell;

        tvDescricao.setText( a.getDescricao() );

        DateFormat formatador = android.text.format.DateFormat.getDateFormat( tvDescricao.getContext() );
        String dataFormatada = formatador.format( a.getData().getTime() );
        tvData.setText( dataFormatada );
        System.out.print(a.getEscolhaPosto());
        System.out.print("teste");

        switch (a.getEscolhaPosto()){
            case "Ipiranga":
                imageView.setImageResource(ipiranga);
                break;
            case "Texaco":
                imageView.setImageResource(texaco);
                break;
            case "Shell":
                imageView.setImageResource(shell);
                break;
            case "Petrobras":
                imageView.setImageResource(petrobras);
                break;
             default:
                 imageView.setImageResource(outros);
        }
        Log.v(a.getEscolhaPosto(),"teste");


    }


}
