package br.com.teste.controledevendas.order.feature.addorder.extensions

import br.com.teste.controledevendas.order.feature.addorder.model.FormData


fun List<FormData>.sumAllProducts() =
    runCatching {
        this.sumOf { formData ->
            formData.price.toDouble() * formData.qt.toInt()
        }
    }.getOrDefault(0.0)

fun FormData.sumTotal() =
    runCatching {
        this.price.toDouble() * this.qt.toInt()
    }.getOrDefault(0.0)

