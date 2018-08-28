package util

import java.time.{LocalDate, LocalDateTime, LocalTime}
import java.time.format.DateTimeFormatter

object DateTimeUtil {

  def time24ToLocalTime(time24: String): LocalTime = LocalTime.parse(time24, DateTimeFormatter.ISO_TIME)

  def stringToLocalDate(date: String): LocalDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"))

  def buildLocalDateTime(date: String, time: String): LocalDateTime = LocalDateTime.of(stringToLocalDate(date), time24ToLocalTime(time))

  def compareDays(day1: LocalDateTime, day2: LocalDateTime) = day1.toLocalDate.compareTo(day2.toLocalDate) == 0

  /*
   * Compares two time with inclusive start and end
   */ def compareTimeRangeInclusive(time: LocalTime, rangeFrom: LocalTime, rangeTo: LocalTime) =
    (time.compareTo(rangeFrom) == 0 || time.isAfter(rangeFrom)) &&
      (time.compareTo(rangeTo) == 0 || time.isBefore(rangeTo))

  /*
   * Compares two time with inclusive start
   */
  def compareTimeRangeInclusiveStart(time: LocalTime, rangeFrom: LocalTime, rangeTo: LocalTime) =
    (time.compareTo(rangeFrom) == 0 || time.isAfter(rangeFrom)) &&
      time.isBefore(rangeTo)

  /*
   * Compares two time with inclusive end
   */
  def compareTimeRangeInclusiveEnd(time: LocalTime, rangeFrom: LocalTime, rangeTo: LocalTime) =
    time.isAfter(rangeFrom) &&
      (time.compareTo(rangeTo) == 0 || time.isBefore(rangeTo))

  /*
   * Checks overlap between two time periods
   */
  def checkOverlap(thisTimeFrom: LocalTime, thisTimeTo: LocalTime, thatTimeFrom: LocalTime, thatTimeTo: LocalTime) =
    compareTimeRangeInclusiveStart(thisTimeFrom, thatTimeFrom, thatTimeTo) ||
    compareTimeRangeInclusiveEnd(thisTimeTo, thatTimeFrom, thatTimeTo) ||
    compareTimeRangeInclusiveStart(thatTimeFrom, thisTimeFrom, thisTimeTo) ||
    compareTimeRangeInclusiveEnd(thatTimeTo, thisTimeFrom, thisTimeTo)

}
