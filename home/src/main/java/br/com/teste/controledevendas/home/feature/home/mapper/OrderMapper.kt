package br.com.teste.controledevendas.home.feature.home.mapper

import br.com.teste.controledevendas.data.local.entity.OrderEntity

fun OrderEntity.toOrder() =
    Order(
        id = id,
        client = client,
        createdAt = createdAt
    )

fun List<OrderEntity>.toOrderList() =
    map {
        Order(
            id = it.id,
            client = it.client,
            createdAt = it.createdAt
        )
    }