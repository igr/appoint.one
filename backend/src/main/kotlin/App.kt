import infra.DatabaseFactory
import scheduler.Scheduler

object App {
	private val scheduler = Scheduler(1);

	fun start() {
		DatabaseFactory.init()
		scheduler.start()
	}

	fun end() {
		scheduler.stop()
	}

}
