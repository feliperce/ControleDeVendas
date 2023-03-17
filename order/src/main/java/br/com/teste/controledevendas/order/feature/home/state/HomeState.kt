package br.com.teste.controledevendas.order.feature.home.state

import br.com.teste.controledevendas.data.handler.ErrorType
import br.com.teste.controledevendas.data.local.entity.OrderWithProducts

data class HomeUiState (
    val loading: Boolean = false,
    var error: ErrorType = ErrorType.NONE,
    val orderWithProductsList: List<OrderWithProducts> = listOf(),
    var totalSales: Double = 0.0
)

sealed class HomeIntent {
    object GetAllOrdersWithProducts : HomeIntent()
}