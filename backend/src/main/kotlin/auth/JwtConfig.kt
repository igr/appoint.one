package auth

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import domain.user.User
import server.Env
import java.util.*

object JwtConfig {
	private const val issuer = "oblac.com"
	const val realm = issuer
	private const val validityInMs = 3_600_000 * 10 // 10 hours
	private val algorithm = Algorithm.HMAC512(Env.JWT_SECRET)

	val verifier: JWTVerifier = JWT
		.require(algorithm)
		.withIssuer(issuer)
		.withSubject("Authentication")
		.build()

	/**
	 * Produces a token for this combination of User and Account.
	 */
	fun makeToken(user: User): String = JWT.create()
		.withSubject("Authentication")
		.withIssuer(issuer)
		.withClaim("id", user.id.value)
		.withClaim("name", user.name)
		.withClaim("role", user.role.value)
		.withExpiresAt(getExpiration())
		.sign(algorithm)

    /**
     * Calculates the expiration Date based on current time + the given validity.
     */
    private fun getExpiration() = Date(System.currentTimeMillis() + validityInMs)
}

