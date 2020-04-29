package appoint1.annotations

/**
 * Marker for generated fields
 */
inline fun GENERATED(): Nothing = throw NotImplementedError()

@Retention(AnnotationRetention.SOURCE)
@Target(AnnotationTarget.PROPERTY)
annotation class IdGen