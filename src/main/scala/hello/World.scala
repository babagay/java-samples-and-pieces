package hello;

class Companion {
 def hello() { println("Hello (class)") } // [1]
}

object World extends Companion {
  def hallo() { println("Hallo (object)") } // [2]
  override def hello() { println("Hello (object)") } // [3]



  println( hello() )
}

