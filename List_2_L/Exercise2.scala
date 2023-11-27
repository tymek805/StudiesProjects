object Exercise2 {
  def grupuj[A](list: List[A]): List[(A, Int)] = {
    list.foldRight(List.empty[(A, Int)]) { (elem, acc) =>
      acc match {
        case (headElem, count) :: tail if headElem == elem =>
          (elem, count + 1) :: tail
        case _ =>
          (elem, 1) :: acc
      }
    }
  }
}
