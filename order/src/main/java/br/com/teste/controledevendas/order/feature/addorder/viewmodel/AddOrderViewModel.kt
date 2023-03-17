package br.com.teste.controledevendas.order.feature.addorder.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.teste.controledevendas.data.handler.ErrorType
import br.com.teste.controledevendas.data.handler.Resource
import br.com.teste.controledevendas.order.feature.addorder.extensions.sumAllProducts
import br.com.teste.controledevendas.order.feature.addorder.model.FormData
import br.com.teste.controledevendas.order.feature.addorder.repository.AddOrderRepositoryImpl
import br.com.teste.controledevendas.order.feature.addorder.state.AddOrderIntent
import br.com.teste.controledevendas.order.feature.addorder.state.AddOrderUiState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class AddOrderViewModel(
    private val addOrderRepositoryImpl: AddOrderRepositoryImpl
): ViewModel() {

    private val intentChannel = Channel<AddOrderIntent>(Channel.UNLIMITED)

    private val _addOrderState = MutableStateFlow(AddOrderUiState(loading = false))
    val addOrderState: StateFlow<AddOrderUiState> = _addOrderState.asStateFlow()

    init {
        handleIntents()
    }

    fun sendIntent(intent: AddOrderIntent) {
        viewModelScope.launch {
            intentChannel.send(intent)
        }
    }

    private fun handleIntents() {
        intentChannel
            .consumeAsFlow()
            .onEach { intent ->
                when(intent) {
                    is AddOrderIntent.ValidateForm -> {
                        valiteFormAndUpdateUi(formData = intent.formData)
                    }
                    is AddOrderIntent.ValidateOrder -> {
                        validateOrder(intent.clientName)
                    }
                    is AddOrderIntent.AddOrder -> {
                        if (_addOrderState.value.error == ErrorType.NONE) {
                            addOrder(intent.clientName)
                        }
                    }
                }
            }.launchIn(viewModelScope)
    }

    private fun addOrder(clientName: String) {
        viewModelScope.launch {
            addOrderRepositoryImpl.insertOrderWithProducts(
                clientName, _addOrderState.value.formDataList
            ).collect { res ->
                when (res) {
                    is Resource.Success -> {
                        _addOrderState.update {
                            it.copy(isAdded = true)
                        }
                    }
                    is Resource.Loading -> {
                        _addOrderState.update {
                            it.copy(loading = it.loading)
                        }
                    }
                    is Resource.Error -> {
                        _addOrderState.update {
                            it.copy(error = it.error)
                        }
                    }
                }
            }
        }
    }

    private fun validateOrder(clientName: String) {
        if (clientName.isEmpty()) {
            _addOrderState.update {
                it.copy(error = ErrorType.EMPTY_CLIENT_NAME)
            }
        } else if (_addOrderState.value.formDataList.isEmpty()) {
            _addOrderState.update {
                it.copy(error = ErrorType.EMPTY_PRODUCTS)
            }
        } else {
            _addOrderState.update {
                it.copy(error = ErrorType.NONE)
            }
        }
    }

    private fun valiteFormAndUpdateUi(
        formData: FormData
    ) {
        if (validateForm(formData)) {
            _addOrderState.update {
                it.formDataList.add(formData)
                it.copy(
                    error = ErrorType.NONE,
                    orderTotal = it.formDataList.sumAllProducts()
                )
            }
        } else {
            _addOrderState.update {
                it.copy(
                    error = ErrorType.INVALID_FORM
                )
            }
        }
    }

    private fun validateForm(
        formData: FormData
    ): Boolean {
        val isValid: Boolean = if (formData.name.isEmpty()) {
            false
        } else {
            if (formData.qt.isEmpty()) {
                false
            } else {
                formData.price.isNotEmpty()
            }
        }

        return isValid
    }

}