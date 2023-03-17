package br.com.teste.controledevendas.order.feature.addorder.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.teste.controledevendas.data.handler.ErrorType
import br.com.teste.controledevendas.order.R
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

    private val _orderDetailState = MutableStateFlow(AddOrderUiState(loading = false))
    val orderDetailState: StateFlow<AddOrderUiState> = _orderDetailState.asStateFlow()

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
                    is AddOrderIntent.AddOrderWithProducts -> {

                    }
                }
            }.launchIn(viewModelScope)
    }

    private fun valiteFormAndUpdateUi(
        formData: FormData
    ) {
        if (validateForm(formData)) {
            _orderDetailState.update {
                it.copy(
                    error = ErrorType.NONE
                )
            }
        } else {
            _orderDetailState.update {
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