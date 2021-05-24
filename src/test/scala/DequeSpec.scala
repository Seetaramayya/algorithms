import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import tutorial.java.part1.assignment.permutation.Deque

import scala.jdk.CollectionConverters._

class DequeSpec extends AnyWordSpec with Matchers {

  "tutorial.java.part1.assignment.permutation.Deque" should {
    "add one items in the front" in {
      val deque = new Deque[Int]()
      deque.addFirst(1)

      deque.size() shouldBe 1
      deque.iterator().asScala.toList shouldBe List(1)
    }

    "add one items in the last" in {
      val deque = new Deque[Int]()
      deque.addLast(1)

      deque.size() shouldBe 1
      deque.iterator().asScala.toList shouldBe List(1)
    }

    "add one item and remove one item from the the last is same as no elements" in {
      val deque = new Deque[Int]()
      deque.addLast(1)
      deque.size() shouldBe 1

      deque.removeLast()
      deque.size() shouldBe 0
      deque.iterator().asScala.toList shouldBe List()
    }

    "add one item and remove one item from the the first is same as no elements" in {
      val deque = new Deque[Int]()
      deque.addFirst(1)
      deque.size() shouldBe 1

      deque.removeFirst()
      deque.size() shouldBe 0
      deque.iterator().asScala.toList shouldBe List()
    }

    "add 4 items in the front" in {
      val deque = new Deque[Int]()
      deque.addFirst(4)
      deque.addFirst(3)
      deque.addFirst(2)
      deque.addFirst(1)

      deque.size() shouldBe 4
      deque.iterator().asScala.toList shouldBe List(1, 2, 3, 4)
    }

    "add 4 items in the last" in {
      val deque = new Deque[Int]()
      deque.addLast(1)
      deque.addLast(2)
      deque.addLast(3)
      deque.addLast(4)

      deque.size() shouldBe 4
      deque.iterator().asScala.toList shouldBe List(1, 2, 3, 4)
    }

    "add items in the front or last, but result should work" in {
      val deque = new Deque[Int]()
      deque.addFirst(1)
      deque.addLast(2)
      deque.addFirst(0)
      deque.addLast(3)
      deque.addLast(4)
      deque.addFirst(-1)
      deque.addFirst(-2)

      deque.size() shouldBe 7
      deque.iterator().asScala.toList shouldBe (-2 to 4).toList
    }

    "add items and remove items in the front or last, but result should work" in {
      val deque = new Deque[Int]()
      deque.addFirst(1)
      deque.addLast(2)
      deque.addFirst(0)
      deque.addLast(3)
      deque.addLast(4)
      deque.addFirst(-1)
      deque.addFirst(-2)

      deque.iterator().asScala.toList shouldBe (-2 to 4).toList

      deque.removeFirst()
      deque.removeFirst()
      deque.removeFirst()
      deque.iterator().asScala.toList shouldBe (1 to 4).toList

      deque.addFirst(0)
      deque.removeLast()
      deque.removeLast()
      deque.iterator().asScala.toList shouldBe (0 to 2).toList

      deque.addFirst(-1)
      deque.removeFirst()
      deque.addLast(3)
      deque.addLast(4)
      deque.addFirst(-1)
      deque.addLast(5)

      deque.size() shouldBe 7
      deque.iterator().asScala.toList shouldBe (-1 to 5).toList
    }

    "add and remove" in {
      val deque = new Deque[Int]()
      (1 to 100).foreach(i => deque.addFirst(i))
      val result = (1 to 100).map(_ => deque.removeLast()).toList

      result shouldBe (1 to 100).toList
    }
  }

}
