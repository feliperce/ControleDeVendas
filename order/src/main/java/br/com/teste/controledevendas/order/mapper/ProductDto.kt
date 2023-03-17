package br.com.teste.controledevendas.order.mapper

data class ProductDto(
    val id: Long,
    val name: String,
    val description: String,
    val qt: Int,
    val price: Double,
    val orderId: Long
)
