import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

import scala.io.Source
import scala.jdk.CollectionConverters.IterableHasAsScala

class SolverSpec extends AnyWordSpec with Matchers {
  "When solver is given 3X3 board, then it" should {
    val goal = new Board(Array(Array(1, 2, 3), Array(4, 5, 6), Array(7, 8, 0)))
    "return 1 move solution" in {
      val initial = new Board(Array(Array(1, 2, 3), Array(4, 5, 6), Array(7, 0, 8)))
      val solver = new Solver(initial)
      solver.isSolvable shouldBe true
      solver.moves() shouldBe 1
      solver.solution().asScala.toList shouldBe List(initial, goal)
    }

    "return 2 moves solution" in {
      val initial = new Board(Array(Array(1, 2, 3), Array(4, 0, 6), Array(7, 5, 8)))
      val intermediate = new Board(Array(Array(1, 2, 3), Array(4, 5, 6), Array(7, 0, 8)))
      val solver = new Solver(initial)
      solver.isSolvable shouldBe true
      solver.moves() shouldBe 2
      solver.solution().asScala.toList shouldBe List(initial, intermediate, goal)
    }

    "return 4 moves solution" in {
      val initial = new Board(Array(Array(0, 1, 3), Array(4, 2, 5), Array(7, 8, 6)))
      val intermediate1 = new Board(Array(Array(1, 0, 3), Array(4, 2, 5), Array(7, 8, 6)))
      val intermediate2 = new Board(Array(Array(1, 2, 3), Array(4, 0, 5), Array(7, 8, 6)))
      val intermediate3 = new Board(Array(Array(1, 2, 3), Array(4, 5, 0), Array(7, 8, 6)))

      val solver = new Solver(initial)
      solver.isSolvable shouldBe true
      solver.moves() shouldBe 4
      solver.solution().asScala.toList shouldBe List(initial, intermediate1, intermediate2, intermediate3, goal)
    }

    "return -1 moves solution for unsolvable board" in {
      val initial = new Board(Array(Array(1, 2, 3), Array(4, 5, 6), Array(8, 7, 0)))
      val solver = new Solver(initial)
      solver.isSolvable shouldBe false
      solver.moves() shouldBe -1
      solver.solution() shouldBe null
    }
  }

  def createBoard(fileName: String): Board = {
    val lines = Source.fromInputStream(getClass.getResourceAsStream(s"8puzzle/$fileName")).getLines().toList
    val tiles = lines.drop(1).map { line =>
      line.split("\\s+").filter(_.nonEmpty).map(s => Integer.parseInt(s))
    }.toArray

    new Board(tiles)
  }

  "When solver is given 4X4 board, then it" should {
    "return solution for given board" ignore {
      // 36, 44
      (0 to 50).filter(i => i != 36 && i != 44).map(i => (i, f"$i%2d".replace(' ', '0'))).map {
        case (moves, suffix) =>
          val fileName = s"puzzle4x4-$suffix.txt"
          val initial = createBoard(fileName)
          if (moves != 0) {
            initial.isGoal should not be(true)
          }
          val solver = new Solver(initial)
          solver.isSolvable shouldBe true
          solver.moves() shouldBe moves
          println(s"$fileName is success $moves")
      }
    }

    // TODO why solver is returning 38 instead of 36
    "return solution for puzzle4x4-36.txt" in {
      val initial = createBoard("puzzle4x4-36.txt")
      val solver = new Solver(initial)
      solver.isSolvable shouldBe true
      println(solver.solution().asScala.zipWithIndex.map { case (board, i) => s"Step $i:     $board" }.mkString("\n"))
      solver.moves() shouldBe 36
    }
  }

  "When solver is given 2X2 board, then it" should {
    "return solution for given board" in {
      // 36, 44
      (0 to 6).map(i => (i, f"$i%2d".replace(' ', '0'))).map {
        case (moves, suffix) =>
          val fileName = s"puzzle2x2-$suffix.txt"
          println(s"2X2 solvable board verification with the file name $fileName")
          val initial = createBoard(fileName)
          if (moves != 0) {
            initial.isGoal should not be(true)
          }
          val solver = new Solver(initial)
          solver.isSolvable shouldBe true
          solver.moves() shouldBe moves
      }
    }

    "return -1 moves for all unsolvable solutions" in {
      val unsolvable = List("puzzle2x2-unsolvable1.txt", "puzzle2x2-unsolvable2.txt",
        "puzzle2x2-unsolvable3.txt", "puzzle3x3-unsolvable.txt", "puzzle3x3-unsolvable1.txt",
        "puzzle3x3-unsolvable2.txt", "puzzle4x4-unsolvable.txt")

      unsolvable.foreach { fileName =>
        println(s"Unsolvable board verification with the file name $fileName")
        val initial = createBoard(fileName)
        initial.isGoal should not be (true)
        val solver = new Solver(initial)
        solver.isSolvable shouldBe false
        solver.moves() shouldBe -1
      }
    }
  }

  "When solver is given 3X3 board, then it" should {
    "return solution for given board" in {
      // 36, 44
      (0 to 31).map(i => (i, f"$i%2d".replace(' ', '0'))).map {
        case (moves, suffix) =>
          val fileName = s"puzzle3x3-$suffix.txt"
          println(s"3X3 solvable board verification with the file name $fileName")
          val initial = createBoard(fileName)
          if (moves != 0) {
            initial.isGoal should not be(true)
          }
          val solver = new Solver(initial)
          solver.isSolvable shouldBe true
          solver.moves() shouldBe moves
      }
    }
  }

  "When solver is given 2X2 board, then it" should {
    val goal = new Board(Array(Array(1, 2), Array(3, 0)))
    "return 1 move solution" in {
      val initial = new Board(Array(Array(1, 2), Array(0, 3)))
      val solver = new Solver(initial)
      solver.isSolvable shouldBe true
      solver.moves() shouldBe 1
      solver.solution().asScala.toList shouldBe List(initial, goal)
    }

    "return 2 move solution" in {
      val initial = new Board(Array(Array(0, 1), Array(3, 2)))
      val intermediate = new Board(Array(Array(1, 0), Array(3, 2)))
      val solver = new Solver(initial)
      solver.isSolvable shouldBe true
      solver.moves() shouldBe 2
      solver.solution().asScala.toList shouldBe List(initial, intermediate, goal)
    }
  }
}
