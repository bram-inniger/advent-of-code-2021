package be.inniger.advent.util

class UnionFind(nrElements: Int) {

    private val parents = (0 until nrElements).toList().toIntArray()
    private val sizes = IntArray(nrElements) { 1 }

    tailrec fun find(el: Int): Int =
        if (el == parents[el]) el
        else find(parents[el])

    fun union(el1: Int, el2: Int) {
        val rootEl1 = find(el1)
        val rootEl2 = find(el2)

        when {
            rootEl1 == rootEl2 -> {}
            sizes[rootEl1] < sizes[rootEl2] -> {
                parents[rootEl1] = rootEl2
                sizes[rootEl2] += sizes[rootEl1]
            }
            else -> {
                parents[rootEl2] = rootEl1
                sizes[rootEl1] += sizes[rootEl2]
            }
        }
    }

    fun getSizes() = sizes
}
