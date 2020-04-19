package domain

/**
 * Context functions that share the same value as a first argument.
 * Each function should be defined inside the context and curried.
 * Example:
 *
 * ```
 * class DoctorContext(id: DoctorId) : Context<DoctorId>(id) {
 *     val updateDoctorData = curry(UpdateDoctorData)
 * }
 * ```
 *
 * This requires a LOT of repetitions.
 */
abstract class ContextFns<T>(protected val value: T) {
	protected fun <IN, OUT> curry(fn: (T, IN) -> OUT): (IN) -> OUT {
		return { fn(this.value, it) }
	}
}
