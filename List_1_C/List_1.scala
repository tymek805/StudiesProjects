object List_1 {
  def flatten[T](list : List[List[T]]):List[T] = {
    if (list != null && list.nonEmpty) {
      list.head ++ flatten(list.tail)
    } else Nil
  }

  def count[T](x : T, list : List[T]):Int = {
    if (list != null && list.nonEmpty) {
      val ooz = if (x == list.head) 1 else 0
      ooz + count(x, list.tail)
    } else 0
  }

  def replicate[T](x : T, number : Int):List[T] = {
    if (number > 0) {
      List(x) ++ replicate(x, number - 1)
    } else Nil
  }

  def sqrList(list : List[Int]):List[Int] = {
    if (list != null && list.nonEmpty) {
      List(list.head*list.head) ++ sqrList(list.tail)
    } else Nil
  }

  def palindrome[T](list : List[T]):Boolean = {
    if (list == null) true
    else list == list.reverse
  }

  def listLength[T](list :List[T]):Int = {
    if (list != null && list.nonEmpty) {
      1 + listLength(list.tail)
    } else 0
  }
}