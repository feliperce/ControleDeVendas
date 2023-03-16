package br.com.teste.controledevendas.data.local.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity
data class OrderEntity(
    @PrimaryKey val id: Long,
    val client: String
)

@Entity
data class ProductEntity(
    @PrimaryKey val id: Long,
    val name: String,
    val description: String,
    val qt: Int,
    val price: Double,
    val orderId: Long
)

data class OrderWithProducts(
    @Embedded val order: OrderEntity,
    @Relation(
        parentColumn = "userId",
        entityColumn = "orderId"
    )
    val products: List<ProductEntity>
)
