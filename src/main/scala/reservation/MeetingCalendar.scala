package reservation

import model.{OfficeHours, Reservation}
import util.DateTimeUtil

import scala.collection.mutable


class MeetingCalendar {

  var reservationsMap: mutable.Map[String, List[Reservation]] = mutable.Map()
  var officeHours: Option[OfficeHours] = None

  def makeReservation(reservation: Reservation) = {

    if(validatteReservation(reservation)) {
      val key = reservation.reservationFrom.toLocalDate.toString

      val reservationList = reservationsMap.get(key) match {
        case Some(reservations) => reservation :: reservations
        case None => List(reservation)
      }

      reservationsMap += (key -> reservationList)
    }
  }

  def validatteReservation(reservation: Reservation) = {

    officeHours match {
      case Some(hours) => {
        val isStartWithinOfficeHours = DateTimeUtil.compareTimeRangeInclusive(reservation.reservationFrom.toLocalTime,
          hours.from, hours.to)

        val isEndWithinOfficeHours = DateTimeUtil.compareTimeRangeInclusive(reservation.reservationTo.toLocalTime,
          hours.from, hours.to)

        isStartWithinOfficeHours && isEndWithinOfficeHours
      }

      case None => false
    }
  }

  def allReservationsAsString() = {

    val reservationString = new StringBuilder

    reservationsMap.keySet.toSeq.sorted.foreach(key => {
      reservationString
        .append(key)
        .append(System.getProperty("line.separator"))

      reservationsMap.get(key).map(_.map(reservation => {
            reservationString
              .append(reservation.reservationFrom.toLocalTime.toString)
              .append(" ")
              .append(reservation.reservationTo.toLocalTime.toString)
              .append(" ")
              .append(reservation.employeeId)
              .append(System.getProperty("line.separator"))
      }))
    })

    reservationString.toString
  }
}
