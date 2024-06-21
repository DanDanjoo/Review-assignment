package com.teamsparta.assignment.infra.security.config.jwt

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.stereotype.Component
import java.nio.charset.StandardCharsets
import java.time.Duration
import java.time.Instant
import java.util.*


@Component
class JwtPlugin {

    companion object {
        const val ISSUER = "assignment"
        const val SECRET = "PO5o6c72FN672Fd31967VWbAWq4Ws5aZ"
        const val ACCESS_TOKEN_EXPIRATION_HOUR : Long = 168
    }


    fun generateAccessToken(subject: String, nickname: String): String{
        return generateToken(subject,nickname,Duration.ofHours(ACCESS_TOKEN_EXPIRATION_HOUR))

    }

    private fun generateToken(subject: String, nickname: String, expirationPeriod: Duration): String {
        val claims: Claims = Jwts.claims()
            .add(mapOf("nickname" to nickname))
            .build()

        val now = Instant.now()
        val key = Keys.hmacShaKeyFor(SECRET.toByteArray(StandardCharsets.UTF_8))

        return Jwts.builder()
            .subject(subject)
            .issuer(ISSUER)
            .issuedAt(Date.from(now))
            .expiration(Date.from(now.plus(expirationPeriod)))
            .claims(claims)
            .signWith(key)
            .compact()
    }
}




