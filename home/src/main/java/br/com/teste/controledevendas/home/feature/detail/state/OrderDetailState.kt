package br.com.teste.controledevendas.home.feature.detail.state

import br.com.teste.controledevendas.data.handler.ErrorType
import br.com.teste.controledevendas.data.local.entity.OrderWithProducts

data class OrderUiState (
    val loading: Boolean = false,
    var error: ErrorType = ErrorType.NONE,
    val orderWithProducts: OrderWithProducts? = null
)

sealed class OrderDetailIntent {
    class GetAllOrdersWithProducts(val orderId: Long) : OrderDetailIntent()
}