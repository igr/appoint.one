package domain

/**
 * Annotation that describes 'targets' in the domain.
 * On targets, we have the `verbs` (i.e. functions).
 * Finally, user (i.e. a `subject`) may start using the domain.
 */
@kotlin.annotation.Target(AnnotationTarget.CLASS)
@kotlin.annotation.Retention(AnnotationRetention.SOURCE)
@kotlin.annotation.MustBeDocumented
annotation class TargetIs(val description: String)