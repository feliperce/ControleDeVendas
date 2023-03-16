package br.com.teste.controledevendas.home.feature.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.teste.controledevendas.data.handler.Resource
import br.com.teste.controledevendas.data.local.entity.OrderWithProducts
import br.com.teste.controledevendas.data.local.entity.ProductEntity
import br.com.teste.controledevendas.home.feature.home.extensions.sumAllSales
import br.com.teste.controledevendas.home.feature.home.repository.OrderRepository
import br.com.teste.controledevendas.home.feature.home.repository.OrderRepositoryImpl
import br.com.teste.controledevendas.home.feature.home.state.HomeIntent
import br.com.teste.controledevendas.home.feature.home.state.HomeUiState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class HomeViewModel(
    private val orderRepository: OrderRepositoryImpl
) : ViewModel() {

    private val intentChannel = Channel<HomeIntent>(Channel.UNLIMITED)

    private val _homeState = MutableStateFlow(HomeUiState(loading = false))
    val homeState: StateFlow<HomeUiState> = _homeState.asStateFlow()

    init {
        handleIntents()
    }

    fun sendIntent(intent: HomeIntent) {
        viewModelScope.launch {
            intentChannel.send(intent)
        }
    }

    private fun handleIntents() {
        intentChannel
            .consumeAsFlow()
            .onEach { intent ->
                when(intent) {
                    is HomeIntent.GetAllOrdersWithProducts -> {
                        getAllOrdersWithProducts()
                    }
                }
            }.launchIn(viewModelScope)
    }

    private fun getAllOrdersWithProducts() {
        viewModelScope.launch {
            orderRepository.getOrdersWithProducts().collect { res ->
                when (res) {
                    is Resource.Success -> {
                        _homeState.update {
                            it.copy(
                                orderWithProductsList = res.data ?: emptyList(),
                                totalSales = res.data?.sumAllSales() ?: 0.0
                            )
                        }
                    }
                    is Resource.Loading -> {
                        _homeState.update {
                            it.copy(loading = it.loading)
                        }
                    }
                    is Resource.Error -> {
                        _homeState.update {
                            it.copy(error = it.error)
                        }
                    }
                }
            }
        }
    }

}