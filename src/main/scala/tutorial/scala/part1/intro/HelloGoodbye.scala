package tutorial.scala.part1.intro

object HelloGoodbye {
  def main(args: Array[String]): Unit = {
    require(args.length == 2, "Only 2 arguments are allowed")
    println(s"Hello ${args(0)}, ${args(1)}")
    println(s"Goodbye ${args(1)}, ${args(0)}")
  }
}
