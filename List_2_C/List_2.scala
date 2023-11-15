object List_2 {
  def fibA(n: Int): Int = {
    n match {
      case 0 => 0
      case 1 => 1
      case _ => fibA(n - 1) + fibA(n - 2)
    }
  }

  def fibB(n: Int): Int = {
    def fibIn(n: Int, p: Int, s: Int): Int = {
      n match {
        case 0 => p
        case 1 => s
        case _ => fibIn(n - 1, s, p + s)
      }
    }
    fibIn(n, 0, 1)
  }
  
  def root3(a: Double, e: Double): Double = {
    def fabs(a: Double): Double = {
      if (a >= 0) a
      else -a
    }
    def cbrtIn(a: Double, i: Int, s: Double, e: Double): Double = {
      if (fabs(s*s*s-a) < e*fabs(a)) {
        s
      } else {
        i match {
          case 0 if a > 1 => cbrtIn(a, i+1, a/3, e)
          case 0 => cbrtIn(a, i+1, a, e)
          case _ => cbrtIn(a, i+1, s + (a/(s*s)-s)/3, e)
        }
      }
    }
    cbrtIn(a, 0, 0, e)
  }

  def matchingA[T](a:List[T]): Unit = {
    val List(_, _, x, _, _) = a
  }
  
  def matchingB[T](a:List[T]): Unit = {
    val List(_, (x, _)) = a
  }

  def initSegment[T](l: (List[T], List[T])): Boolean ={
    l match {
      case (Nil, _) => true
      case (_, Nil) => false
      case (x, y) => if (x.head == y.head) initSegment(x.tail, y.tail) else false
    }
  }

  def replaceNth[T](list :List[T], pos: Int, elem: T): List[T] = {
    def replace[T](list :List[T], pos: Int, elem: T, index: Int, ret:List[T]): List[T] = {
      if (pos == index) {
        ret.reverse++(elem::list.tail)
      } else {
        replace(list.tail, pos, elem, index + 1, list.head :: ret)
      }
    }
    replace(list, pos, elem, 0, Nil)
  }
}