package br.com.teste.controledevendas.order.feature.detail.di

import br.com.teste.controledevendas.data.local.SalesDb
import br.com.teste.controledevendas.order.feature.detail.repository.OrderDetailRepositoryImpl
import br.com.teste.controledevendas.order.feature.detail.viewmodel.OrderDetailViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val orderDetailModule = module {

    single {
        get<SalesDb>(
            named("db")
        ).orderDao()
    }

    single { OrderDetailRepositoryImpl(get()) }

    viewModel { OrderDetailViewModel(get()) }

}