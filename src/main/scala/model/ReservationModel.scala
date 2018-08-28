package model

import java.time.{LocalDateTime, LocalTime}

import util.DateTimeUtil

case class OfficeHours(from: LocalTime, to: LocalTime)

case class Reservation(employeeId: String,
                       requestDate: LocalDateTime,
                       reservationFrom: LocalDateTime,
                       reservationTo: LocalDateTime)

object Reservation {

  def apply(reservationRequest: String, reservationDate: String): Reservation = {

    // 0 = request day, 1 = request time, 2 = emp id
    val reservationRequestArray = reservationRequest.split(" ")

    // 0 = reservation day, 1 = reservation time, 2 = meeting hours
    val reservationDateArray = reservationDate.split(" ")

    val requestDate = DateTimeUtil.buildLocalDateTime(reservationRequestArray(0), reservationRequestArray(1))
    val empId = reservationRequestArray(2)
    val reservationFrom = DateTimeUtil.buildLocalDateTime(reservationDateArray(0), reservationDateArray(1))
    val reservationTo = reservationFrom.plusHours(reservationDateArray(2).toLong)

    Reservation(empId, requestDate, reservationFrom, reservationTo)
  }
}
