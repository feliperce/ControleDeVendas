package br.com.teste.controledevendas.home.feature.home.repository

import br.com.teste.controledevendas.data.handler.Resource
import br.com.teste.controledevendas.data.local.entity.OrderEntity
import br.com.teste.controledevendas.data.local.entity.OrderWithProducts
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

interface OrderRepository {

    fun getOrdersWithProducts(): Flow<Resource<List<OrderWithProducts>>>

}