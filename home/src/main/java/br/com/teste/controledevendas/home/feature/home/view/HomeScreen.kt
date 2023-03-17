package br.com.teste.controledevendas.home.feature.home.view

import androidx.compose.foundation.clickable
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import br.com.teste.controledevendas.data.handler.ErrorType
import br.com.teste.controledevendas.data.local.entity.OrderEntity
import br.com.teste.controledevendas.data.local.entity.OrderWithProducts
import br.com.teste.controledevendas.data.local.entity.ProductEntity
import br.com.teste.controledevendas.design.component.DefaultAppBar
import br.com.teste.controledevendas.design.theme.MarginPaddingSizeMedium
import br.com.teste.controledevendas.design.theme.TextSizeMicro
import br.com.teste.controledevendas.design.theme.TextSizeSmall
import br.com.teste.controledevendas.home.R
import br.com.teste.controledevendas.home.feature.home.state.HomeIntent
import br.com.teste.controledevendas.home.feature.home.viewmodel.HomeViewModel
import org.koin.androidx.compose.getViewModel
import java.util.*

@Composable
fun HomeScreen(
    navController: NavHostController = rememberNavController(),
    homeViewModel: HomeViewModel = getViewModel()
) {

    val scaffoldState = rememberScaffoldState()
    val homeUiState by homeViewModel.homeState.collectAsState()

    homeViewModel.sendIntent(HomeIntent.GetAllOrdersWithProducts)

    homeUiState.error.let { error ->
        if (error != ErrorType.NONE) {
            val errorMsg = stringResource(id = R.string.home_error_generic)

            LaunchedEffect(Unit) {
                scaffoldState.snackbarHostState.showSnackbar(message = errorMsg)
                homeUiState.error = ErrorType.NONE
            }
        }
    }

    OrderContent(
        onOrderItemClick = {
            navController.navigate("order/detail/${it.id}")
        },
        onAddOrderClick = {

        },
        orderList = homeUiState.orderWithProductsList,
        showProgress = homeUiState.loading,
        totalSales = homeUiState.totalSales
    )

}

@Composable
fun OrderContent(
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    onOrderItemClick: (order: OrderEntity) -> Unit,
    showProgress: Boolean,
    onAddOrderClick: () -> Unit,
    orderList: List<OrderWithProducts>,
    totalSales: Double
) {

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            DefaultAppBar(
                title = stringResource(id = R.string.screen_title),
                subTitle = stringResource(id = R.string.screen_subtitle, totalSales)
            )
        },
        content = {
            if (showProgress) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxSize(),
                ) {
                    CircularProgressIndicator()
                }
            }

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
    orderList: List<OrderWithProducts>,
    onOrderItemClick: (order: OrderEntity) -> Unit
) {
    LazyColumn() {
        items(orderList) {
            OrderItem(order = it.order, onOrderItemClick = onOrderItemClick)
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
                fontSize = TextSizeSmall
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
fun OrderItemPreview() {
    OrderItem(order = fakeOrderList[1]) {}
}

@Composable
@Preview(showBackground = true)
fun OrderItemListPreview() {
    OrderItemList(orderList = fakeOrderWithProductsList) {}
}

@Composable
@Preview(showBackground = true)
fun OrderContentPreview() {
    OrderContent(
        onOrderItemClick = { },
        onAddOrderClick = {  },
        orderList = fakeOrderWithProductsList,
        showProgress = true,
        totalSales = 1260.31
    )
}

private val fakeProductList = listOf(
    ProductEntity(id = 0, name = "Naaaame", description = "Desc", qt = 5, price = 10.5, orderId = 0),
    ProductEntity(id = 1, name = "Naaaame", description = "Desc", qt = 5, price = 10.5, orderId = 0),
    ProductEntity(id = 2, name = "Naaaame", description = "Desc", qt = 5, price = 10.5, orderId = 0),
    ProductEntity(id = 3, name = "Naaaame", description = "Desc", qt = 5, price = 10.5, orderId = 0),
    ProductEntity(id = 4, name = "Naaaame", description = "Desc", qt = 5, price = 10.5, orderId = 0)
)

private val fakeOrderList = listOf(
    OrderEntity(id = 0, client = "Lala", createdAt = Date()),
    OrderEntity(id = 1, client = "Bleble", createdAt = Date()),
    OrderEntity(id = 2, client = "Blublu", createdAt = Date()),
    OrderEntity(id = 3, client = "Mumu", createdAt = Date()),
    OrderEntity(id = 4, client = "Meme", createdAt = Date())
)

private val fakeOrderWithProductsList = listOf(
    OrderWithProducts(
        order = fakeOrderList[0],
        products = listOf(fakeProductList[0])
    ),
    OrderWithProducts(
        order = fakeOrderList[1],
        products = listOf(fakeProductList[0])
    ),
    OrderWithProducts(
        order = fakeOrderList[2],
        products = listOf(fakeProductList[0])
    ),
    OrderWithProducts(
        order = fakeOrderList[3],
        products = listOf(fakeProductList[0])
    )
)