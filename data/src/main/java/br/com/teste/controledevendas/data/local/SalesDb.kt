package br.com.teste.controledevendas.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import br.com.teste.controledevendas.data.local.converter.DateConverter
import br.com.teste.controledevendas.data.local.dao.OrderDao
import br.com.teste.controledevendas.data.local.entity.OrderEntity
import br.com.teste.controledevendas.data.local.entity.ProductEntity

@Database(entities = [
    OrderEntity::class,
    ProductEntity::class
],
    version = 1
)
@TypeConverters(DateConverter::class)
abstract class SalesDb : RoomDatabase() {
    abstract fun orderDao(): OrderDao
}

