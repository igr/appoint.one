import org.slf4j.Logger
import org.slf4j.LoggerFactory

/**
 * Defines logger.
 */
inline fun <reified T> T.logger(): Logger {
	return LoggerFactory.getLogger(T::class.java)
}

