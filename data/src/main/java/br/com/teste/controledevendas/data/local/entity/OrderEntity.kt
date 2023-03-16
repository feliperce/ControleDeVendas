package br.com.teste.controledevendas.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation
import java.util.Date

@Entity
data class OrderEntity(
    @PrimaryKey val id: Long,
    val client: String,
    @ColumnInfo(name = "created_at") val createdAt: Date
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
        parentColumn = "id",
        entityColumn = "orderId"
    )
    val products: List<ProductEntity>
)
