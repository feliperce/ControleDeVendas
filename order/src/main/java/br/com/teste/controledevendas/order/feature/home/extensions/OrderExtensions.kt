package br.com.teste.controledevendas.order.feature.home.extensions

import br.com.teste.controledevendas.data.local.entity.OrderWithProducts
import br.com.teste.controledevendas.data.local.entity.ProductEntity

fun List<ProductEntity>.sumAllProducts() =
    this.sumOf { product ->
        product.price * product.qt
    }

fun List<OrderWithProducts>.sumAllSales() =
    this.sumOf { sale ->
        sale.products.sumAllProducts()
    }