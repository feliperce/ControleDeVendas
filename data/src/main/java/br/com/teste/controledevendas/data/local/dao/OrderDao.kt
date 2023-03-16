package br.com.teste.controledevendas.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import br.com.teste.controledevendas.data.local.entity.OrderEntity
import br.com.teste.controledevendas.data.local.entity.OrderWithProducts
import kotlinx.coroutines.flow.Flow

@Dao
interface OrderDao {

    @Insert
    fun insertOrder(order: OrderEntity)

    @Transaction
    @Query("SELECT * FROM orders")
    fun getOrdersWithProducts(): Flow<List<OrderWithProducts>>

    @Transaction
    @Query("SELECT * FROM orders WHERE orders.id = :orderId")
    fun getOrderWithProductsByOrderId(orderId: Long): Flow<OrderWithProducts>

}