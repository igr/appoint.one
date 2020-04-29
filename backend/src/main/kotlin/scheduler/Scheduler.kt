package scheduler

import kotlinx.coroutines.*
import server.logger
import java.util.concurrent.Executors
import kotlin.coroutines.CoroutineContext

object Scheduler : CoroutineScope {

	private val log = logger();

	private val job = Job()
	private val singleThreadExecutor = Executors.newSingleThreadExecutor()

	override val coroutineContext: CoroutineContext
		get() = job + singleThreadExecutor.asCoroutineDispatcher()

	private var tasks = mutableListOf<() -> Unit>()

	fun registerTask(task: () -> Unit) {
		tasks.add(task);
	}

	fun stop() {
		job.cancel()
		singleThreadExecutor.shutdown()
	}

	fun start(intervalMillis: Long,
	          initialDelayMilis: Long = 0) = launch {
		log.info("Scheduler running at ${intervalMillis}ms")

		delay(initialDelayMilis)
		while (isActive) {

			val batch = tasks
			tasks = mutableListOf()

			if (batch.isNotEmpty()) {
				log.debug("Running ${batch.size} tasks")

				batch.forEach {
					try {
						it()
					} catch (e: Exception) {
						log.error("Error", e);
					}
				}
			}
			delay(intervalMillis)
		}
	}
}
