package br.com.teste.controledevendas.home.feature.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.teste.controledevendas.home.feature.home.repository.OrderRepository
import br.com.teste.controledevendas.home.feature.home.state.HomeIntent
import br.com.teste.controledevendas.home.feature.home.state.HomeUiState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class HomeViewModel(
    private val orderRepository: OrderRepository
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
                    else -> {}
                }
            }.launchIn(viewModelScope)
    }

    private fun getAllOrdersWithProducts() {

    }

}