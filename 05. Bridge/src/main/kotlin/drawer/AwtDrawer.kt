package drawer

import api.AwtDrawingApi

class AwtDrawer: Drawer {
    override fun getDrawingAreaWidth(): Long = AwtDrawingApi.width

    override fun getDrawingAreaHeight(): Long = AwtDrawingApi.height

    override fun drawCircle(center: Pair<Double, Double>, radius: Double) {
        AwtDrawingApi.circles.add(Pair(center, radius))
    }

    override fun drawLine(begin: Pair<Double, Double>, end: Pair<Double, Double>) {
        AwtDrawingApi.lines.add(Pair(begin, end))
    }

    override fun draw() {
        AwtDrawingApi().render()
    }
}