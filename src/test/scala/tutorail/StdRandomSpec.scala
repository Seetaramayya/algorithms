package tutorail

import edu.princeton.cs.algs4.StdRandom
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class StdRandomSpec extends AnyWordSpec with Matchers {
  "StdRandom" should {
    "generate random number between 0 to n - 1" in {
      StdRandom.uniform(1) shouldBe 0
      StdRandom.uniform(2) should ( be >= 0 and be < 2)
      StdRandom.uniform(3) should ( be >= 0 and be < 3)

    }
  }

}
