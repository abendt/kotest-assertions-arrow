package io.kotest.assertions.arrow.core

import arrow.core.NonEmptyList
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.comparables.beGreaterThan
import io.kotest.matchers.comparables.beLessThan
import io.kotest.matchers.should
import io.kotest.matchers.shouldBe

class NelInspectors : StringSpec({

  "forNone" {
    nel.forNone {
      it shouldBe 10
    }
  }

  "forNone: fail if one elements passes fn test" {
    val t = shouldThrow<AssertionError> {
      nel.forNone {
        it shouldBe 4
      }
    }
    t.message shouldBe """1 elements passed but expected 0

The following elements passed:
4

The following elements failed:
1 => expected:<4> but was:<1>
2 => expected:<4> but was:<2>
3 => expected:<4> but was:<3>
5 => expected:<4> but was:<5>"""
  }

  "forNone: fail if all elements pass fn test" {
    shouldThrow<AssertionError> {
      nel.forNone {
        it should beGreaterThan(0)
      }
    }.message shouldBe """5 elements passed but expected 0

The following elements passed:
1
2
3
4
5

The following elements failed:
--none--"""
  }


  "forSome" {
    nel.forSome {
      it shouldBe 3
    }
  }

  "forSome: pass if size-1 elements pass test"  {
    nel.forSome {
      it should beGreaterThan(1)
    }
  }

  "forSome: fail if no elements pass test"  {
    shouldThrow<AssertionError> {
      nel.forSome {
        it should beLessThan(0)
      }
    }.message shouldBe """No elements passed but expected at least one

The following elements passed:
--none--

The following elements failed:
1 => 1 should be < 0
2 => 2 should be < 0
3 => 3 should be < 0
4 => 4 should be < 0
5 => 5 should be < 0"""
  }

  "forSome: fail if all elements pass test"  {
    shouldThrow<AssertionError> {
      nel.forSome {
        it should beGreaterThan(0)
      }
    }.message shouldBe """All elements passed but expected < 5

The following elements passed:
1
2
3
4
5

The following elements failed:
--none--"""
  }

  "forOne"{
    nel.forOne {
      it shouldBe 3
    }
  }
  "forOne: fail if > 1 elements pass test"  {
    shouldThrow<AssertionError> {
      nel.forOne {
        it should beGreaterThan(2)
      }
    }.message shouldBe """3 elements passed but expected 1

The following elements passed:
3
4
5

The following elements failed:
1 => 1 should be > 2
2 => 2 should be > 2"""
  }

  "forOne fail if no elements pass test"  {
    shouldThrow<AssertionError> {
      nel.forOne {
        it shouldBe 22
      }
    }.message shouldBe """0 elements passed but expected 1

The following elements passed:
--none--

The following elements failed:
1 => expected:<22> but was:<1>
2 => expected:<22> but was:<2>
3 => expected:<22> but was:<3>
4 => expected:<22> but was:<4>
5 => expected:<22> but was:<5>"""
  }

  "forAny" {
    nel.forAny {
      it shouldBe 3
    }
  }
  "forAny: pass if at least elements pass test"  {
    nel.forAny {
      it should beGreaterThan(2)
    }
  }

  "forAny: fail if no elements pass test"  {
    shouldThrow<AssertionError> {
      nel.forAny {
        it shouldBe 6
      }
    }.message shouldBe """0 elements passed but expected at least 1

The following elements passed:
--none--

The following elements failed:
1 => expected:<6> but was:<1>
2 => expected:<6> but was:<2>
3 => expected:<6> but was:<3>
4 => expected:<6> but was:<4>
5 => expected:<6> but was:<5>"""
  }

  "forExactly" {
    nel.forExactly(2) {
      it should beLessThan(3)

    }
  }

  "forExactly: fail if more elements pass test"  {
    shouldThrow<AssertionError> {
      nel.forExactly(2) {
        it should beGreaterThan(2)
      }
    }.message shouldBe """3 elements passed but expected 2

The following elements passed:
3
4
5

The following elements failed:
1 => 1 should be > 2
2 => 2 should be > 2"""
  }

  "forExactly: fail if less elements pass test"  {
    shouldThrow<AssertionError> {
      nel.forExactly(2) {
        it should beLessThan(2)
      }
    }.message shouldBe """1 elements passed but expected 2

The following elements passed:
1

The following elements failed:
2 => 2 should be < 2
3 => 3 should be < 2
4 => 4 should be < 2
5 => 5 should be < 2"""
  }

  "forExactly: fail if no elements pass test"  {
    shouldThrow<AssertionError> {
      nel.forExactly(2) {
        it shouldBe 33
      }
    }.message shouldBe """0 elements passed but expected 2

The following elements passed:
--none--

The following elements failed:
1 => expected:<33> but was:<1>
2 => expected:<33> but was:<2>
3 => expected:<33> but was:<3>
4 => expected:<33> but was:<4>
5 => expected:<33> but was:<5>"""
  }
})

private val nel = NonEmptyList(1, listOf(2, 3, 4, 5))
