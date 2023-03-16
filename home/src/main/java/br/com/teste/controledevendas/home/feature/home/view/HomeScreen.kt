package br.com.teste.controledevendas.home.feature.home.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.teste.controledevendas.data.local.entity.OrderEntity
import br.com.teste.controledevendas.design.component.DefaultAppBar
import br.com.teste.controledevendas.design.theme.MarginPaddingSizeMedium
import br.com.teste.controledevendas.design.theme.TextSizeMicro
import br.com.teste.controledevendas.home.R
import java.util.*

@Composable
fun OrderContent(
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    onOrderItemClick: (order: OrderEntity) -> Unit,
    onAddOrderClick: () -> Unit,
    orderList: List<OrderEntity>
) {

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            DefaultAppBar(title = stringResource(id = R.string.screen_title))
        },
        content = {
            Column() {
                OrderItemList(
                    orderList = orderList,
                    onOrderItemClick = onOrderItemClick
                )
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    onAddOrderClick()
                },
                content = {
                    Icon(
                        Icons.Rounded.Add,
                        contentDescription = null
                    )
                }
            )
        }
    )
}

@Composable
fun OrderItemList(
    orderList: List<OrderEntity>,
    onOrderItemClick: (order: OrderEntity) -> Unit
) {
    LazyColumn() {
        items(orderList) {
            OrderItem(order = it, onOrderItemClick = onOrderItemClick)
            Divider(color = Color.Black, thickness = 1.dp)
        }
    }
}

@Composable
fun OrderItem(
    order: OrderEntity,
    onOrderItemClick: (order: OrderEntity) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(MarginPaddingSizeMedium)
            .clickable {
                onOrderItemClick(order)
            }
    ) {
        Text(text = stringResource(id = R.string.home_order_item_order, order.id))
        CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.disabled) {
            Text(
                text = stringResource(id = R.string.home_order_item_client, order.client),
                fontSize = TextSizeMicro
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
fun OrderItemPreview() {
    OrderItem(order = fakeOrderList[1], onOrderItemClick = {})
}

@Composable
@Preview(showBackground = true)
fun OrderItemListPreview() {
    OrderItemList(orderList = fakeOrderList, onOrderItemClick = {})
}

@Composable
@Preview(showBackground = true)
fun OrderContentPreview() {
    OrderContent(
        onOrderItemClick = { },
        onAddOrderClick = {  },
        orderList = fakeOrderList
    )
}

private val fakeOrderList = arrayListOf(
    OrderEntity(id = 0, client = "Lala", createdAt = Date()),
    OrderEntity(id = 1, client = "Bleble", createdAt = Date()),
    OrderEntity(id = 2, client = "Blublu", createdAt = Date()),
    OrderEntity(id = 3, client = "Mumu", createdAt = Date()),
    OrderEntity(id = 4, client = "Meme", createdAt = Date())
)