package br.com.teste.controledevendas.data.handler

sealed class Resource<T>(
    val data: T? = null,
    val isLoading: Boolean = false,
    val error: ErrorType? = null
) {
    class Success<T>(data: T?) : Resource<T>(data)
    class Loading<T>(isLoading: Boolean) : Resource<T>(isLoading = isLoading)
    class Error<T>(error: ErrorType, data: T? = null) : Resource<T>(error = error, data = data)
}
