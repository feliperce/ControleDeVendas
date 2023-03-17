package br.com.teste.controledevendas.order.feature.addorder.repository

import br.com.teste.controledevendas.data.handler.Resource
import br.com.teste.controledevendas.data.local.entity.OrderEntity
import br.com.teste.controledevendas.data.local.entity.OrderWithProducts
import br.com.teste.controledevendas.data.local.entity.ProductEntity
import kotlinx.coroutines.flow.Flow

interface AddOrderRepository {

    fun insertOrderWithProducts(order: OrderEntity, products: List<ProductEntity>): Flow<Resource<Unit>>

}