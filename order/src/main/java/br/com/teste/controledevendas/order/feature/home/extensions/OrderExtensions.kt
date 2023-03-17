package br.com.teste.controledevendas.order.feature.home.extensions

import br.com.teste.controledevendas.data.local.entity.OrderWithProducts
import br.com.teste.controledevendas.data.local.entity.ProductEntity
import br.com.teste.controledevendas.order.mapper.OrderWithProductsDto
import br.com.teste.controledevendas.order.mapper.ProductDto

fun List<ProductDto>.sumAllProducts() =
    this.sumOf { product ->
        product.price * product.qt
    }

fun List<OrderWithProductsDto>.sumAllSales() =
    this.sumOf { sale ->
        sale.productDtoList.sumAllProducts()
    }

fun ProductDto.sumAll() =
    this.price * this.qt