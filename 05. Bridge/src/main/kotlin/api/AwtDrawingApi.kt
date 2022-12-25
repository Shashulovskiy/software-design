package api

import java.awt.Color
import java.awt.Frame
import java.awt.Graphics2D
import java.awt.event.WindowAdapter
import java.awt.event.WindowEvent
import java.awt.geom.Ellipse2D
import kotlin.system.exitProcess

class AwtDrawingApi: DrawingApi, Frame() {

    init {
        addWindowListener(object : WindowAdapter() {
            override fun windowClosing(e: WindowEvent?) {
                super.windowClosing(e)
                exitProcess(0)
            }
        })
        setSize(getDrawingAreaWidth().toInt(), getDrawingAreaHeight().toInt())
        isVisible = true
    }

    fun render() {
        circles.forEach { drawCircle(it.first, it.second) }
        lines.forEach { drawLine(it.first, it.second) }
    }

    override fun getDrawingAreaWidth(): Long = AwtDrawingApi.width


    override fun getDrawingAreaHeight(): Long = AwtDrawingApi.height

    override fun drawCircle(center: Pair<Double, Double>, radius: Double) {
        val g = graphics as Graphics2D
        g.color = Color.blue
        g.fill(
            Ellipse2D.Float(
                center.first.toFloat(),
                center.second.toFloat(),
                radius.toFloat() * 2,
                radius.toFloat() * 2
            )
        )
    }

    override fun drawLine(begin: Pair<Double, Double>, end: Pair<Double, Double>) {
        val g = graphics as Graphics2D
        g.drawLine(begin.first.toInt(), begin.second.toInt(), end.first.toInt(), end.second.toInt())
    }

    companion object {
        const val width = 1000L
        const val height = 1000L
        var circles: MutableList<Pair<Pair<Double, Double>, Double>> = mutableListOf()
        var lines: MutableList<Pair<Pair<Double, Double>, Pair<Double, Double>>> = mutableListOf()
    }
}