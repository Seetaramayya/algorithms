package tutorial.scala.part1.percoloation

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import tutorial.java.part1.assignment.percolation.Percolation

class PercolationSpec extends AnyWordSpec with Matchers {

  "tutorial.java.part1.assignment.percolation.Percolation" should {
    "return true after opening few sites (positive case)" in {
      val percolation = new Percolation(3)
      percolation.isFull(1, 1) shouldBe false
      percolation.open(1,1)
      percolation.isFull(1, 1) shouldBe true


      percolation.isFull(3, 3) shouldBe false

      percolation.open(3,3)
      percolation.isFull(3, 3) shouldBe false

      percolation.open(3,2)
      percolation.isFull(3, 3) shouldBe false

      percolation.open(3,1)
      percolation.isFull(3, 3) shouldBe false

      percolation.open(2,1)
      percolation.isFull(3, 3) shouldBe true

      percolation.isFull(2, 1) shouldBe true

      percolation.open(2, 2)
      percolation.open(2, 3)
      percolation.open(1, 3)
      percolation.isFull(1, 3) shouldBe true
    }

    "return false after opening few sites (false positive case)" in {
      val percolation = new Percolation(3)
      percolation.open(3, 3)
      percolation.open(3, 1)
      percolation.open(2, 2)
      percolation.open(2, 3)
      percolation.open(1, 1)
      percolation.open(1, 2)

      println(percolation)

      percolation.isFull(1, 1) shouldBe true
      percolation.isFull(3, 1) shouldBe false
    }

    "return false after opening few sites for non bottom rows (false positive case)" in {
      val percolation = new Percolation(3)
      percolation.open(1, 3)
      percolation.open(2, 1)
      percolation.open(2, 3)
      percolation.open(3, 2)
      percolation.open(3, 3)

      percolation.isFull(2, 1) shouldBe false
      percolation.isFull(3, 2) shouldBe true
    }

    "return true if it percolates for n = 1" in {
      val percolation = new Percolation(1)
      percolation.isOpen(1, 1) shouldBe false
      percolation.isFull(1, 1) shouldBe false
      percolation.percolates() shouldBe false

      percolation.open(1, 1)
      percolation.percolates() shouldBe true
    }

    "return true if it percolates for n = 2" in {
      val percolation = new Percolation(2)
      percolation.percolates() shouldBe false

      percolation.open(1, 1)
      percolation.open(2, 2)
      percolation.percolates() shouldBe false

      percolation.open(1, 2)
      percolation.percolates() shouldBe true
    }
  }
}
