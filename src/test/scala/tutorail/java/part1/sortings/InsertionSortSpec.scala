package tutorail.java.part1.sortings

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class InsertionSortSpec extends AnyWordSpec with Matchers {
  "Insertion sort implemented in java" should {
    "sort already sorted array as well ( best case comparisons are n-1)" in {
      val input: Array[Comparable[_]] = Array(1, 2, 3, 4)
      val comparison = InsertionSort.sort(input)
      comparison shouldBe input.length - 1
      input shouldBe Array(1, 2, 3, 4)
    }

    "sort an array with 4 elements ( best case comparisons are ∑n-1)" in {
      val input: Array[Comparable[_]] = Array(4, 3, 2, 1)
      val comparison = InsertionSort.sort(input)
      input shouldBe Array(1, 2, 3, 4)
      comparison shouldBe 6
    }

    "sort an array which is in worst case ( best case comparisons are ∑n-1)" in {
      val n = 10
      val input: Array[Comparable[_]] = Array(10, 9, 8, 7, 6, 5, 4, 3, 2, 1)
      val comparison = InsertionSort.sort(input)
      val expectedComparisons = n * (n - 1) / 2
      comparison shouldBe expectedComparisons
      input shouldBe Array(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
    }

    "sort unsorted array with duplicate values" in {
      val input: Array[Comparable[_]] = Array(3, 2, 1, 4, 4, 1, 2)
      InsertionSort.sort(input)
      input shouldBe Array(1, 1, 2, 2, 3, 4, 4)
    }
  }
}
