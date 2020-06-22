package domain

/**
 * Generic context that executes verbs.
 * This context supports functions with 1 or 2 arguments.
 * The first argument is always the context value.
 * There is no particular reason for this except to somewhat
 * speed up the development time, due to urgency of this project.
 * Next, exceptions are not handled here. Since all verbs are executed
 * in the web actions, they will be handled there.
 */
class Ctx<T>(val value: T) {

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

	inline fun use(consumer: (T) -> Unit) {
		consumer(this.value)
	}

}
