package domain

import kotlinx.coroutines.cancel
import scheduler.Scheduler

class Matcher{

	private val scheduler = Scheduler(1000)

	fun cancel() {
		scheduler.cancel()
	}

	fun schedule() {
		scheduler.start {
			println('.')
		}
	}
}