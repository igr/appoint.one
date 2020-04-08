package cal

import domain.appointment.Appointment
import java.time.format.DateTimeFormatter

object ICS {
	private val dateFormat = DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmmss'Z'")

	fun of(ap: Appointment): String {
		val ts = ap.timeslot
		val ldt = ts.datetime.toLocalDateTime()
		return """
			| BEGIN:VCALENDAR
			| VERSION:2.0
			| PRODID:-//oblac/igr//NONSGML v1.0//EN
			| BEGIN:VEVENT
			| UID:${ts.id}"
			| DTSTART:${ldt.format(dateFormat)}
			| DTEND:${ldt.plusMinutes(30).format(dateFormat)}
			| SUMMARY: Psihoterapeut
			| DESCRIPTION: ${ap.doctor.data.name} ${ap.doctor.data.phone}
			| TRANSP:OPAQUE
			| FBTYPE:BUSY-UNAVAILABLE
			| BEGIN:VALARM
			| TRIGGER:-PT15M
			| ACTION:DISPLAY
			| DESCRIPTION:Reminder
			| END:VALARM
			| TRANSP:OPAQUE
			| END:VEVENT
			| END:VCALENDAR
		""".trimIndent()
	}
}
