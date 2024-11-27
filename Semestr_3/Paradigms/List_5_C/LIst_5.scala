sealed trait lBT[+A]
case object LEmpty extends lBT[Nothing]
case class LNode[+A](elem:A, left:()=>lBT[A], right:()=>lBT[A]) extends lBT[A]

object List_5 {
  def lfrom(n:Int):Stream[Int] = {
    n #:: lfrom(n + 1)
  }

  // Zad 1
  def lrepeat[T](n:Int, s:Stream[T]): Stream[T] = {
    def lrep(n:Int, x:T, streamRest:() => Stream[T]): Stream[T] = {
      if (n > 1) {
        x #:: lrep(n-1, x, streamRest)
      } else {
        x #:: streamRest()
      }
    }

    if (s.isEmpty) {
      Stream.Empty
    } else {
      val x #:: xs = s
      lrep(n, x, () => lrepeat(n, xs))
    }
  }

  // Zad 2
  val lfib = {
    def lfibIn(p:Int, n:Int): Stream[Int] = {
      (p+n) #:: lfibIn(n, (p+n))
    }
    Stream.cons(0,Stream.cons(1,lfibIn(0, 1)))
  }

  def lTree(n:Int):lBT[Int] = {
    LNode(n, () => lTree(2*n), () => lTree(2*n+1))
  }
  
  def bfs[T](ltree:lBT[T]):Stream[T] = {
    def bfsIn(queue:List[lBT[T]]):Stream[T] = {
      queue match {
        case Nil => Stream.Empty
        case LEmpty :: t => bfsIn(t)
        case LNode(v, l, r) :: t => Stream.cons(v, bfsIn(t ++ List(l(),r())))
      }
    }
    bfsIn(List(ltree))
  }
}
