package domain.admin.verbs

import jodd.mail.Email
import jodd.mail.MailServer

object SendTestMail : () -> Unit {
	override fun invoke() {
		val email = Email.create()
			.from("admin@podrskapsihoterapeuta.com")
			.to("igor.spasic@gmail.com")
			.subject("Zdravo!")
			.textMessage("Proba.");

		val smtpServer = MailServer.create()
			.host("localhost")
			.port(25)
			.auth("admin@podrskapsihoterapeuta.com", "pybp6YXa4xIh2H8a")
			.buildSmtpMailServer()

		with(smtpServer.createSession()) {
			open()
			sendMail(email)
			close()
		}
	}
}
