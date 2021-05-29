package tutorail.java.part1.sortings

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class MergeSortSpec extends AnyWordSpec with Matchers {
  "MergeSort" should {
    "sort random arry" in {
      val input: Array[Comparable[_]] = Array(10, 9, 8, 7, 6, 5, 4, 3, 2, 1)
      MergeSort.sort(input)
      input shouldBe Array(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
    }
  }
}
