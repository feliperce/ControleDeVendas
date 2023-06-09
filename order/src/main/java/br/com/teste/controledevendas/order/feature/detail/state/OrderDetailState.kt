package br.com.teste.controledevendas.order.feature.detail.state

import br.com.teste.controledevendas.data.handler.ErrorType
import br.com.teste.controledevendas.order.mapper.OrderWithProductsDto

data class OrderDetailUiState (
    val loading: Boolean = false,
    var error: ErrorType = ErrorType.NONE,
    val orderWithProducts: OrderWithProductsDto? = null,
    var isRemoved: Boolean = false
)

sealed class OrderDetailIntent {
    class GetAllOrdersWithProductsByOrderId(val orderId: Long): OrderDetailIntent()
    class RemoveOrderWithProducts(val orderWithProducts: OrderWithProductsDto): OrderDetailIntent()
}