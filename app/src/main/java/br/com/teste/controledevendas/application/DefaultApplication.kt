package br.com.teste.controledevendas.application

import android.app.Application
import br.com.teste.controledevendas.data.di.dataModule
import br.com.teste.controledevendas.order.feature.addorder.di.addOrderModule
import br.com.teste.controledevendas.order.feature.detail.di.orderDetailModule
import br.com.teste.controledevendas.order.feature.home.di.homeModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class DefaultApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(applicationContext)
            modules(
                arrayListOf(
                    dataModule,
                    homeModule,
                    orderDetailModule,
                    addOrderModule
                )
            )
        }
    }
}