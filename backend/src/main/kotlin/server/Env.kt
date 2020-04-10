package server

object Env {
	val REG_CODE = System.getenv("APP1_REG_CODE") ?: "A1"
	val JWT_SECRET = System.getenv("APP1_JWT_SECRET") ?: "xE1x1o1x8qflc1iYtcRd000OBLAC"
}
