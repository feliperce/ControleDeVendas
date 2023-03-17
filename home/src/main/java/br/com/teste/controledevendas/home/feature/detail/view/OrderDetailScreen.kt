package br.com.teste.controledevendas.home.feature.detail.view

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
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
import br.com.teste.controledevendas.design.theme.TextSizeLarge
import br.com.teste.controledevendas.design.theme.TextSizeSmall
import br.com.teste.controledevendas.home.R
import br.com.teste.controledevendas.home.feature.detail.state.OrderDetailIntent
import br.com.teste.controledevendas.home.feature.detail.viewmodel.OrderDetailViewModel
import org.koin.androidx.compose.getViewModel
import java.util.*

@Composable
fun OrderDetailScreen(
    navController: NavHostController = rememberNavController(),
    orderDetailViewModel: OrderDetailViewModel = getViewModel(),
    orderId: Long
) {

    val context = LocalContext.current

    val scaffoldState = rememberScaffoldState()
    val orderDetailUiState by orderDetailViewModel.orderDetailState.collectAsState()

    orderDetailViewModel.sendIntent(OrderDetailIntent.GetAllOrdersWithProductsByOrderId(orderId))

    orderDetailUiState.error.let { error ->
        if (error != ErrorType.NONE) {
            val errorMsg = stringResource(id = R.string.order_detail_error_generic)

            LaunchedEffect(Unit) {
                scaffoldState.snackbarHostState.showSnackbar(message = errorMsg)
                orderDetailUiState.error = ErrorType.NONE
            }
        }
    }

    if (orderDetailUiState.isRemoved) {
        Toast.makeText(context, R.string.order_detail_removal, Toast.LENGTH_LONG).show()
        navController.popBackStack()
    }

    orderDetailUiState.orderWithProducts?.let {
        OrderDetailContent(
            showProgress = orderDetailUiState.loading,
            order = it,
            onRemoveButtonClick = { orderWithProducts ->
                orderDetailViewModel.sendIntent(
                    OrderDetailIntent.RemoveOrderWithProducts(orderWithProducts)
                )
            },
            onBackButtonClick = {
                navController.popBackStack()
            }
        )
    } ?: run {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize(),
        ) {
            Text(text = stringResource(id = R.string.order_detail_empty))
        }
    }
}

@Composable
fun OrderDetailContent(
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    showProgress: Boolean,
    order: OrderWithProducts,
    onRemoveButtonClick: (orderWithProducts: OrderWithProducts) -> Unit,
    onBackButtonClick: () -> Unit
) {

    var openRemoveDialogState by remember { mutableStateOf(false) }

    if (openRemoveDialogState) {
        RemoveDialog(
            onDismiss = { openRemoveDialogState = false },
            orderWithProducts = order,
            onRemoveButtonClick = onRemoveButtonClick
        )
    }

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            DefaultAppBar(
                title = stringResource(id = R.string.sale_detail_title, order.order.id),
                subTitle = order.order.client,
                actions = {
                    TopMenu(
                        onRemoveButtonClick = {
                            openRemoveDialogState = true
                        }
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { onBackButtonClick() }) {
                        Icon(Icons.Filled.ArrowBack,null)
                    }
                }
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

            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                ProductItemList(
                    productList = order.products
                )
            }
        }
    )
}

@Composable
fun ProductItem(product: ProductEntity) {
    Column(
        modifier = Modifier.padding(MarginPaddingSizeMedium)
    ) {
        Text(
            text = product.name
        )
        CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.disabled) {
            Text(
                text = product.description,
                fontSize = TextSizeSmall
            )
        }

        Text(modifier = Modifier
            .padding(top = MarginPaddingSizeMedium)
            .fillMaxWidth(),
            text = buildAnnotatedString {
                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                    append(stringResource(id = R.string.sale_detail_qt))
                }
                append(
                    stringResource(id = R.string.sale_detail_qt_unity, product.qt, product.price)
                )
            }
        )
        Text(modifier = Modifier
            .fillMaxWidth(),
            text = buildAnnotatedString {
                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                    append(stringResource(id = R.string.sale_detail_total))
                }
                append(
                    stringResource(id = R.string.sale_detail_total_result, "10540.32")
                )
            }
        )
    }
}

@Composable
fun ProductItemList(productList: List<ProductEntity>) {
    LazyColumn() {
        item {
            Text(
                modifier = Modifier.padding(MarginPaddingSizeMedium),
                text = stringResource(id = R.string.sale_detail_product),
                fontSize = TextSizeLarge
            )

        }
        items(productList) {
            ProductItem(product = it)
            Divider(color = Color.Black, thickness = 1.dp)
        }
    }
}

@Composable
fun TopMenu(
    onRemoveButtonClick: () -> Unit
) {
    Row {
        IconButton(
            onClick = { onRemoveButtonClick() },
            content = {
                Icon(
                    Icons.Default.Delete,
                    contentDescription = null
                )
            }
        )

    }
}

@Composable
fun RemoveDialog(
    onDismiss: () -> Unit,
    orderWithProducts: OrderWithProducts,
    onRemoveButtonClick: (orderWithProducts: OrderWithProducts) -> Unit
) {

    AlertDialog(
        onDismissRequest = { onDismiss() },
        confirmButton = {
            TextButton(
                onClick = {
                    onRemoveButtonClick(orderWithProducts)
                    onDismiss()
                },
                content = {
                    Text(text = stringResource(id = R.string.sale_detail_remove_positive_button))
                }
            )
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onDismiss()
                },
                content = {
                    Text(text = stringResource(id = R.string.sale_detail_remove_negative_button))
                }
            )
        },
        title = {
            Text(text = stringResource(id = R.string.sale_detail_remove_dialog_title))
        }
    )
}

@Composable
@Preview(showBackground = true)
fun ProductItemPreview() {
    ProductItem(fakeProductList[0])
}

@Composable
@Preview(showBackground = true)
fun ProductItemListPreview() {
    ProductItemList(fakeProductList)
}

@Composable
@Preview(showBackground = true)
fun OrderDetailContentPreview() {
    OrderDetailContent(
        showProgress = true,
        order = fakeOrderWithProductsList[0],
        onRemoveButtonClick = {},
        onBackButtonClick = {}
    )
}

@Composable
@Preview(showBackground = true)
fun RemoveDialogPreview() {
    RemoveDialog(
        onDismiss = {  },
        orderWithProducts = fakeOrderWithProductsList[0],
        onRemoveButtonClick = {}
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

