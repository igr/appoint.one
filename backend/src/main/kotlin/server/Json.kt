package server

import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration

/**
 * Default JSON implementation.
 */
val json = Json(JsonConfiguration.Stable.copy(prettyPrint = true))



