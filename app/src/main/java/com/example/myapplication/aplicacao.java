package com.example.myapplication;

import android.app.Application;

import com.example.myapplication.modelo.gerenciadorMigracoes;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class aplicacao extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
        //Realm.deleteRealm(Realm.getDefaultConfiguration());

        RealmConfiguration configuracaoRealm = new RealmConfiguration.Builder()
                .schemaVersion(1)
                .migration( new gerenciadorMigracoes())
                .build();

        Realm.setDefaultConfiguration( configuracaoRealm );
        Realm.getInstance( configuracaoRealm );
    }

}
