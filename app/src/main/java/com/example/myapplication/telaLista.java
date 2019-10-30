package com.example.myapplication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.myapplication.adaptador.abastecimentoAdapter;
import com.example.myapplication.modelo.abastecimentoDAO;

public class telaLista extends AppCompatActivity {

    private abastecimentoAdapter adaptador;
    private RecyclerView rvAbastecimentos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_lista);

        rvAbastecimentos= findViewById(R.id.rvAbastecimento);

        adaptador = new abastecimentoAdapter();
        rvAbastecimentos.setLayoutManager( new LinearLayoutManager(this));
        rvAbastecimentos.setAdapter(adaptador);
    }

    public void modificaAbastecimento(View v, String idDoAbastecimento){
        Intent intencao = new Intent( this, telaCadastro.class );
        intencao.putExtra("idAbastecimento", idDoAbastecimento);
        startActivityForResult(intencao, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1){
            if (resultCode == 300){
                int posicao = data.getIntExtra("posicaoDoObjetoEditado", -1);
                adaptador.notifyItemChanged( posicao );
                rvAbastecimentos.smoothScrollToPosition(posicao);
            }else if (resultCode == 301){
                Toast.makeText(this, "Abastecimento inserido com sucesso", Toast.LENGTH_LONG).show();
                int posicao = abastecimentoDAO.obterInstancia().obterLista().size()-1;
                adaptador.notifyItemInserted( posicao );
                rvAbastecimentos.smoothScrollToPosition(posicao);
            }else if (resultCode == 302){
                Toast.makeText(this, "Abastecimento excluído com sucesso", Toast.LENGTH_LONG).show();
                int posicao = data.getIntExtra("posicaoDoObjetoExcluido", -1);
                adaptador.notifyItemRemoved(posicao);
            }
        }
    }

    public void adicionaAbastecimento(View v){
        Intent intencao = new Intent( this, telaCadastro.class );
        startActivityForResult(intencao, 1);
    }
}
