package br.com.teste.controledevendas.order.feature.addorder.view

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Save
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.text.isDigitsOnly
import br.com.teste.controledevendas.data.local.entity.OrderWithProducts
import br.com.teste.controledevendas.data.local.entity.ProductEntity
import br.com.teste.controledevendas.design.component.DefaultAppBar
import br.com.teste.controledevendas.design.component.ExpandableItem
import br.com.teste.controledevendas.design.theme.Green200
import br.com.teste.controledevendas.design.theme.MarginPaddingSizeMedium
import br.com.teste.controledevendas.design.theme.TextSizeLarge
import br.com.teste.controledevendas.design.theme.TextSizeSmall
import br.com.teste.controledevendas.order.R
import br.com.teste.controledevendas.order.mapper.FakeData
import br.com.teste.controledevendas.order.mapper.OrderWithProductsDto
import br.com.teste.controledevendas.order.mapper.ProductDto

/*@Composable
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
        orderDetailUiState.isRemoved = false
        navController.popBackStack()
    }

    orderDetailUiState.orderWithProducts?.let {
        AddOrderContent(
            showProgress = orderDetailUiState.loading,
            order = it,
            onSaveButtonClick = { orderWithProducts ->
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
}*/

@Composable
fun AddOrderContent(
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    showProgress: Boolean,
    onSaveButtonClick: (orderWithProducts: OrderWithProducts) -> Unit,
    onBackButtonClick: () -> Unit
) {

    var onSaveDialogState by remember { mutableStateOf(false) }

    if (onSaveDialogState) {
        /*SaveDialog(
            onDismiss = { onSaveDialogState = false },
            orderWithProducts = order,
            onSaveButtonClick = onSaveButtonClick
        )*/
    }

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            DefaultAppBar(
                title = stringResource(id = R.string.add_order_title),
                actions = {
                    TopMenu(
                        onSaveButtonClick = {
                            onSaveDialogState = true
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
                AddOrderForm(
                    onAddProductClick = { name, description, qt, price ->  
                        
                    }
                )
            }
        }
    )
}

@Composable
fun AddOrderForm(
    onAddProductClick: (name: String, description: String, qt: String, price: String) -> Unit
) {

    var clientText by remember { mutableStateOf("") }
    var productList by remember { mutableStateOf(listOf<ProductDto>()) }
    var showAddProductDialog by remember { mutableStateOf(false) }

    if (showAddProductDialog) {
        AddProductDialog(
            onAddProductClick = { name, description, qt, price ->  
                                
            },
            onDismiss = { showAddProductDialog = false }
        )
    }

    Column(
        modifier = Modifier.padding(MarginPaddingSizeMedium)
    ) {
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = MarginPaddingSizeMedium),
            value = clientText,
            onValueChange = { clientText = it },
            label = {
                Text(
                    text = stringResource(id = R.string.add_order_client_tf)
                )
            }
        )
        
        ExpandableItem(
            modifier = Modifier.padding(top = MarginPaddingSizeMedium),
            title = stringResource(id = R.string.add_order_product_expandable, productList.size),
            backgroundColor = Green200
        ) {
            ProductItemList(
                productList = productList,
                onAddProductButtonClick = {
                    showAddProductDialog = true
                }
            )
        }
    }
}

@Composable
fun ProductItem(product: ProductDto) {
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
fun ProductItemList(
    productList: List<ProductDto>,
    onAddProductButtonClick: () -> Unit
) {
    LazyColumn() {
        item {
            Button(
                onClick = { onAddProductButtonClick() }
            ) {
                Text(
                    text = stringResource(id = R.string.add_order_product_button)
                )
            }
        }
        items(productList) {
            ProductItem(product = it)
            Divider(color = Color.Black, thickness = 1.dp)
        }
    }
}

@Composable
fun TopMenu(
    onSaveButtonClick: () -> Unit
) {
    Row {
        IconButton(
            onClick = { onSaveButtonClick() },
            content = {
                Icon(
                    Icons.Default.Save,
                    contentDescription = null
                )
            }
        )

    }
}

@Composable
fun SaveDialog(
    onDismiss: () -> Unit,
    orderWithProducts: OrderWithProductsDto,
    onSaveButtonClick: (orderWithProducts: OrderWithProductsDto) -> Unit
) {

    AlertDialog(
        onDismissRequest = { onDismiss() },
        confirmButton = {
            TextButton(
                onClick = {
                    onSaveButtonClick(orderWithProducts)
                    onDismiss()
                },
                content = {
                    Text(text = stringResource(id = R.string.add_order_save_positive_button))
                }
            )
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onDismiss()
                },
                content = {
                    Text(text = stringResource(id = R.string.add_order_save_negative_button))
                }
            )
        },
        title = {
            Text(text = stringResource(id = R.string.add_order_save_dialog_title))
        }
    )
}

