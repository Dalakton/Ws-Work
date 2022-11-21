package me.project.wswork.di

import me.project.wswork.data.local.CarDatabase
import me.project.wswork.data.remote.CarsWebService
import me.project.wswork.data.repositories.CarsRepository
import me.project.wswork.data.repositories.ResourseCarRepository
import me.project.wswork.ui.home.CarsViewModel
import me.project.wswork.ui.register.RegisterViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


// Este viewModel é um factory toda vez que precisar de um viewModel do tipo CarsViewModel
// automaticamente o koin vai criar essa instancia e entregar para quem chamar o CarsViewModel
val viewModelModule = module {
    //usamos o get para pedir para o koin pegar uma dependencia
    viewModel { CarsViewModel(repository = get()) }
    viewModel { RegisterViewModel(repository = get()) }
}
// single nos diz que será instanciado so uma vez, e quando sor necessario utilizar da mesma classe
//nos será retornado da mesma classe , ou sejá não instanciara de novo.
val repositoryModule = module {
    single<CarsRepository> { ResourseCarRepository(carWebService = get(), dao = get()) }
}

val webServiceModule = module {
    single { CarsWebService.getInstance() }
}

val dataBaseModule = module {
    single { CarDatabase.getInstance(androidApplication()).CarDao() }
}


