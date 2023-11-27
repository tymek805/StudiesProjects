sealed trait BT[A]
  case class Empty[A]() extends BT[A]
  case class Node[A](elem:A, left:BT[A], right:BT[A]) extends BT[A]

sealed trait Graphs[A]
  case class Graph[A](succ: A=>List[A]) extends Graphs[A]


object List_4 {
  val tt = Node[Int](1,
    Node[Int](2,
      Node[Int](4,
        Empty[Int],
        Empty[Int]
      ),
      Empty[Int]
    ),
    Node[Int](3,
      Node[Int](5,
        Empty[Int],
        Node[Int](6,
          Empty[Int],
          Empty[Int]
        )
      ),
      Empty[Int]
    )
  )
  val g = Graph((i: Int) => i match {
    case 0 => List(3)
    case 1 => List(0,2,4)
    case 2 => List(1)
    case 3 => Nil
    case 4 => List(0,2)
    case n => throw new Exception("Graph g: node „ + n" + " doesn't exist")
  })

  // Zad 3
  def breadthBT[T](bt: BT[T]): List[T] = {
    def breadthIn(acc: List[BT[T]], res: List[T]): List[T] = {
      if (acc == Nil) {
        res
      } else {
        acc.head match {
          case Empty() => breadthIn(acc.tail, res)
          case Node(x, l, r) => breadthIn(acc.tail ++ List(l, r), x :: res)
        }
      }
    }
    breadthIn(List(bt), List.empty[T]).reverse
  }

  // Zad 4
  def internalPath(bt: BT[Int]): Int = {
    def internal(n: BT[Int], res: Int, lvl: Int): Int =
      n match {
        case Empty() => res
        case Node(v, l, r) => internal(l, internal(r, res + lvl, lvl + 1), lvl + 1)
      }
    internal(bt, 0, 0)
  }

  def externalPath(bt: BT[Int]) = {
    def extern(n: BT[Int], res: Int, lvl: Int): Int = {
      n match {
        case Empty() => res + lvl
        case Node(v, l, r) => extern(l, extern (r,res, lvl+1), lvl+1)
      }
    }
    extern(bt, 0, 0)
  }

  // Zad 5
  def depthSearch(gr: Graph[Int], st: Int):List[Int] = {
    def dfs(acc:List[Int], res:List[Int]):List[Int] = {
      if (acc == Nil) {
        res
      } else {
        if (res.contains(acc.head)) {
          dfs(acc.tail, res)
        } else {
          dfs((gr succ acc.head)++acc.tail, acc.head::res)
        }
      }
    }
    dfs(List(st), List.empty[Int]).reverse
  }
}
