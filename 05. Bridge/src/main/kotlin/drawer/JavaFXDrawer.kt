package drawer

import api.JavaFXDrawingApi
import javafx.application.Application
import kotlin.concurrent.thread

class JavaFXDrawer: Drawer {
    override fun getDrawingAreaWidth(): Long = JavaFXDrawingApi.width

    override fun getDrawingAreaHeight(): Long = JavaFXDrawingApi.height

    override fun drawCircle(center: Pair<Double, Double>, radius: Double) {
        JavaFXDrawingApi.circles.add(Pair(center, radius))
    }

    override fun drawLine(begin: Pair<Double, Double>, end: Pair<Double, Double>) {
        JavaFXDrawingApi.lines.add(Pair(begin, end))
    }

    override fun draw() {
        JavaFXDrawingApi()
        thread {
            Application.launch(JavaFXDrawingApi::class.java)
        }
    }
}