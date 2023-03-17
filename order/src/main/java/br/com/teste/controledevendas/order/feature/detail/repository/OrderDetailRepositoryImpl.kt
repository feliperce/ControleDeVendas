package br.com.teste.controledevendas.order.feature.detail.repository

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

    override fun removeOrderWithProducts(orderWithProducts: OrderWithProducts) = flow<Resource<Unit>> {
        orderDao.deleteOrderWithProducts(orderWithProducts.order, orderWithProducts.products)
        emit(Resource.Success(Unit))
    }.flowOn(Dispatchers.IO)

}