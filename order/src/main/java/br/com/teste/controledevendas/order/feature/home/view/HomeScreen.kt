package br.com.teste.controledevendas.order.feature.home.view

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
import br.com.teste.controledevendas.design.theme.TextSizeSmall
import br.com.teste.controledevendas.order.R
import br.com.teste.controledevendas.order.feature.home.state.HomeIntent
import br.com.teste.controledevendas.order.feature.home.viewmodel.HomeViewModel
import br.com.teste.controledevendas.order.mapper.FakeData.fakeOrderList
import br.com.teste.controledevendas.order.mapper.FakeData.fakeOrderWithProductsList
import br.com.teste.controledevendas.order.mapper.OrderDto
import br.com.teste.controledevendas.order.mapper.OrderWithProductsDto
import br.com.teste.controledevendas.order.mapper.ProductDto
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
            navController.navigate("order/add")
        },
        orderList = homeUiState.orderWithProductsList,
        showProgress = homeUiState.loading,
        totalSales = homeUiState.totalSales
    )

}

@Composable
fun OrderContent(
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    onOrderItemClick: (order: OrderDto) -> Unit,
    showProgress: Boolean,
    onAddOrderClick: () -> Unit,
    orderList: List<OrderWithProductsDto>,
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
    orderList: List<OrderWithProductsDto>,
    onOrderItemClick: (order: OrderDto) -> Unit
) {
    LazyColumn() {
        items(orderList) {
            OrderItem(order = it.orderDto, onOrderItemClick = onOrderItemClick)
            Divider(color = Color.Black, thickness = 1.dp)
        }
    }
}

@Composable
fun OrderItem(
    order: OrderDto,
    onOrderItemClick: (order: OrderDto) -> Unit
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
