package br.com.teste.controledevendas.order.mapper

data class OrderWithProductsDto(
    val orderDto: OrderDto,
    val productDtoList: List<ProductDto>
)
