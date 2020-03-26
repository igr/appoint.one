package domain

object App {
	val `$doctor` = DoctorUnit()
	val `$doctors` = DoctorsGroup()
	val `$matcher` = Matcher()

	fun start() {
		DatabaseFactory.init()
		`$matcher`.schedule()
	}

	fun end() {
		`$matcher`.cancel()
	}

}