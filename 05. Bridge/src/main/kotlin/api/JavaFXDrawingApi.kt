package api

import javafx.application.Application
import javafx.scene.Group
import javafx.scene.Scene
import javafx.scene.canvas.Canvas
import javafx.scene.canvas.GraphicsContext
import javafx.scene.paint.Color
import javafx.stage.Stage
import kotlin.concurrent.thread

class JavaFXDrawingApi(): DrawingApi, Application() {
    private var graphicsContext: GraphicsContext? = null

    override fun start(primaryStage: Stage) {
        val root = Group()
        val canvas = Canvas(width.toDouble(), height.toDouble())

        graphicsContext = canvas.graphicsContext2D
        root.children.add(canvas)

        circles.forEach {
            drawCircle(it.first, it.second)
        }

        lines.forEach {
            drawLine(it.first, it.second)
        }

        primaryStage.scene = Scene(root)
        primaryStage.show()
    }

    override fun getDrawingAreaWidth(): Long {
        return width
    }

    override fun getDrawingAreaHeight(): Long {
        return height
    }

    override fun drawCircle(center: Pair<Double, Double>, radius: Double) {
        graphicsContext?.fill = Color.RED
        graphicsContext?.fillOval(center.first, center.second, 2 * radius, 2 * radius)
    }

    override fun drawLine(begin: Pair<Double, Double>, end: Pair<Double, Double>) {
        graphicsContext?.strokeLine(begin.first, begin.second, end.first, end.second)
    }

    companion object {
        const val width = 1000L
        const val height = 1000L
        var circles: MutableList<Pair<Pair<Double, Double>, Double>> = mutableListOf()
        var lines: MutableList<Pair<Pair<Double, Double>, Pair<Double, Double>>> = mutableListOf()
    }
}