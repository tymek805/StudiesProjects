object Exercise1 {
  def accumulateTail[A](function: (A, A) => A, list: List[A], accumulator: A): A = {
    list.foldRight(accumulator)(function)
  }

  def accumulateHead[A](function: (A, A) => A, list: List[A], accumulator: A): A = {
    list.foldLeft(accumulator)(function)
  }

  def main(args: Array[String]): Unit = {
    val sumResult = accumulateTail((x: Int, y: Int) => y - x, List(1, 2, 3, 4, 5), 0)
    val subResult = accumulateHead((x: Int, y: Int) => x - y, List(1, 2, 3, 4, 5), 0)
    val productResult = accumulateTail((x: Int, y: Int) => x * y, List(1, 2, 3, 4, 5), 1)

    println(s"SubtractT: $sumResult")
    println(s"SubtractH: $subResult")
    println(s"Iloczyn: $productResult")
  }
}
