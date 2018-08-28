package reservation

import model.{OfficeHours, Reservation}
import util.DateTimeUtil


class ReservationRequestProcessor(meetingCalendar: MeetingCalendar) {

  def processBatchReservations(batch: List[String]) = {

    val officeHours = parseOfficeHours(batch.head)
    meetingCalendar.officeHours = Some(officeHours)

    batch.tail.sliding(2, 2)
      .map(res => Reservation(res.head, res.tail.head))
      .foreach(meetingCalendar.makeReservation(_))

    meetingCalendar.allReservationsAsString()
  }

  private def parseOfficeHours(hours: String) = {

    val hoursArray = hours.split(" ")
    val from = hoursArray(0).substring(0, 2) + ":" + hoursArray(0).substring(2, 4)
    val to = hoursArray(1).substring(0, 2) + ":" + hoursArray(1).substring(2, 4)

    OfficeHours(DateTimeUtil.time24ToLocalTime(from), DateTimeUtil.time24ToLocalTime(to))
  }
}
