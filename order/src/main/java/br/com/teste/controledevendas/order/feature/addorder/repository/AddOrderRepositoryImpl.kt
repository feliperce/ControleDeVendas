package br.com.teste.controledevendas.order.feature.addorder.repository

import br.com.teste.controledevendas.data.handler.ErrorType
import br.com.teste.controledevendas.data.handler.Resource
import br.com.teste.controledevendas.data.local.dao.OrderDao
import br.com.teste.controledevendas.data.local.entity.OrderEntity
import br.com.teste.controledevendas.data.local.entity.OrderWithProducts
import br.com.teste.controledevendas.data.local.entity.ProductEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*

class AddOrderRepositoryImpl(
    private val orderDao: OrderDao
): AddOrderRepository {

    override fun insertOrderWithProducts(order: OrderEntity, products: List<ProductEntity>) = flow<Resource<Unit>> {
        orderDao.insertOrderWithProducts(order, products)
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