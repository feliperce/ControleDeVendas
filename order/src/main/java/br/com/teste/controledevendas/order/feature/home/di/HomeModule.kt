package br.com.teste.controledevendas.order.feature.home.di

import br.com.teste.controledevendas.data.local.SalesDb
import br.com.teste.controledevendas.order.feature.home.repository.OrderRepositoryImpl
import br.com.teste.controledevendas.order.feature.home.viewmodel.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val homeModule = module {

    single {
        get<SalesDb>(
            named("db")
        ).orderDao()
    }

    single { OrderRepositoryImpl(get()) }

    viewModel { HomeViewModel(get()) }

}