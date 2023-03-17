package br.com.teste.controledevendas.order.mapper

import java.util.*

object FakeData {
    val fakeProductList = listOf(
        ProductDto(name = "Naaaame", description = "Desc", qt = 5, price = 10.5, id = 0, orderId = 0),
        ProductDto(name = "Naaaame", description = "Desc", qt = 5, price = 10.5, id = 1, orderId = 0),
        ProductDto(name = "Naaaame", description = "Desc", qt = 5, price = 10.5, id = 2, orderId = 0),
        ProductDto(name = "Naaaame", description = "Desc", qt = 5, price = 10.5, id = 3, orderId = 0),
        ProductDto(name = "Naaaame", description = "Desc", qt = 5, price = 10.5, id = 4, orderId = 0)
    )

    val fakeOrderList = listOf(
        OrderDto(id = 0, client = "Lala", createdAt = Date()),
        OrderDto(id = 1, client = "Bleble", createdAt = Date()),
        OrderDto(id = 2, client = "Blublu", createdAt = Date()),
        OrderDto(id = 3, client = "Mumu", createdAt = Date()),
        OrderDto(id = 4, client = "Meme", createdAt = Date())
    )

    val fakeOrderWithProductsList = listOf(
        OrderWithProductsDto(
            orderDto = fakeOrderList[0],
            productDtoList = listOf(fakeProductList[0])
        ),
        OrderWithProductsDto(
            orderDto = fakeOrderList[1],
            productDtoList = listOf(fakeProductList[0])
        ),
        OrderWithProductsDto(
            orderDto = fakeOrderList[2],
            productDtoList = listOf(fakeProductList[0])
        ),
        OrderWithProductsDto(
            orderDto = fakeOrderList[3],
            productDtoList = listOf(fakeProductList[0])
        )
    )
}