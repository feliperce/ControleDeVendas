package br.com.teste.controledevendas.order.feature.addorder.state

import br.com.teste.controledevendas.data.handler.ErrorType
import br.com.teste.controledevendas.order.feature.addorder.model.FormData
import br.com.teste.controledevendas.order.feature.detail.state.OrderDetailIntent
import br.com.teste.controledevendas.order.mapper.OrderWithProductsDto

data class AddOrderUiState (
    val loading: Boolean = false,
    var error: ErrorType = ErrorType.NONE,
    val orderWithProducts: OrderWithProductsDto? = null,
    var isRemoved: Boolean = false,
)

sealed class AddOrderIntent {
    class AddOrderWithProducts(val orderWithProducts: OrderWithProductsDto): AddOrderIntent()
    class ValidateForm(val formData: FormData): AddOrderIntent()
}