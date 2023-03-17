package br.com.teste.controledevendas.order.feature.detail.repository

import br.com.teste.controledevendas.data.handler.Resource
import br.com.teste.controledevendas.data.local.entity.OrderWithProducts
import br.com.teste.controledevendas.order.mapper.OrderWithProductsDto
import kotlinx.coroutines.flow.Flow

interface OrderDetailRepository {

    fun getOrderWithProductsByOrderId(orderId: Long): Flow<Resource<OrderWithProductsDto>>

    fun removeOrderWithProducts(orderWithProducts: OrderWithProductsDto): Flow<Resource<Unit>>

}