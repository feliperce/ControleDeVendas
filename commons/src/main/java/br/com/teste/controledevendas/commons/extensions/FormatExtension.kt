package br.com.teste.controledevendas.commons.extensions

fun Double.toMoneyString(): String {
    return String.format("%.2f", this)
}