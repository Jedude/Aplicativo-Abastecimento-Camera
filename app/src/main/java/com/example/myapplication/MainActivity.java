package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.myapplication.modelo.abastecimento;
import com.example.myapplication.modelo.abastecimentoDAO;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public TextView autonomia;
    public TextView km;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Realm.init(this);
        //Realm.deleteRealm(Realm.getDefaultConfiguration());
        setContentView(R.layout.activity_main);

        autonomia=findViewById(R.id.tvAutonomia);
        //km.findViewById(R.id.textView2);

        autonomia.setText(String.valueOf(calculoAutonomia()));
        //km.setText("km/L");



        Button bt= findViewById(R.id.btAbastecimentos);

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intencao = new  Intent(MainActivity.this, telaLista.class);
                startActivity(intencao);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        autonomia.setText(String.valueOf(calculoAutonomia()));

    }

    public double calculoAutonomia() {
        double km=0.0;
        double litros=0.0;
        double result=0.0;
        ArrayList<abastecimento> lista;
         lista = abastecimentoDAO.obterInstancia().obterLista();
        if (lista.size() > 1) {
            km = lista.get(lista.size()-1).getQuilometragem() - lista.get(0).getQuilometragem();
            for (int i = 0; i < lista.size(); i++) {
                litros = +lista.get(i).getLitrosAbastecidos();
            }
           result= km / litros;
        }
        return result;
    }
}
