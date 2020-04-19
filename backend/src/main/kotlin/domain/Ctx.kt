package domain

/**
 * Generic context.
 */
class Ctx<T>(private val value: T) {

	companion object {
		fun <A> of(value: A): Ctx<A> {
			return Ctx(value)
		}
	}

	// map value to a new one

	fun <OUT> set(fn: () -> OUT): Ctx<OUT> {
		return of(fn.invoke())
	}

	fun <OUT> map(fn: (T) -> OUT): Ctx<OUT> {
		return of(fn.invoke(this.value))
	}

	fun <IN, OUT> map(fn: (T, IN) -> OUT, argSupplier: () -> IN): Ctx<OUT> {
		return of(fn.invoke(this.value, argSupplier()))
	}

	// run (peek) and don't modify the context

	fun <IN, OUT> run(fn: (T, IN) -> OUT, argSupplier: () -> IN): Ctx<T> {
		fn.invoke(this.value, argSupplier())
		return this
	}

	// consumers

	fun use(consumer: (T) -> Unit) {
		consumer(this.value)
	}

	suspend fun useS(consumer: suspend (T) -> Unit) {
		consumer(this.value)
	}
}
