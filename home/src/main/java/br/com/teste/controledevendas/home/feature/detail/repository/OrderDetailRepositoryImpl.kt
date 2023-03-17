package br.com.teste.controledevendas.home.feature.detail.repository

import br.com.teste.controledevendas.data.handler.ErrorType
import br.com.teste.controledevendas.data.handler.Resource
import br.com.teste.controledevendas.data.local.dao.OrderDao
import br.com.teste.controledevendas.data.local.entity.OrderWithProducts
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*

class OrderDetailRepositoryImpl(
    private val orderDao: OrderDao
): OrderDetailRepository {

    override fun getOrderWithProductsByOrderId(orderId: Long) = flow<Resource<OrderWithProducts>> {
        orderDao.getOrderWithProductsByOrderId(orderId).collect {
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

    override fun removeOrderWithProductsByOrderId(orderId: Long) = flow<Resource<Unit>> {
        emit(Resource.Success(Unit))
    }

}