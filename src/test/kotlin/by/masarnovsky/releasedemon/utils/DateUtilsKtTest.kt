package by.masarnovsky.releasedemon.utils

import java.time.LocalDate
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class DateUtilsKtTest {

  @Test
  fun tryDateWithSuccessParsingForFullDate() {
    val expected = LocalDate.of(2008, 10, 25)
    val result = tryDate("2008-10-25")

    assertEquals(expected, result)
  }

  @Test
  fun tryDateWithSuccessParsingForDefaultDate() {
    val expected = LocalDate.of(1970, 1, 1)
    val result = tryDate("200")

    assertEquals(expected, result)
  }

  @Test
  fun tryFullWithSuccess() {
    val expected = LocalDate.of(2008, 10, 25)
    val result = tryFull("2008-10-25")

    assertEquals(expected, result)
  }

  @Test
  fun tryFullWithError() {
    val result = tryFull("2008-25-25")

    assertNull(result)
  }

  @Test
  fun tryYearAndMonthWithSuccess() {
    val expected = LocalDate.of(2008, 10, 1)
    val result = tryYearAndMonth("2008-10")

    assertEquals(expected, result)
  }

  @Test
  fun tryYearAndMonthWithFail() {
    val result = tryYearAndMonth("2008-20")

    assertNull(result)
  }

  @Test
  fun tryYearWithSuccess() {
    val expected = LocalDate.of(2008, 1, 1)
    val result = tryYear("2008")

    assertEquals(expected, result)
  }

  @Test
  fun tryYearWithFail() {
    val result = tryYear("12")

    assertNull(result)
  }
}
