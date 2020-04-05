package server.db

import org.jetbrains.exposed.sql.Query
import org.jetbrains.exposed.sql.QueryBuilder

fun Query.toSQL(): String = prepareSQL(QueryBuilder(false))
