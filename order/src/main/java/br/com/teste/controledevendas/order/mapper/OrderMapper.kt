package br.com.teste.controledevendas.order.mapper

import br.com.teste.controledevendas.data.local.entity.OrderEntity
import br.com.teste.controledevendas.data.local.entity.OrderWithProducts
import br.com.teste.controledevendas.data.local.entity.ProductEntity

fun OrderEntity.toOrder() =
    OrderDto(
        id = id,
        client = client,
        createdAt = createdAt
    )

fun ProductEntity.toProduct() =
    ProductDto(
        id = id,
        name = name,
        description = description,
        qt = qt,
        price = price,
        orderId = orderId
    )

fun List<OrderEntity>.toOrderList() =
    map {
        it.toOrder()
    }

fun List<ProductEntity>.toProductList() =
    map {
        it.toProduct()
    }

fun OrderWithProducts.toOrderWithProductsDto() =
    OrderWithProductsDto(
        orderDto = order.toOrder(),
        productDtoList = products.toProductList()
    )

fun List<OrderWithProducts>.toOrderWithProductsDtoList() =
    map {
        it.toOrderWithProductsDto()
    }

fun OrderDto.toOrderEntity() =
    OrderEntity(
        id = id,
        client = client,
        createdAt = createdAt
    )

fun ProductDto.toProductEntity() =
    ProductEntity(
        id = id,
        name = name,
        description = description,
        qt = qt,
        price = price,
        orderId = orderId
    )

fun List<ProductDto>.toProductEntityList() =
    map {
        it.toProductEntity()
    }