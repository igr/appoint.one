package domain

import scheduler.Scheduler

object Matcher {
	private val scheduler = Scheduler(1000)

	fun cancel() {
		scheduler.stop()
	}

	fun schedule() {
		scheduler.start {
			println('.')
		}
	}
}