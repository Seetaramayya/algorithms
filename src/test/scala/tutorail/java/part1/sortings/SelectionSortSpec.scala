package tutorail.java.part1.sortings

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class SelectionSortSpec extends AnyWordSpec with Matchers {
  "Java Selection sort" should {
    "sort already sorted array as well" in {
      val input: Array[Comparable[_]] = Array(1, 2, 3, 4)
      SelectionSort.sort(input)
      input shouldBe Array(1, 2, 3, 4)
    }

    "sort unsorted array with duplicate values" in {
      val input: Array[Comparable[_]] = Array(3, 2, 1, 4, 4, 1, 2)
      SelectionSort.sort(input)
      input shouldBe Array(1, 1, 2, 2, 3, 4, 4)
    }
  }
}
