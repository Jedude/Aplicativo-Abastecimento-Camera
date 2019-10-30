package com.example.myapplication.modelo;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;

public class abastecimentoDAO {

    private ArrayList<abastecimento> bancoDeDados;

    public ArrayList<abastecimento> obterLista(){
        Realm realm = Realm.getDefaultInstance();
        RealmResults lista = realm.where(abastecimento.class).findAll();
        bancoDeDados.clear();
        bancoDeDados.addAll(realm.copyFromRealm(lista));
        return bancoDeDados;
    }

    public void adicionarNaLista(abastecimento a){
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        //realm.insertOrUpdate(a);
        realm.copyToRealm(a);
        realm.commitTransaction();
    }

    public int atualizaNaLista(abastecimento a){
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.insertOrUpdate(a);
        realm.commitTransaction();

        for(int i = 0; i < bancoDeDados.size(); i++){
            if(bancoDeDados.get(i).getId().equals(a.getId())){
                bancoDeDados.set(i, a);
                return i;
            }
        }
        return -1;
    }

    public int excluiDaLista(abastecimento a){
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.where(abastecimento.class).equalTo("id", a.getId()).findFirst().deleteFromRealm();
        realm.commitTransaction();

        for(int i = 0; i < bancoDeDados.size(); i++){
            if(bancoDeDados.get(i).getId().equals(a.getId())){
                bancoDeDados.remove(i);
                return i;
            }
        }
        return -1;//nao encontrou o compromisso para excluir
    }

    public abastecimento obterObjetoPeloId(String id){
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        abastecimento a = realm.copyFromRealm(realm.where(abastecimento.class).equalTo("id", id).findFirst());
        realm.commitTransaction();
        return a;
    }

//    static public ArrayList<abastecimento> getBancoDeDados() {
//        return bancoDeDados;
//    }

    //Padrão de projeto DAO
    private static abastecimentoDAO INSTANCIA;

    public static abastecimentoDAO obterInstancia(){
        if (INSTANCIA == null){
            INSTANCIA = new abastecimentoDAO();
        }
        return INSTANCIA;
    }

    private abastecimentoDAO(){
        bancoDeDados = new ArrayList<abastecimento>();
    }

}
