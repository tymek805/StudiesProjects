trait Debug {
  def debugName(): Unit = {
    val className = this.getClass.getSimpleName
    println(s"Klasa: $className")
  }
}

class Point(xv: Int, yv: Int) extends Debug {
  var x: Int = xv
  var y: Int = yv
  var a: String = "test"
}

object Main extends App {
  var p: Point = new Point(3, 4)
  p.debugName()
}

