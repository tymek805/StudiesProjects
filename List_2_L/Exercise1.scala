object Exercise1 {
  def obetnijkonce[T](list: List[T]): List[T] = {
    list match {
      case Nil => Nil
      case _ :: tail => tail.init
    }
  }
}
