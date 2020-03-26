package scheduler

import kotlinx.coroutines.*
import java.util.concurrent.Executors
import kotlin.coroutines.CoroutineContext

class Scheduler(val interval: Long, val initialDelay: Long = 0) : CoroutineScope {
	private val job = Job()

	private val singleThreadExecutor = Executors.newSingleThreadExecutor()

	override val coroutineContext: CoroutineContext
		get() = job + singleThreadExecutor.asCoroutineDispatcher()


	fun stop() {
		job.cancel()
		singleThreadExecutor.shutdown()
	}

	fun start(task: () -> Unit) = launch {
		delay(initialDelay)
		while (isActive) {
			task()
			delay(interval)
		}
	}
}