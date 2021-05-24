package tutorial.scala.part1.queues

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import tutorial.java.part1.assignment.permutation.RandomizedQueue

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
      val max = 10
      enqueueItems(randomizedQueue, max)

      (1 to max).foreach { i =>
        val iterated1Items = randomizedQueue.iterator().asScala.toList
        val iterated2Items = randomizedQueue.iterator().asScala.toList
        iterated1Items should not be(iterated2Items)
      }

    }

    // distribution is not really that great, List(280, 220, 250, 250) is not that good, if the negligibility is less than
    // 10 is good enough for me.
    "test randomness: permutations for (n= 4, k=1) for 1000 trails distribution" in {
      val k = 1
      val n = 4
      val totalTrails = 1000
      val negligibility = 30
      val average = totalTrails / n
      val distribution = (1 to totalTrails).map(_ => permutation(k, n).head).groupBy(identity).map(_._2.size)
      val deviation = distribution.map {numberOfElements => average - numberOfElements}

      println(s"permutations for (n= 4, k=1) for 1000 trails distribution is : $distribution")
      println(s"permutations for (n= 4, k=1) for 1000 trails deviation    is : $deviation")
      deviation.count(Math.abs(_) > negligibility) shouldBe 0
    }

  }

  // TODO: if the number of elements to keep in memory is k (instead of n) then randomness is not uniform.
  // 0 <= k <= n
  private def permutation(k: Int, n: Int) = {
    val randomizedQueue = new RandomizedQueue[Int]()
    var total = 0;
    (1 to n).foreach { i =>
      randomizedQueue.enqueue(i)
      total += 1
    }
//    randomizedQueue.size() shouldBe k
//    val set = randomizedQueue.iterator().asScala.toSet
//    set.size shouldBe k
//    set
    (1 to k).map(_ => randomizedQueue.dequeue())
  }


  private def enqueueItems(randomizedQueue: RandomizedQueue[Int], count: Int): List[Int] = (1 to count).toList.map { i =>
    randomizedQueue.enqueue(i) //side effect
    i
  }

  private def dequeue(randomizedQueue: RandomizedQueue[Int], count: Int): List[Int] =
    (1 to count).foldLeft(List[Int]())((removedItems, _) => removedItems :+ randomizedQueue.dequeue())
}
