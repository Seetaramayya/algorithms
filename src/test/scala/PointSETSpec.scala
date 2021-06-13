import edu.princeton.cs.algs4.{Point2D, RectHV}
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

import java.lang.{Double => JavaDouble}
import scala.io.Source
import scala.jdk.CollectionConverters.IterableHasAsScala

class PointSETSpec extends AnyWordSpec with Matchers {

  "PointSET brute force implementation" should {
    "handle api correctly with one point in the square " in {
      val pointsSet = new PointSET()
      pointsSet.isEmpty shouldBe true

      val p = new Point2D(0.1, 0.2)
      pointsSet.insert(p)
      pointsSet.isEmpty shouldBe false
      pointsSet.size() shouldBe 1
      pointsSet.contains(p) shouldBe true
      pointsSet.nearest(new Point2D(0.9, 0.9)) shouldBe p

      val containedRange = pointsSet.range(new RectHV(0.1, 0.1, 0.2, 0.2)).asScala.toList
      containedRange shouldBe List(p)

      val emptyRange = pointsSet.range(new RectHV(0.2, 0.2, 0.3, 0.3)).asScala.toList
      emptyRange shouldBe List()
    }

    "handle api correctly with two points in the square " in {
      val pointsSet = new PointSET()
      pointsSet.isEmpty shouldBe true

      val p1 = new Point2D(0.1, 0.2)
      val p2 = new Point2D(0.9, 0.9)
      pointsSet.insert(p1)
      pointsSet.isEmpty shouldBe false
      pointsSet.contains(p1) shouldBe true
      pointsSet.contains(p2) shouldBe false
      pointsSet.size() shouldBe 1
      pointsSet.nearest(new Point2D(0.7, 0.8)) shouldBe p1

      pointsSet.insert(p2)
      pointsSet.isEmpty shouldBe false
      pointsSet.size() shouldBe 2
      pointsSet.contains(p2) shouldBe true
      pointsSet.nearest(new Point2D(0.7, 0.8)) shouldBe p2

      val containedOneElementInRange = pointsSet.range(new RectHV(0.1, 0.1, 0.2, 0.2)).asScala.toList
      containedOneElementInRange shouldBe List(p1)

      val containedTwoElementInRange = pointsSet.range(new RectHV(0.0, 0.0, 0.99, 0.99)).asScala.toList
      containedTwoElementInRange shouldBe List(p1, p2)

      val emptyRange = pointsSet.range(new RectHV(0.2, 0.2, 0.3, 0.3)).asScala.toList
      emptyRange shouldBe List()
    }


    "handle API correctly for input10.txt" in {
      val points = readPointsFromResource("kdtree/input10.txt")
      val pointsSet = new PointSET()
      pointsSet.isEmpty shouldBe true
      points.foreach(point => pointsSet.insert(point))
      pointsSet.isEmpty shouldBe false
      pointsSet.size() shouldBe points.size

      val rectangle = rectangleWithWholeArea(points)
      val contained = pointsSet.range(rectangle).asScala.toList
      contained.size shouldBe points.size
    }

    "handle API correctly for input10K.txt" in {
      val points = readPointsFromResource("kdtree/vertical7.txt")
      val distinctPoints = points.toSet
      val pointsSet = new PointSET()
      pointsSet.isEmpty shouldBe true
      points.foreach(point => pointsSet.insert(point))
      pointsSet.isEmpty shouldBe false
      pointsSet.size() shouldBe distinctPoints.size

      val rectangle = rectangleWithWholeArea(points)
      val contained = pointsSet.range(rectangle).asScala.toList
      contained.size shouldBe distinctPoints.size
    }

    "handle API correctly for all input files" in {
      val files = List("circle10.txt",
        "circle100.txt",
        "circle1000.txt",
        "circle10000.txt",
        "circle4.txt",
        "horizontal8.txt",
        "input10.txt",
        "input100K.txt",
        "input10K.txt",
        "input1M.txt",
        "input200K.txt",
        "input20K.txt",
        "input400K.txt",
        "input40K.txt",
        "input800K.txt",
        "input80K.txt",
        "vertical7.txt")
      files.foreach { name =>
        println(s"Executing brute-force point set test for $name")
        val points = readPointsFromResource(s"kdtree/$name")
        val (smallestX, smallestY) = smallestCoordinates(points)
        val (largestX, largestY) = largestCoordinates(points)
        // lack of better name
        val smallPoint = new Point2D(smallestX, smallestY)
        val largePoint = new Point2D(largestX, largestY)
        val distinctPoints = points.toSet

        val pointsSet = new PointSET()
        pointsSet.isEmpty shouldBe true
        // before inserting points nearest point should be null
        pointsSet.nearest(smallPoint) shouldBe null

        // inserting all the points
        points.foreach(point => pointsSet.insert(point))

        pointsSet.isEmpty shouldBe false
        pointsSet.size() shouldBe distinctPoints.size

        pointsSet.nearest(smallPoint) shouldBe smallPoint
        pointsSet.nearest(largePoint) shouldBe largePoint

        val rectangle = rectangleWithWholeArea(points)
        val contained = pointsSet.range(rectangle).asScala.toList
        contained.size shouldBe distinctPoints.size
      }
    }
  }

  private def smallestCoordinates(points: List[Point2D]): (Double, Double) = {
    (points.minBy(_.x()).x(), points.minBy(_.y()).y())
  }

  private def largestCoordinates(points: List[Point2D]): (Double, Double) = {
    (points.maxBy(_.x()).x(), points.maxBy(_.y()).y())
  }

  private def rectangleWithWholeArea(points: List[Point2D]): RectHV = {
    val (smallestX, smallestY) = smallestCoordinates(points)
    val (largestX, largestY) = largestCoordinates(points)
    new RectHV(smallestX, smallestY, largestX, largestY)
  }

  private def readPointsFromResource(fileName: String): List[Point2D] = {
    val inputStream = getClass.getResourceAsStream(fileName)
    Source.fromInputStream(inputStream).getLines().filter(_.trim.nonEmpty).map { line =>
      val tokens = line.split(" ")
      val x = JavaDouble.parseDouble(tokens.head)
      val y = JavaDouble.parseDouble(tokens.head)
      new Point2D(x, y)
    }.toList
  }
}
