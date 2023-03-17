package br.com.teste.controledevendas.data.local.dao

import androidx.room.*
import br.com.teste.controledevendas.data.local.entity.OrderEntity
import br.com.teste.controledevendas.data.local.entity.OrderWithProducts
import br.com.teste.controledevendas.data.local.entity.ProductEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface OrderDao {

    @Transaction
    @Query("SELECT * FROM orders")
    fun getOrdersWithProducts(): Flow<List<OrderWithProducts>>

    @Transaction
    @Query("SELECT * FROM orders WHERE orders.id = :orderId")
    fun getOrderWithProductsByOrderId(orderId: Long): Flow<OrderWithProducts>

    @Delete
    fun deleteOrderWithProducts(order: OrderEntity, products: List<ProductEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOrder(orderEntity: OrderEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertProduct(productEntity: ProductEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertProducts(productEntityList: List<ProductEntity>)

    @Transaction
    fun insertOrderWithProduct(
        order: OrderEntity,
        productEntityList: List<ProductEntity>
    ) {
        val orderId = insertOrder(order)

        productEntityList.forEach {
            it.orderId = orderId
        }
        insertProducts(productEntityList)
    }



}