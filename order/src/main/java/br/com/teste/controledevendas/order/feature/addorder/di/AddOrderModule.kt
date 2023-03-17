package br.com.teste.controledevendas.order.feature.addorder.di

import br.com.teste.controledevendas.data.local.SalesDb
import br.com.teste.controledevendas.order.feature.addorder.repository.AddOrderRepositoryImpl
import br.com.teste.controledevendas.order.feature.addorder.viewmodel.AddOrderViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val addOrderModule = module {

    single {
        get<SalesDb>(
            named("db")
        ).orderDao()
    }

    single { AddOrderRepositoryImpl(get()) }

    viewModel { AddOrderViewModel(get()) }

}