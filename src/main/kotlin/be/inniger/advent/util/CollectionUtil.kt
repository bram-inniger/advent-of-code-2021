package be.inniger.advent.util

fun <T> List<T>.head() = this.first()

fun <T> List<T>.tail() = this.subList(1, this.size)

fun CharSequence.head() = this.first()

fun CharSequence.tail() = this.substring(1, this.length)

fun <T> ArrayDeque<T>.push(element: T) = this.addLast(element)

fun <T> ArrayDeque<T>.pop() = this.removeLast()

fun <T> ArrayDeque<T>.peek() = this.lastOrNull()
