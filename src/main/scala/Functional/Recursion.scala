/**
  * https://docs.scala-lang.org/tutorials/scala-for-java-programmers.html
  * https://www.artima.com/pins1ed/combining-scala-and-java.html
  * http://proselyte.net/tutorials/scala/variables/
  * http://www.w3ii.com/ru/scala/scala_quick_guide.html
  *
  * книга
  * https://books.google.com.ua/books?id=PU08DwAAQBAJ&pg=PA43&lpg=PA43&dq=%D0%B2%D1%8B%D0%B7%D0%B2%D0%B0%D1%82%D1%8C+scala-%D0%BA%D0%BE%D0%B4+%D0%B8%D0%B7+java&source=bl&ots=fF5evTS2mk&sig=2WDhoOQg_esFt5gIUUjyuJLMgb8&hl=ru&sa=X&ved=0ahUKEwjA69nJ1OjXAhXRaVAKHW2MC6QQ6AEIPzAE#v=onepage&q=%D0%B2%D1%8B%D0%B7%D0%B2%D0%B0%D1%82%D1%8C%20scala-%D0%BA%D0%BE%D0%B4%20%D0%B8%D0%B7%20java&f=false
  * https://books.google.com.ua/books?id=PU08DwAAQBAJ&pg=PA66&lpg=PA66&dq=scala+%D0%BF%D0%B5%D1%80%D0%B5%D0%BF%D1%80%D0%B8%D1%81%D0%B2%D0%B0%D0%B8%D0%B2%D0%B0%D0%BD%D0%B8%D0%B5+%D0%BF%D0%B0%D1%80%D0%B0%D0%BC%D0%B5%D1%82%D1%80%D0%B0+%D0%BC%D0%B5%D1%82%D0%BE%D0%B4%D0%B0&source=bl&ots=fF5evURakc&sig=sGRFMAZP53kHlfoA1bXGmer-LCs&hl=ru&sa=X&ved=0ahUKEwiLo8L9-OjXAhVNEVAKHUCoBJwQ6AEIKTAB#v=onepage&q=scala%20%D0%BF%D0%B5%D1%80%D0%B5%D0%BF%D1%80%D0%B8%D1%81%D0%B2%D0%B0%D0%B8%D0%B2%D0%B0%D0%BD%D0%B8%D0%B5%20%D0%BF%D0%B0%D1%80%D0%B0%D0%BC%D0%B5%D1%82%D1%80%D0%B0%20%D0%BC%D0%B5%D1%82%D0%BE%D0%B4%D0%B0&f=false
  *
  * wiki
  * https://ru.wikibooks.org/wiki/Scala_%D0%B2_%D0%BF%D1%80%D0%B8%D0%BC%D0%B5%D1%80%D0%B0%D1%85
  *
  * Скала для нетерпеливых
  *
  * TODO
  * [?] Как импортировать скала-код в джаву
  * http://lampwww.epfl.ch/~michelou/scala/using-scala-from-java.html
  */


/**
  * Написать рекурсивную функцию возведения числа-Х в степень-N
  * Даны два целых числа A и В (каждое в отдельной строке). Выведите все числа от A до B включительно, в порядке возрастания
  */

case class Recursion(val name: String) {

  // [!] vals get a method defined for access from Java. You can access the value of the val “foo” via the method “foo()”

  // [!] vars get a method _$eq defined. You can call it like so foo$_eq("newfoo")

  def main(args: Array[String]): Unit = {
  }

  var int = 3

  val i() = 4

  // Каррирование?
  // See https://habrahabr.ru/post/335866/
  def powerRecursively(X: Int, N: Int, Acc: Int): Int = {

    if (N == 0) return 1

    else if( N < 0 ) {
      // todo
    }
    else if ( N > 0 ) {

      // TODO
      // N = N - 1

      // if( --N > 0 )

      // return powerRecursively()
    }

    1
  }

  /**
    * рекурсивную функцию возведения числа-Х в степень-N
    * @param X
    * @param N
    */
  def power(X: Int, N: Int) = {

    powerRecursively(X,N,1)

  }



  object i {
    def apply(): Any = 6

    def unapply(i: Int): Boolean = i == 4
  }

}