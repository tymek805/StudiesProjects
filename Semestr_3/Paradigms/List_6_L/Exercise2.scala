trait Debug {
  def debugVars(): Unit = {
    val fields = this.getClass.getDeclaredFields

    for (field <- fields) {
      field.setAccessible(true)
      val fieldName = field.getName
      val fieldType = field.getType.getName
      val fieldValue = field.get(this)

      println(s"Pole: $fieldName => $fieldType, $fieldValue")
    }
  }
}

class Point(xv: Int, yv: Int) extends Debug {
  var x: Int = xv
  var y: Int = yv
  var a: String = "test"
}

object Main extends App {
  var p: Point = new Point(3, 4)
  p.debugVars()
}

