import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import tutorial.java.part1.assignment.collinear.{FastCollinearPoints, Point}

import scala.io.Source
import scala.util.Try

class FastCollinearPointsSpec extends AnyWordSpec with Matchers {
  "tutorial.java.part1.assignment.collinear.FastCollinearPoints" should {
    "return correct number of segments" in {
      val points = List[Point](new Point(1, 1), new Point(2, 2), new Point(3, 3), new Point(4, 4)).toArray
      val collinear = new FastCollinearPoints(points)
      collinear.numberOfSegments() shouldBe 1
      val lineSegment = collinear.segments().head
      lineSegment.toString shouldBe "(1, 1) -> (4, 4)"
    }

    "return correct number of segments (input array is not in order)" in {
      val points = List[Point](new Point(3, 3), new Point(2, 2),new Point(1, 1), new Point(4, 4), new Point(5, 5)).toArray
      val collinear = new FastCollinearPoints(points)
      collinear.numberOfSegments() shouldBe 1
      val lineSegment = collinear.segments().head
      lineSegment.toString shouldBe "(1, 1) -> (5, 5)"
    }

    "return 5 segments when horizontal5.txt is given" in {
      val points = Source
        .fromInputStream(getClass.getResourceAsStream("vertical100.txt"))
        .getLines()
        .toList
        .drop(1)
        .map(_.split(" "))
        .filter(_.length > 1)
        .map(array => (toIntOrZero(array.head), toIntOrZero(array.last)))
        .map {
          case (x, y) => new Point(x, y)
        }.toArray

      (1 to 5).foreach { i =>
        val collinear = new FastCollinearPoints(points)
        println(collinear.segments().mkString(";"))
        collinear.numberOfSegments() shouldBe 100
      }
    }

    "return correct number of segments = 2" in {
      val points = List[Point](new Point(1, 0), new Point(2, 1),new Point(3, 2), new Point(4, 3), new Point(2, 3), new Point(1, 4), new Point(4, 1)).toArray
      val collinear = new FastCollinearPoints(points)
      println(collinear.segments().mkString("\n"))
      collinear.numberOfSegments() shouldBe 2
      val segment1 = collinear.segments().head
      val segment2 = collinear.segments().tail.head
      segment1.toString shouldBe "(1, 0) -> (4, 3)"
      segment2.toString shouldBe "(4, 1) -> (1, 4)"
    }

    "throw IllegalArgumentException when null is passed for points" in {
      intercept[IllegalArgumentException] {
        new FastCollinearPoints(null)
      }
    }

    "throw IllegalArgumentException when null point is passed in points" in {
      intercept[IllegalArgumentException] {
        val points = new Array[Point](2)
        points(0) = new Point(1, 1)
        new FastCollinearPoints(points)
      }
    }

    "throw IllegalArgumentException when there are duplicate points (size = 2)" in {
      intercept[IllegalArgumentException] {
        val points = new Array[Point](2)
        points(0) = new Point(1, 1)
        points(1) = new Point(1, 1)
        new FastCollinearPoints(points)
      }
    }

    "throw IllegalArgumentException when there are duplicate points (size = 5)" in {
      intercept[IllegalArgumentException] {
        val points = List[Point](new Point(1, 1), new Point(2, 2), new Point(3, 3), new Point(4, 4), new Point(4, 4)).toArray
        new FastCollinearPoints(points)
      }
    }
  }

  private def toIntOrZero(number: String) = Try(Integer.parseInt(number)).getOrElse(0)
}