@Composable
private fun AddProductDialog(
    onAddProductClick: (name: String, description: String, qt: String, price: String) -> Unit,
    onDismiss: () -> Unit
) {
    /*val name: String,
    val description: String,
    val qt: Int,
    val price: Double,*/

    val context = LocalContext.current

    var nameText by remember { mutableStateOf("") }
    var descText by remember { mutableStateOf("") }
    var qtText by remember { mutableStateOf("") }
    var priceText by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = { onDismiss() },
        confirmButton = {
            TextButton(
                onClick = {
                    onAddProductClick(
                        nameText,
                        descText,
                        qtText,
                        priceText
                    )
                    onDismiss()
                }
            ) {
                Text(text = stringResource(id = R.string.add_order_add_dialog_positive_button))
            }
        },
        dismissButton = {
            TextButton(onClick = { onDismiss() }) {
                Text(text = stringResource(id = R.string.add_order_add_dialog_negative_button))
            }
        },
        title = { Text(text = stringResource(id = R.string.add_order_add_dialog_title)) },
        text = {
            Column {

                Text(
                    text = stringResource(id = R.string.add_order_add_dialog_msg)
                )

                TextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = MarginPaddingSizeMedium),
                    value = nameText,
                    onValueChange = {
                        nameText = it
                    },
                    label = { Text(text = stringResource(id = R.string.add_order_add_dialog_name_tf)) },
                    singleLine = true
                )

                TextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = MarginPaddingSizeMedium),
                    value = descText,
                    onValueChange = { descText = it },
                    label = { Text(text = stringResource(id = R.string.add_order_add_dialog_desc_tf)) }
                )

                TextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = MarginPaddingSizeMedium),
                    value = qtText,
                    onValueChange = {
                        qtText = if (it.isDigitsOnly()) {
                            it
                        } else {
                            qtText
                        }
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Done),
                    label = {
                        Text(
                            text = stringResource(id = R.string.add_order_add_dialog_qt_tf)
                        )
                    },
                    singleLine = true
                )

                TextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = MarginPaddingSizeMedium),
                    value = priceText,
                    onValueChange = {
                        priceText = when (it.toDoubleOrNull()) {
                            null -> priceText
                            else -> it
                        }
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Done),
                    label = {
                        Text(
                            text = stringResource(id = R.string.add_order_add_dialog_price_tf)
                        )
                    },
                    singleLine = true
                )


            }
        }
    )
}

private fun validateForm(
    context: Context,
    nameText: String,
    qtText: String,
    priceText: String
): Boolean {
    val isValid: Boolean

    if (nameText.isEmpty()) {
        Toast.makeText(
            context,
            R.string.add_order_add_dialog_name_tf_error,
            Toast.LENGTH_LONG
        ).show()
        isValid = false
    } else {
        if (qtText.isEmpty()) {
            Toast.makeText(
                context,
                R.string.add_order_add_dialog_qt_error,
                Toast.LENGTH_LONG
            ).show()

            isValid = false
        } else {
            isValid = if (priceText.isEmpty()) {
                Toast.makeText(
                    context,
                    R.string.add_order_add_dialog_price_error,
                    Toast.LENGTH_LONG
                ).show()
                false
            } else {
                true
            }
        }
    }

    return isValid
}

@Composable
@Preview(showBackground = true)
fun ProductItemPreview() {
    ProductItem(FakeData.fakeProductList[0])
}

@Composable
@Preview(showBackground = true)
fun ProductItemListPreview() {
    ProductItemList(FakeData.fakeProductList, {})
}

@Composable
@Preview(showBackground = true)
fun AddOrderFormPreview() {
    AddOrderForm { _, _, _, _ -> }
}

/*@Composable
@Preview(showBackground = true)
fun OrderDetailContentPreview() {
    AddOrderContent(
        showProgress = true,
        order = FakeData.fakeOrderWithProductsList[0],
        onSaveButtonClick = {},
        onBackButtonClick = {}
    )
}*/

@Composable
@Preview(showBackground = true)
fun RemoveDialogPreview() {
    SaveDialog(
        onDismiss = {  },
        orderWithProducts = FakeData.fakeOrderWithProductsList[0],
        onSaveButtonClick = {}
    )
}

@Composable
@Preview(showBackground = true)
fun AddProductDialogPreview() {
    AddProductDialog(
        onAddProductClick = {_,_,_,_ -> },
        onDismiss = {}
    )
}