package be.inniger.advent.util

fun <T> List<T>.head() = this.first()

fun <T> List<T>.tail() = this.subList(1, this.size)

fun CharSequence.head() = this.first()

fun CharSequence.tail() = this.substring(1, this.length)
