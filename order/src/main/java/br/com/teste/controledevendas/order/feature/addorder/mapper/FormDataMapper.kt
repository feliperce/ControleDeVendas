package br.com.teste.controledevendas.order.feature.addorder.mapper

import br.com.teste.controledevendas.data.local.entity.ProductEntity
import br.com.teste.controledevendas.order.feature.addorder.model.FormData

fun FormData.toProductEntity() =
    ProductEntity(
        name = name,
        description = description,
        qt = qt.toInt(),
        price = price.toDouble()
    )

fun List<FormData>.toProductEntityList() =
    map {
        it.toProductEntity()
    }