package scheduler

import kotlinx.coroutines.*
import logger
import java.util.concurrent.Executors
import kotlin.coroutines.CoroutineContext

class Scheduler(
	private val intervalMillis: Long,
	private val initialDelayMilis: Long = 0) : CoroutineScope {

	val log = logger();

	private val job = Job()
	private val singleThreadExecutor = Executors.newSingleThreadExecutor()

	override val coroutineContext: CoroutineContext
		get() = job + singleThreadExecutor.asCoroutineDispatcher()

	private val tasks = mutableListOf<() -> Unit>()

	fun registerTask(task: () -> Unit) {
		tasks.add(task);
	}

	fun stop() {
		job.cancel()
		singleThreadExecutor.shutdown()
	}

	fun start() = launch {
		delay(initialDelayMilis)
		while (isActive) {
			tasks.forEach {
				try {
					it()
				} catch (e: Exception) {
					log.error("Error", e);
				}
			};
			delay(intervalMillis)
		}
	}
}