package br.com.teste.controledevendas.home.feature.detail.state

import br.com.teste.controledevendas.data.handler.ErrorType
import br.com.teste.controledevendas.data.local.entity.OrderWithProducts

data class OrderDetailUiState (
    val loading: Boolean = false,
    var error: ErrorType = ErrorType.NONE,
    val orderWithProducts: OrderWithProducts? = null,
    var isRemoved: Boolean = false
)

sealed class OrderDetailIntent {
    class GetAllOrdersWithProductsByOrderId(val orderId: Long): OrderDetailIntent()
    class RemoveOrderWithProducts(val orderWithProducts: OrderWithProducts): OrderDetailIntent()
}