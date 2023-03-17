package br.com.teste.controledevendas.order.mapper

import java.util.*

data class OrderDto(
    val id: Long = -1,
    val client: String,
    val createdAt: Date
)
