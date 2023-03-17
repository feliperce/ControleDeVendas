package br.com.teste.controledevendas.order.feature.addorder.repository

import br.com.teste.controledevendas.data.handler.ErrorType
import br.com.teste.controledevendas.data.handler.Resource
import br.com.teste.controledevendas.data.local.dao.OrderDao
import br.com.teste.controledevendas.data.local.entity.OrderEntity
import br.com.teste.controledevendas.order.feature.addorder.mapper.toProductEntityList
import br.com.teste.controledevendas.order.feature.addorder.model.FormData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*

class AddOrderRepositoryImpl(
    private val orderDao: OrderDao
): AddOrderRepository {

    override fun insertOrderWithProducts(clientName: String, formDataList: List<FormData>) = flow<Resource<Unit>> {
        orderDao.insertOrderWithProduct(
            order = OrderEntity(client = clientName),
            productEntityList = formDataList.toProductEntityList()
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