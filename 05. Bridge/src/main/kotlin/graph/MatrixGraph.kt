package graph

import drawer.Drawer
import java.util.*

class MatrixGraph(val drawer: Drawer): Graph(drawer) {
    private var n = 0
    private var m = 0
    private var edges: MutableList<Pair<Int, Int>> = mutableListOf()

    override fun drawGraph() {
        Helper.drawGraph(n, m, edges, drawer)
    }

    override fun readGraph(sc: Scanner) {
        n = sc.nextInt()
        m = 0

        for (i in 0 until n) {
            for (j in 0 until n) {
                when (sc.nextInt()) {
                    0 -> continue
                    1 -> {
                        edges.add(i to j)
                        m++
                    }
                    else -> throw IllegalArgumentException("Incorrect input")
                }
            }
        }
    }
}