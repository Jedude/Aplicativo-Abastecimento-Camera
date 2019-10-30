package com.example.myapplication.modelo;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;

public class abastecimento extends RealmObject {

    @PrimaryKey
    private String id;
    private double quilometragem;
    private double litrosAbastecidos;
    private String escolhaPosto;
    private Date dataPura;
    private String caminhoFotografia;


    @Ignore
    private Calendar data;

    public abastecimento(){
        id = UUID.randomUUID().toString();
    }

    public String getId() {
        return id;
    }

    public double getLitrosAbastecidos() {
        return litrosAbastecidos;
    }

    public void setLitrosAbastecidos(double litrosAbastecidos) {
        this.litrosAbastecidos = litrosAbastecidos;
    }

    public double getQuilometragem() {
        return quilometragem;
    }

    public void setQuilometragem(double quilometragem) {
        this.quilometragem = quilometragem;
    }

    public String getEscolhaPosto() {
        return escolhaPosto;
    }

    public void setEscolhaPosto(String escolhaPosto) {
        this.escolhaPosto = escolhaPosto;
    }

    public Calendar getData() {
        if (dataPura != null){
            data = Calendar.getInstance();
            data.setTime(dataPura);
        }
        return data;
    }

    public void setData(Calendar data) {
        this.data = data;
        this.dataPura = data.getTime();
    }
    public String getDescricao(){
        return "Abastecidos: "+String.valueOf(litrosAbastecidos)+". Km do véiculo: "+String.valueOf(quilometragem);
    }

    public String getCaminhoFotografia() {
        return caminhoFotografia;
    }

    public void setCaminhoFotografia(String caminhoFotografia) {
        this.caminhoFotografia = caminhoFotografia;
    }
}
