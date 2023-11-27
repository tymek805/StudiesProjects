object List_3 {
  def sumProdd(xs:List[Int]): (Int,Int) = {
    xs.foldLeft(0, 1)((accM:(Int, Int), n:Int) => (n+accM._1, n*accM._2))
  }

  def insertSort(xs:List[Int], f: (Int, Int) => Boolean): List[Int] = {
    def insert(xs: List[Int], x: Int): List[Int] = {
      xs match {
      case Nil => List(x)
      case h :: t if f(x,h) => h :: insert(t, x)
      case h :: t => x :: h :: t
      }
    }
    xs.foldLeft(List.empty[Int])(insert)
  }

  def mergeSort(xs: List[Int], f: (Int, Int) => Boolean): List[Int] = {
    val splitted = xs.foldLeft(List.empty[List[Int]])((acc:List[List[Int]], x:Int) => (List(x)::acc))
    def putInList(a: List[Int], b: List[Int], acc: List[Int]): List[Int] = {
      (a, b) match {
        case (Nil, x) => acc.reverse ++ x
        case (x, Nil) => acc.reverse ++ x
        case (e, r) => if (!f(e.head,r.head)) { putInList(e, r.tail, r.head::acc) } else { putInList(e.tail, r, e.head::acc) }
        case _ => throw new Exception("putInList match error")
      }
    }
    def merge(xs: List[List[Int]]): List[Int] = {
      xs match {
        case a::b::Nil => putInList(a, b, List.empty[Int])
        case a::b::t => merge(List(putInList(a, b, List.empty[Int]),merge(t)))
        case _ => throw new Exception("Pusta lista")
      }
    }
    merge(splitted)
  }
}
