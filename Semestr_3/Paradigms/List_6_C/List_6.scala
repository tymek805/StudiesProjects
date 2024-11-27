object List_6 {
  def whileLoop(condition: =>Boolean)(expression: =>Any): Unit = {
    if (condition) {
      expression
      whileLoop(condition)(expression)
    }
  }
  
  def swap(tab: Array[Int], i: Int, j: Int): Unit = {
    val tmp = tab(i)
    tab(i) = tab(j)
    tab(j) = tmp
  }
  
  def partition(tab: Array[Int], l: Int, r: Int): (Int, Int) = {
    var i = l
    var j = r
    val pivot = tab((i+j)/2)

    while (i <= j) {
      while (tab(i) < pivot) { i = i + 1 }
      while (tab(j) > pivot) { j = j - 1 }

      if (i <= j) {
        swap(tab, i, j)
        i = i + 1
        j = j - 1
      }
    }
    (i, j)
  }
  
  def quick(tab: Array[Int], l: Int, r: Int) : Unit = {
    if (l < r) {
      val (i, j) = partition(tab, l, r)

      if (j-l < r-i) {
        quick(tab, i, r)
      } else {
        quick(tab, l, j)
      }
    }
  }
  
  def quicksort(tab: Array[Int]): Unit = {
    quick(tab, 0, tab.length-1)
  }

  def main(args: Array[String]): Unit = {
    var i = 0
    whileLoop(i<3) {
      i = i + 1
      println(i)
    }

    val arr = Array[Int](9,8,7,6,5,5,4,3,2,1)
    println(arr.toList)
    quicksort(arr)
    println(arr.toList)
  }
}

