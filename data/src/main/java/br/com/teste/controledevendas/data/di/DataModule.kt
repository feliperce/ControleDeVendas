package br.com.teste.controledevendas.data.di

import androidx.room.Room
import br.com.teste.controledevendas.data.local.SalesDb
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module

val dataModule = module {
    single(named("db")) {
        Room.databaseBuilder(
            androidContext(),
            SalesDb::class.java, "sales"
        ).build()
    }
}
