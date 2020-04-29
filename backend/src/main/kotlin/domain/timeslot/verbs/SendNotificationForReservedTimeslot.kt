package domain.timeslot.verbs

import id.TimeslotId
import jodd.mail.Email
import jodd.mail.MailServer

object SendNotificationForReservedTimeslot : (TimeslotId) -> Unit {
	override fun invoke(id: TimeslotId) {
		val email = Email.create()
			.from("john@jodd.org")
			.to("igor.spasic@gmail.com")
			.subject("Hello!")
			.textMessage("Vaš termin je rezervisan");

		val smtpServer = MailServer.create()
			.host("localhost")
			.port(25)
			.buildSmtpMailServer()

		with(smtpServer.createSession()) {
			open()
			sendMail(email)
			close()
		}
	}
}
