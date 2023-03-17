package br.com.teste.controledevendas.order.feature.detail.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.teste.controledevendas.data.handler.Resource
import br.com.teste.controledevendas.data.local.entity.OrderWithProducts
import br.com.teste.controledevendas.order.feature.detail.repository.OrderDetailRepositoryImpl
import br.com.teste.controledevendas.order.feature.detail.state.OrderDetailIntent
import br.com.teste.controledevendas.order.feature.detail.state.OrderDetailUiState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class OrderDetailViewModel(
    private val orderDetailRepositoryImpl: OrderDetailRepositoryImpl
): ViewModel() {

    private val intentChannel = Channel<OrderDetailIntent>(Channel.UNLIMITED)

    private val _orderDetailState = MutableStateFlow(OrderDetailUiState(loading = false))
    val orderDetailState: StateFlow<OrderDetailUiState> = _orderDetailState.asStateFlow()

    init {
        handleIntents()
    }

    fun sendIntent(intent: OrderDetailIntent) {
        viewModelScope.launch {
            intentChannel.send(intent)
        }
    }

    private fun handleIntents() {
        intentChannel
            .consumeAsFlow()
            .onEach { intent ->
                when(intent) {
                    is OrderDetailIntent.GetAllOrdersWithProductsByOrderId -> {
                        getAllOrdersWithProducts(intent.orderId)
                    }
                    is OrderDetailIntent.RemoveOrderWithProducts -> {
                        removeOrderWithProducts(intent.orderWithProducts)
                    }
                }
            }.launchIn(viewModelScope)
    }

    private fun getAllOrdersWithProducts(orderId: Long) {
        viewModelScope.launch {
            orderDetailRepositoryImpl.getOrderWithProductsByOrderId(orderId).collect { res ->
                when (res) {
                    is Resource.Success -> {
                        _orderDetailState.update {
                            it.copy(
                                orderWithProducts = res.data
                            )
                        }
                    }
                    is Resource.Loading -> {
                        _orderDetailState.update {
                            it.copy(loading = it.loading)
                        }
                    }
                    is Resource.Error -> {
                        _orderDetailState.update {
                            it.copy(error = it.error)
                        }
                    }
                }
            }
        }
    }

    private fun removeOrderWithProducts(orderWithProducts: OrderWithProducts) {
        viewModelScope.launch {
            orderDetailRepositoryImpl.removeOrderWithProducts(orderWithProducts).collect { res ->
                when (res) {
                    is Resource.Success -> {
                        _orderDetailState.update {
                            it.copy(isRemoved = true)
                        }
                    }
                    is Resource.Loading -> {
                        _orderDetailState.update {
                            it.copy(loading = it.loading)
                        }
                    }
                    is Resource.Error -> {
                        _orderDetailState.update {
                            it.copy(error = it.error)
                        }
                    }
                }
            }
        }
    }

}