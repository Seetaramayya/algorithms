import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import tutorial.java.part1.assignment.collinear.Point

class PointSpec extends AnyWordSpec with Matchers {

  "tutorial.java.part1.assignment.collinear.Point compare" should {
    "return correct value based on point position" in {
      val p = point(6, 2)
      val q = point(2, 1)
      val r = point(5, 2)
      p.slopeOrder().compare(q, r) shouldBe 1

      val p1 = point(9065, 6751)
      val q1 = point(32256, 13696)
      val r1 = point(9065, 529)
      p1.slopeOrder().compare(q1, r1) shouldBe -1
    }

    "throw NullPointerException if first parameter is null" in {
      intercept[NullPointerException] {
        point(6, 2).slopeOrder().compare(null, point(5, 2)) shouldBe 1
      }
    }

    "throw NullPointerException if second parameter is null" in {
      intercept[NullPointerException] {
        point(6, 2).slopeOrder().compare(point(5, 2), null) shouldBe 1
      }
    }

  }

  def point(x: Int, y: Int) = new Point(x, y)
}
