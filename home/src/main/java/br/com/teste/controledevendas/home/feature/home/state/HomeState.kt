package br.com.teste.controledevendas.home.feature.home.state

import br.com.teste.controledevendas.data.handler.ErrorType
import br.com.teste.controledevendas.data.local.entity.OrderWithProducts

data class HomeUiState (
    val loading: Boolean = false,
    val error: ErrorType? = null,
    val orderWithProductsList: List<OrderWithProducts> = listOf()
)

sealed class HomeIntent {
    object GetAllOrdersWithProducts : HomeIntent()
}