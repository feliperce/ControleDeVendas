package br.com.teste.controledevendas.order.feature.home.repository

import br.com.teste.controledevendas.data.handler.Resource
import br.com.teste.controledevendas.data.local.entity.OrderWithProducts
import kotlinx.coroutines.flow.Flow

interface OrderRepository {

    fun getOrdersWithProducts(): Flow<Resource<List<OrderWithProducts>>>

}