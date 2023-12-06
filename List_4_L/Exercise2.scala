object Exercise2 {
  def przeksztalc(points: List[(Float, Float)], transformations: List[(Float, Float) => (Float, Float)]): List[(Float, Float)] = {
    points.map { point =>
      transformations.foldLeft(point) { (currentPoint, transformation) =>
        transformation(currentPoint._1, currentPoint._2)
      }
    }
  }

  def main(args: Array[String]): Unit = {
    val points = List((1.0f, 1.0f), (2.0f, 2.0f))
    val transformations = List(
      (x: Float, y: Float) => (x + 2.0f, y - 2.0f),
      (x: Float, y: Float) => (-1.0f * x, y)
    )

    println(przeksztalc(points, transformations))
  }
}