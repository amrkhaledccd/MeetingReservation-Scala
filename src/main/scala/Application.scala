import reader.FileReader
import reservation.{MeetingCalendar, ReservationRequestProcessor}

object Application extends App {

  val meetingCalendar = new MeetingCalendar
  val reservationRequestProcessor = new ReservationRequestProcessor(meetingCalendar)

  val batch = FileReader.fromClassPath("/reservation_batch")
  val response = reservationRequestProcessor.processBatchReservations(batch)

  println(response)
}
