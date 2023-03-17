package br.com.teste.controledevendas.order.feature.detail.repository

import br.com.teste.controledevendas.data.handler.ErrorType
import br.com.teste.controledevendas.data.handler.Resource
import br.com.teste.controledevendas.data.local.dao.OrderDao
import br.com.teste.controledevendas.data.local.entity.OrderEntity
import br.com.teste.controledevendas.data.local.entity.OrderWithProducts
import br.com.teste.controledevendas.order.mapper.OrderWithProductsDto
import br.com.teste.controledevendas.order.mapper.toOrderEntity
import br.com.teste.controledevendas.order.mapper.toOrderWithProductsDto
import br.com.teste.controledevendas.order.mapper.toProductEntityList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*

class OrderDetailRepositoryImpl(
    private val orderDao: OrderDao
): OrderDetailRepository {

    override fun getOrderWithProductsByOrderId(orderId: Long) = flow<Resource<OrderWithProductsDto>> {
        orderDao.getOrderWithProductsByOrderId(orderId).collect {
            emit(Resource.Success(data = it.toOrderWithProductsDto()))
        }
        emit(Resource.Loading(false))
    }.flowOn(Dispatchers.IO)
        .onStart {
            emit(Resource.Loading(true))
        }
        .catch {
            emit(Resource.Error(error = ErrorType.GENERIC))
        }

    override fun removeOrderWithProducts(orderWithProducts: OrderWithProductsDto) = flow<Resource<Unit>> {
        val orderDto = orderWithProducts.orderDto
        val productsDtoList = orderWithProducts.productDtoList

        orderDao.deleteOrderWithProducts(
            orderWithProducts.orderDto.toOrderEntity(), orderWithProducts.productDtoList.toProductEntityList()
        )
        emit(Resource.Success(Unit))
        emit(Resource.Loading(false))
    }.flowOn(Dispatchers.IO)
        .onStart {
            emit(Resource.Loading(true))
        }
        .catch {
            emit(Resource.Error(error = ErrorType.GENERIC))
        }

}