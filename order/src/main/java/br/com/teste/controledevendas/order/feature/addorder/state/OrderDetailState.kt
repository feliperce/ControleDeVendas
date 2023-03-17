package br.com.teste.controledevendas.order.feature.addorder.state

import br.com.teste.controledevendas.data.handler.ErrorType
import br.com.teste.controledevendas.order.feature.addorder.model.FormData
import br.com.teste.controledevendas.order.mapper.OrderWithProductsDto
import br.com.teste.controledevendas.order.mapper.ProductDto

data class AddOrderUiState (
    val loading: Boolean = false,
    var error: ErrorType = ErrorType.NONE,
    val orderWithProducts: OrderWithProductsDto? = null,
    val formDataList: ArrayList<FormData> = arrayListOf(),
    var orderTotal: Double = 0.0,
    var isAdded: Boolean = false
)

sealed class AddOrderIntent {
    class AddOrder(val clientName: String): AddOrderIntent()
    class ValidateForm(val formData: FormData): AddOrderIntent()
}