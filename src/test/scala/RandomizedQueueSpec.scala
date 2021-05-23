import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

import scala.jdk.CollectionConverters.IteratorHasAsScala

class RandomizedQueueSpec extends AnyWordSpec with Matchers {
  "RandomizedQueue" should {
    "enqueue/dequeue correct number of items" in {
      val randomizedQueue = new RandomizedQueue[Int]()
      val max = 100
      randomizedQueue.isEmpty shouldBe true
      randomizedQueue.size() shouldBe 0

      val inputItems = enqueueItems(randomizedQueue, max)
      randomizedQueue.isEmpty shouldBe false
      randomizedQueue.size() shouldBe max

      val removedItems = dequeue(randomizedQueue, max)
      inputItems.toSet shouldBe removedItems.toSet

      // order should not be same but some times randomness might give same order, then bellow assert fails, flaky test :)
      // chance of getting same order is almost impossible if number of items grows higher and higher
      // chance definite when one element exists, 2 elements probability is 0.5, 3 elements probability is 0.333, etc....
      inputItems should not be(removedItems)

      randomizedQueue.size() shouldBe 0
      randomizedQueue.isEmpty shouldBe true
    }

    "return random iterator" in {
      val randomizedQueue = new RandomizedQueue[Int]()
      val max = 4
      enqueueItems(randomizedQueue, max)

      (1 to max).foreach { i =>
        val iterated1Items = randomizedQueue.iterator().asScala.toList
        val iterated2Items = randomizedQueue.iterator().asScala.toList
        iterated1Items should not be(iterated2Items)
      }

    }

  }
  private def enqueueItems(randomizedQueue: RandomizedQueue[Int], count: Int): List[Int] = (1 to count).toList.map { i =>
    randomizedQueue.enqueue(i) //side effect
    i
  }

  private def dequeue(randomizedQueue: RandomizedQueue[Int], count: Int): List[Int] =
    (1 to count).foldLeft(List[Int]())((removedItems, _) => removedItems :+ randomizedQueue.dequeue())
}
