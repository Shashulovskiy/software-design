package graph

import drawer.Drawer
import java.util.Scanner

abstract class Graph(
    /**
     * Bridge to drawing api
     */
    private val drawer: Drawer
) {
    abstract fun drawGraph()
    abstract fun readGraph(sc: Scanner)
}