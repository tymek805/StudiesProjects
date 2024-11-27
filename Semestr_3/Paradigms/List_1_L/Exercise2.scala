object Exercise2 {
  def calculate(list:List[String]): List[Int] = {
    def pomocnicza(lista: List[String], wynik: List[Int]): List[Int] = lista match {
      case Nil => wynik
      case head :: tail =>
        val nowyWynik = wynik :+ head.length
        pomocnicza(tail, nowyWynik)
    }
    pomocnicza(list, List.empty)
  }

  def main(args: Array[String]): Unit = {
    println(calculate(List("te", "tes", "test")))
    println(calculate(List("t")))
    println(calculate(List("Sprawdzam", "test")))
    println(calculate(List("poprawnosc")))
  }  
}