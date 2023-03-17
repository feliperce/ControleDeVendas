package br.com.teste.controledevendas.order.feature.home.repository

import br.com.teste.controledevendas.data.handler.ErrorType
import br.com.teste.controledevendas.data.handler.Resource
import br.com.teste.controledevendas.data.local.dao.OrderDao
import br.com.teste.controledevendas.data.local.entity.OrderWithProducts
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart

class OrderRepositoryImpl(
    private val orderDao: OrderDao
): OrderRepository {

    override fun getOrdersWithProducts() = flow<Resource<List<OrderWithProducts>>> {
        orderDao.getOrdersWithProducts().collect {
            emit(Resource.Success(data = it))
        }
        emit(Resource.Loading(false))
    }.flowOn(Dispatchers.IO)
        .onStart {
            emit(Resource.Loading(true))
        }
        .catch {
            emit(Resource.Error(error = ErrorType.GENERIC))
        }

}