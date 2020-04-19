package domain

abstract class Context<T>(protected val value: T) {
	protected fun <IN, OUT> curry(fn: (T, IN) -> OUT): (IN) -> OUT {
		return { fn(this.value, it) }
	}
}
