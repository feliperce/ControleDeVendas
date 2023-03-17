package br.com.teste.controledevendas.order.feature.addorder.view

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
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import br.com.teste.controledevendas.data.handler.ErrorType
import br.com.teste.controledevendas.data.local.entity.OrderWithProducts
import br.com.teste.controledevendas.design.component.DefaultAppBar
import br.com.teste.controledevendas.design.component.ExpandableItem
import br.com.teste.controledevendas.design.theme.Green200
import br.com.teste.controledevendas.design.theme.MarginPaddingSizeMedium
import br.com.teste.controledevendas.design.theme.TextSizeSmall
import br.com.teste.controledevendas.order.R
import br.com.teste.controledevendas.order.feature.addorder.model.FormData
import br.com.teste.controledevendas.order.feature.addorder.state.AddOrderIntent
import br.com.teste.controledevendas.order.feature.addorder.viewmodel.AddOrderViewModel
import br.com.teste.controledevendas.order.mapper.FakeData
import br.com.teste.controledevendas.order.mapper.OrderWithProductsDto
import br.com.teste.controledevendas.order.mapper.ProductDto
import org.koin.androidx.compose.getViewModel

@Composable
fun AddOrderScreen(
    navController: NavHostController = rememberNavController(),
    addOrderViewModel: AddOrderViewModel = getViewModel()
) {

    val context = LocalContext.current

    val scaffoldState = rememberScaffoldState()
    val orderDetailUiState by addOrderViewModel.orderDetailState.collectAsState()

    orderDetailUiState.error.let { error ->
        if (error != ErrorType.NONE) {
            val errorMsg = if (error == ErrorType.INVALID_FORM) {
                stringResource(id = R.string.add_order_form_invalid)
            } else {
                stringResource(id = R.string.order_detail_error_generic)
            }

            LaunchedEffect(Unit) {
                scaffoldState.snackbarHostState.showSnackbar(message = errorMsg)
                orderDetailUiState.error = ErrorType.NONE
            }
        }
    }

    AddOrderContent(
        scaffoldState = scaffoldState,
        showProgress = orderDetailUiState.loading,
        onSaveButtonClick = { orderWithProducts ->

        },
        onBackButtonClick = {
            navController.popBackStack()
        },
        onAddProductClick = {
            addOrderViewModel.sendIntent(
                AddOrderIntent.ValidateForm(it)
            )
        },
        formDataList = orderDetailUiState.formDataList,
        orderTotal = orderDetailUiState.orderTotal
    )
}

@Composable
fun AddOrderContent(
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    showProgress: Boolean,
    formDataList: List<FormData>,
    orderTotal: Double,
    onSaveButtonClick: (orderWithProducts: OrderWithProducts) -> Unit,
    onBackButtonClick: () -> Unit,
    onAddProductClick: (formData: FormData) -> Unit,
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
                subTitle = stringResource(id = R.string.add_order_subtitle, orderTotal),
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
                    onAddProductClick = onAddProductClick,
                    formDataList = formDataList
                )
            }
        }
    )
}

@Composable
fun AddOrderForm(
    onAddProductClick: (formData: FormData) -> Unit,
    formDataList: List<FormData>
) {

    var clientText by remember { mutableStateOf("") }
    var showAddProductDialog by remember { mutableStateOf(false) }

    if (showAddProductDialog) {
        AddProductDialog(
            onAddProductClick = onAddProductClick,
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
            title = stringResource(id = R.string.add_order_product_expandable, formDataList.size),
            backgroundColor = Green200
        ) {
            ProductItemList(
                formDataList = formDataList,
                onAddProductButtonClick = {
                    showAddProductDialog = true
                }
            )
        }
    }
}

@Composable
fun ProductItem(formData: FormData) {
    Column(
        modifier = Modifier.padding(MarginPaddingSizeMedium)
    ) {
        Text(
            text = formData.name
        )
        CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.disabled) {
            Text(
                text = formData.description,
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
                    stringResource(id = R.string.sale_detail_qt_unity, formData.qt, formData.price)
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
    formDataList: List<FormData>,
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
        items(formDataList) {
            ProductItem(formData = it)
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
    onAddProductClick: (formData: FormData) -> Unit,
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
                        FormData(
                            name = nameText,
                            description = descText,
                            qt = qtText,
                            price = priceText
                        )
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



@Composable
@Preview(showBackground = true)
fun ProductItemPreview() {
    ProductItem(fakeFormDataList[0])
}

@Composable
@Preview(showBackground = true)
fun ProductItemListPreview() {
    ProductItemList(fakeFormDataList, {})
}

@Composable
@Preview(showBackground = true)
fun AddOrderFormPreview() {
    AddOrderForm(
        onAddProductClick = {},
        formDataList = fakeFormDataList
    )
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
        onAddProductClick = {},
        onDismiss = {}
    )
}

private val fakeFormData = FormData(
    name = "Nameee",
    description = "descrrr",
    qt = "5",
    price = "1423"
)

private val fakeFormDataList = listOf<FormData>(
    fakeFormData,
    fakeFormData,
    fakeFormData,
    fakeFormData
)