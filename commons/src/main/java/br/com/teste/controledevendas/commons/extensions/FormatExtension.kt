package br.com.teste.controledevendas.commons.extensions

import java.text.SimpleDateFormat
import java.util.*

fun Double.toMoneyString(): String {
    return String.format("%.2f", this)
}

fun Date.toDateFormatString(pattern: String): String {
    val simpleDateFormat = SimpleDateFormat(pattern, Locale.getDefault())
    return simpleDateFormat.format(this)
}

fun Long.timeMillisToDateFormatString(pattern: String): String {
    val simpleDateFormat = SimpleDateFormat(pattern, Locale.getDefault())
    return simpleDateFormat.format(Date(this))
}