package tutorail.scala.part1.sortings

import scala.math.Ordering
import Ordered._

object SelectionSort {

  /**
   * Returns sorted Array
   * @param input array that needs to be sorted
   * @tparam T for any type T
   * @return sorted array
   */
  def sort[T](input: Array[T])(implicit ord: Ordering[T]): Array[T] = {
    input.indices.flatMap { i =>
      (i + 1 until input.length).map { j =>
        if (input(j) < input(i)) {
          swap(input, i, j)
        }
      }
    }
    input
  }

  def swap[T](input: Array[T], i: Int, j: Int): Unit = {
    val temp = input(i)
    input(i) = input(j)
    input(j) = temp
  }

}
