import edu.princeton.cs.algs4.{StdRandom, StdStats}
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import tutorial.java.part1.assignment.percolation.{Percolation, PercolationStats}

import scala.annotation.tailrec

class PercolationStatsSpec extends AnyWordSpec with Matchers {

  "tutorial.java.part1.assignment.percolation.PercolationStats" should {
    "return correct stats for (n=1, trails=1)" in {
      val stats = new PercolationStats(1, 1)
      stats.stddev().isNaN shouldBe true
      stats.mean() shouldBe 1.0
      stats.confidenceLo().isNaN shouldBe true
      stats.confidenceHi().isNaN shouldBe true
    }

    "return correct stats for (n=2, trails=1)" in {
      val stats = new PercolationStats(2, 1)
      stats.stddev().isNaN shouldBe true
      stats.mean() should equal (0.625 +- 0.125)
      stats.confidenceLo().isNaN shouldBe true
      stats.confidenceHi().isNaN shouldBe true
    }

    "return correct stats for (n=1, trails=10)" in {
      val stats = new PercolationStats(1, 10)
      stats.stddev() shouldBe 0.0
      stats.mean() shouldBe 1.0
      stats.confidenceLo() shouldBe 1.0
      stats.confidenceHi() shouldBe 1.0
    }

    "return correct stats for (n=5, trails=10000)" in {
      val n = 5
      val trails = 10000
      val stats = new PercolationStats(n, trails)
      val ts = thresholds(n, trails)
      val expectedStdDev = calculateStdDev(ts)
      val (low, high) = calculate95(ts)
      /*
    - student tutorial.java.part1.assignment.percolation.PercolationStats stddev() = 0.108331
    - true stddev                       = 0.102653
    - 99.99% confidence interval        = [0.099839, 0.105487]
       */
      stats.stddev() should equal(expectedStdDev +- 0.01)
      stats.mean() should equal(calculateMean(ts) +- 0.01)
      stats.confidenceLo() should equal(low +- 0.01)
      stats.confidenceHi() should equal(high +- 0.01)
    }
  }

  private def getOpenSitesIfPercolates(n: Int): Double = {
    val p = new Percolation(n)
    while (!p.percolates()) {
      p.open(random(n), random(n))
    }
    p.numberOfOpenSites().toDouble / (n * n)
  }

  private def thresholds(n: Int, trails: Int): Array[Double] = (1 to trails).map(_ => getOpenSitesIfPercolates(n)).toArray

  private def calculateStdDev(thresholds: Array[Double]): Double = {
    val variance: Double = thresholds.map(_ - calculateMean(thresholds)).map(x => x * x).sum / (thresholds.length - 1) // With Bessel's correction
    Math.sqrt(variance)
  }

  private def calculate95(thresholds: Array[Double]): (Double, Double) = {
    val mean: Double = calculateMean(thresholds)
    val stdDev = calculateStdDev(thresholds)
    (mean - (1.96 * stdDev / Math.sqrt(thresholds.length)), mean + (1.96 * stdDev / Math.sqrt(thresholds.length)))
  }

  private def calculateMean(thresholds: Array[Double]): Double = thresholds.sum / thresholds.length


  private def random(n: Int): Int = {
    val p = StdRandom.uniform(n + 1)
    if (p == 0) 1 else p
  }
}
