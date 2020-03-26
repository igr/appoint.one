import infra.DatabaseFactory
import domain.Matcher

object App {

	fun start() {
		DatabaseFactory.init()
		Matcher.schedule()
	}

	fun end() {
		Matcher.cancel()
	}

}
