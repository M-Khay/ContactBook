package com.ectosense.contactsbook.di

import com.ectosense.contactsbook.network.ContactRepository
import com.ectosense.contactsbook.network.ContactRepositoryImpl
import com.ectosense.contactsbook.ui.ContactViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module


val networkModule = module {
    factory { ContactAPIFactory.retrofitPupil() }
}

val databaseModule = module {
    factory { DatabaseFactory.getDBInstance(get()) }
}

val repositoryModule = module {
    single<ContactRepository>{ ContactRepositoryImpl(get(), get()) }
}

val viewModelModule = module {
    viewModel {
        ContactViewModel(get())
    }
}