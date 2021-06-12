import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import tutorial.java.part1.assignment.eightpuzzle.Board

import scala.io.Source
import scala.jdk.CollectionConverters.IterableHasAsScala

class BoardSpec extends AnyWordSpec with Matchers {
  "tutorial.java.part1.assignment.eightpuzzle.Board" should {
    "be able to create for this input as well" in {
      val board = createBoard("puzzle4x4-unsolvable.txt")
      board.dimension() shouldBe 4
    }

    "check manhattan() with file inputs" ignore {
      (0 to 50).filter(i => i != 11 && i != 14 && i != 16 && i != 17).map(i => (i, f"$i%2d".replace(' ', '0'))).map {
        case (manhattan, fileSuffix) =>
          println(s"puzzle$fileSuffix.txt")
          val board = createBoard(s"puzzle$fileSuffix.txt")
          board.manhattan() shouldBe manhattan
      }
    }

    "sample" ignore {
      val board = createBoard(s"puzzle11.txt")
      println(board)
      createBoard(s"puzzle04.txt").manhattan() shouldBe 4
      createBoard(s"puzzle07.txt").manhattan() shouldBe 7
      createBoard(s"puzzle11.txt").manhattan() shouldBe 11
    }

    "return the size of the board" in {
      val tiles: Array[Array[Int]] = Array(Array(8, 1, 3), Array(4, 0, 2), Array(7, 6, 5))
      val board = new Board(tiles)
      board.dimension() shouldBe 3
    }

    "calculate Hamming distance between a board and the goal board is the number of tiles in the wrong position" in {
      val tiles: Array[Array[Int]] = Array(Array(8, 1, 3), Array(4, 0, 2), Array(7, 6, 5))
      val board = new Board(tiles)
      board.hamming() shouldBe 5
    }

    "calculate Manhattan distance between a board and the goal board is the sum of the Manhattan distances" +
      " (sum of the vertical and horizontal distance) from the tiles to their goal positions" in {
      val tiles: Array[Array[Int]] = Array(Array(8, 1, 3), Array(4, 0, 2), Array(7, 6, 5))
      val board = new Board(tiles)
      board.manhattan() shouldBe 10
    }

    "return correct equal value" in {
      val tiles: Array[Array[Int]] = Array(Array(8, 1, 3), Array(4, 0, 2), Array(7, 6, 5))
      val tiles2: Array[Array[Int]] = Array(Array(8, 1, 3), Array(4, 1, 2), Array(7, 6, 5))
      val board1 = new Board(tiles)
      val board2 = new Board(tiles)
      board1 shouldBe board2
      board1 should not be(new Board(tiles2))
    }

    "return 4 neighbours for given board" in {
      val tiles: Array[Array[Int]] = Array(Array(8, 1, 3), Array(4, 0, 2), Array(7, 6, 5))
      val board = new Board(tiles)
      board.toString shouldBe "3\n 8  1  3 \n 4  0  2 \n 7  6  5 \n"
      val neighbors = board.neighbors().asScala.toList
      neighbors.size shouldBe 4
      board.toString shouldBe "3\n 8  1  3 \n 4  0  2 \n 7  6  5 \n"
      neighbors.head.toString shouldBe "3\n 8  1  3 \n 0  4  2 \n 7  6  5 \n"

      neighbors(1).toString shouldBe "3\n 8  1  3 \n 4  2  0 \n 7  6  5 \n"
      neighbors(2).toString shouldBe "3\n 8  0  3 \n 4  1  2 \n 7  6  5 \n"
      neighbors(3).toString shouldBe "3\n 8  1  3 \n 4  6  2 \n 7  0  5 \n"
    }

    "return 2 neighbours for 2X2 board" in {
      val tiles: Array[Array[Int]] = Array(Array(1, 2), Array(3, 0))
      val board = new Board(tiles)
      board.toString shouldBe "2\n 1  2 \n 3  0 \n"
      val neighbors = board.neighbors().asScala.toList
      neighbors.size shouldBe 2
      board.toString shouldBe "2\n 1  2 \n 3  0 \n"
      neighbors.head.toString shouldBe "2\n 1  2 \n 0  3 \n"

      neighbors(1).toString shouldBe "2\n 1  0 \n 3  2 \n"
    }

    "return 2 neighbours for given board" in {
      val tiles: Array[Array[Int]] = Array(Array(0, 1, 3), Array(4, 8, 2), Array(7, 6, 5))
      val board = new Board(tiles)
      val neighbors = board.neighbors().asScala.toList
      neighbors.size shouldBe 2
    }

    "return 3 neighbours for given board" in {
      val tiles: Array[Array[Int]] = Array(Array(2, 1, 3), Array(4, 8, 0), Array(7, 6, 5))
      val board = new Board(tiles)
      val neighbors = board.neighbors().asScala.toList
      neighbors.size shouldBe 3
    }

    "return true if the given board is the goal for 2X2" in {
      val tiles: Array[Array[Int]] = Array(Array(1, 2), Array(3, 0))
      val board = new Board(tiles)
      board.isGoal shouldBe true
      board.neighbors().asScala.head.isGoal shouldBe false
    }

    "return true if the given board is the goal for 3X3" in {
      val nonGoalTiles: Array[Array[Int]] = Array(Array(2, 1, 3), Array(4, 8, 0), Array(7, 6, 5))
      val goalTiles: Array[Array[Int]] = Array(Array(1, 2, 3), Array(4, 5, 6), Array(7, 8, 0))
      new Board(nonGoalTiles).isGoal shouldBe false
      new Board(goalTiles).isGoal shouldBe true
    }

    "return twinned board for 2X2" in {
      val tiles: Array[Array[Int]] = Array(Array(1, 2), Array(3, 0))
      val board = new Board(tiles)
      (1 to 5000).foreach { _ =>
        val twin = board.twin()
        board shouldBe board
        twin shouldBe twin
        twin should not be board
      }
    }
  }

  def createBoard(fileName: String): Board = {
    val lines = Source.fromInputStream(getClass.getResourceAsStream(s"8puzzle/$fileName")).getLines().toList
    val n = Integer.parseInt(lines.head.trim)
    val tiles: Array[Array[Int]] = lines.slice(1, n + 1).map { line =>
      line.split("\\s+").filter(_.nonEmpty).map(s => Integer.parseInt(s))
    }.toArray

    new Board(tiles)
  }
}
