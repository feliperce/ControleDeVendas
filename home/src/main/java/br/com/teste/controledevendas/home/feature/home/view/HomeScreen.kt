package br.com.teste.controledevendas.home.feature.home.view

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.teste.controledevendas.design.component.DefaultAppBar
import br.com.teste.controledevendas.design.theme.MarginPaddingSizeMedium
import br.com.teste.controledevendas.design.theme.TextSizeMicro
import br.com.teste.controledevendas.design.theme.TextSizeSmall
import br.com.teste.controledevendas.home.R
import br.com.teste.controledevendas.home.feature.home.mapper.Order
import org.koin.androidx.compose.getViewModel

@Composable
fun OrderContent(
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    onOrderItemClick: (order: Order) -> Unit,
    onAddOrderClick: () -> Unit,
    orderList: List<Order>
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
    orderList: List<Order>,
    onOrderItemClick: (order: Order) -> Unit
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
    order: Order,
    onOrderItemClick: (order: Order) -> Unit
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
    Order(id = 0, client = "Lala"),
    Order(id = 1, client = "Bleble"),
    Order(id = 2, client = "Blublu"),
    Order(id = 3, client = "Mumu"),
    Order(id = 4, client = "Meme")
)