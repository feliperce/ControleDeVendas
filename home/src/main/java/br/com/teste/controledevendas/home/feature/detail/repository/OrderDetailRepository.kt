package br.com.teste.controledevendas.home.feature.detail.repository

import br.com.teste.controledevendas.data.handler.Resource
import br.com.teste.controledevendas.data.local.entity.OrderWithProducts
import kotlinx.coroutines.flow.Flow

interface OrderDetailRepository {

    fun getOrderWithProductsByOrderId(orderId: Long): Flow<Resource<OrderWithProducts>>

    fun removeOrderWithProducts(orderWithProducts: OrderWithProducts): Flow<Resource<Unit>>

}