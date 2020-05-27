package com.ectosense.contactsbook

import android.app.Application
import com.ectosense.contactsbook.di.databaseModule
import com.ectosense.contactsbook.di.networkModule
import com.ectosense.contactsbook.di.repositoryModule
import com.ectosense.contactsbook.di.viewModelModule
import com.ectosense.contactsbook.utils.NetworkUtils
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.module.Module

class App : Application() {

    private val appComponent : MutableList<Module> = mutableListOf(networkModule, databaseModule, viewModelModule, repositoryModule)

    override fun onCreate() {
        super.onCreate()
        NetworkUtils.registerNetworkCallback(this)
        startKoin {
            androidContext(applicationContext)
            modules(appComponent)
        }
    }
}