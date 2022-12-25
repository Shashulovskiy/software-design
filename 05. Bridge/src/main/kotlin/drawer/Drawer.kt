package drawer

interface Drawer {
    fun getDrawingAreaWidth(): Long
    fun getDrawingAreaHeight(): Long
    fun drawCircle(center: Pair<Double, Double>, radius: Double)
    fun drawLine(begin: Pair<Double, Double>, end: Pair<Double, Double>)

    fun draw()
}