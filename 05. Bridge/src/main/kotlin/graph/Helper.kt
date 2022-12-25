package graph

import drawer.Drawer

object Helper {
    fun drawGraph(n: Int, m: Int, edges: List<Pair<Int, Int>>, drawer: Drawer) {
        val vertices = mutableListOf<Pair<Double, Double>>()

        val multiplier = (drawer.getDrawingAreaWidth() + drawer.getDrawingAreaWidth()) / 6
        val radius = 20.0
        val wOffset = drawer.getDrawingAreaWidth() / 2
        val hOffset = drawer.getDrawingAreaWidth() / 2
        val step = 2 * Math.PI / n

        (0 until n).forEach {
            val x = (multiplier * Math.cos(2 * Math.PI - it * step) + wOffset)
            val y = (multiplier * Math.sin(2 * Math.PI - it * step) + hOffset)
            drawer.drawCircle(Pair(x, y), radius)
            vertices.add(Pair(x + radius, y + radius))
        }

        (0 until m).forEach {
            drawer.drawLine(vertices[edges[it].first], vertices[edges[it].second])
        }

        drawer.draw()
    }
}