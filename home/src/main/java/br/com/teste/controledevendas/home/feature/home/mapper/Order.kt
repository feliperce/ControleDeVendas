package br.com.teste.controledevendas.home.feature.home.mapper

import java.util.Date

data class Order(
    val id: Long,
    val client: String,
    val createdAt: Date
)
