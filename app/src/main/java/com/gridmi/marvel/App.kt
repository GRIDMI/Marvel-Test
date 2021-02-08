package com.gridmi.marvel

import android.app.Application
import io.realm.Realm
import io.realm.RealmConfiguration

class App : Application() {

    override fun onCreate() {

        super.onCreate()

        Realm.init(this)
        val config:RealmConfiguration = RealmConfiguration.Builder()
            .name("marvel.realm")
            .schemaVersion(1)
            .deleteRealmIfMigrationNeeded()
            .build()
        Realm.setDefaultConfiguration(config)

    }

}
