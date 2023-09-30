package com.devh.cafe.utility.jwt

import com.devh.cafe.utility.jwt.constant.JwtStatus
import com.devh.cafe.utility.jwt.data.JwtData
import io.jsonwebtoken.Claims
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.JwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.MalformedJwtException
import io.jsonwebtoken.PrematureJwtException
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.SignatureException
import io.jsonwebtoken.UnsupportedJwtException
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.time.Duration
import java.util.*

@Component
class JwtUtils(
    @Value("{jwt.secret}")
    private val secret: String,
    @Value("{jwt.issuer}")
    private val issuer: String,
    @Value("{jwt.expire.access}")
    private val expireAccess: String,
    @Value("{jwt.expire.refresh}")
    private val expireRefresh: String,
) {
    private val log: Logger = LoggerFactory.getLogger(this.javaClass)!!
    private val secretBytes: ByteArray = secret.toByteArray(Charsets.UTF_8)
    private val signatureAlgorithm: SignatureAlgorithm = SignatureAlgorithm.HS256
    val customClaimsKey: String = "customClaims"

    fun parseClaims(token: String): Claims {
        return Jwts.parser()
            .setSigningKey(secretBytes)
            .parseClaimsJws(token)
            .body
    }

    fun generate(subject: String, customClaims: Any): JwtData {
        val now = Date()
        val accessToken = Jwts.builder()
            .setIssuer(this.issuer)
            .setIssuedAt(now)
            .setSubject(subject)
            .setExpiration(Date(now.time + Duration.ofSeconds(this.expireAccess.toLong()).toMillis()))
            .signWith(signatureAlgorithm, secretBytes)
            .addClaims(
                mutableMapOf(Pair(customClaimsKey, customClaims))
            )
            .compact()

        val refreshToken = Jwts.builder()
            .setIssuer(this.issuer)
            .setIssuedAt(now)
            .setSubject(subject)
            .setExpiration(Date(now.time + Duration.ofSeconds(this.expireRefresh.toLong()).toMillis()))
            .signWith(signatureAlgorithm, secretBytes)
            .compact()

        return JwtData(
            accessToken = accessToken,
            refreshToken = refreshToken
        )
    }

    fun refresh(jwtData: JwtData): JwtData {
        val accessTokenStatus = validateAccessToken(jwtData.accessToken)
        val refreshTokenStatus = validateRefreshToken(jwtData.refreshToken)

        if (JwtStatus.VALID == accessTokenStatus) {
            throw JwtException("갱신할 필요가 없는 토큰입니다.")
        }

        if (JwtStatus.INVALID == refreshTokenStatus || JwtStatus.EXPIRED == refreshTokenStatus) {
            throw JwtException("다시 로그인이 필요합니다.")
        }

        try {
            val claims = parseClaims(jwtData.refreshToken)
            val subject = claims.subject
            return generate(
                subject = subject,
                customClaims = try { claims.getValue(customClaimsKey) } catch (e: Exception) { emptyMap<String, Any>() }
            )
        } catch (e: Exception) {
            log.error("토큰 갱신 오류: ", e)
            throw JwtException("토큰 갱신 오류: ${e.message}")
        }
    }

    fun validateAccessToken(accessToken: String): JwtStatus {
        try {
            val claims = parseClaims(accessToken)

            val issuer = claims.issuer
            if (issuer.isNullOrBlank()) {
                throw MalformedJwtException("유효하지 않은 issuer: $issuer")
            }

            val subject = claims.subject
            if (subject.isNullOrBlank()) {
                throw MalformedJwtException("유효하지 않은 subject: $subject")
            }

            return JwtStatus.VALID
        } catch (eje: ExpiredJwtException) {
            log.warn("만료된 access token: $accessToken")
            return JwtStatus.EXPIRED
        } catch (e: Exception) {
            return when (e) {
                is MalformedJwtException,
                is PrematureJwtException,
                is SignatureException,
                is UnsupportedJwtException -> {
                    log.warn("유효하지 않은 access token: $accessToken")
                    JwtStatus.INVALID
                }

                else -> {
                    log.error("Unknown exception. ", e)
                    JwtStatus.INVALID
                }
            }
        }
    }

    fun validateRefreshToken(refreshToken: String): JwtStatus {
        try {
            val claims = parseClaims(refreshToken)

            val issuer = claims.issuer
            if (issuer.isNullOrBlank()) {
                throw MalformedJwtException("유효하지 않은 issuer: $issuer")
            }

            return JwtStatus.VALID
        } catch (eje: ExpiredJwtException) {
            log.warn("만료된 refresh token: $refreshToken")
            return JwtStatus.EXPIRED
        } catch (e: Exception) {
            return when (e) {
                is MalformedJwtException,
                is PrematureJwtException,
                is SignatureException,
                is UnsupportedJwtException -> {
                    log.warn("유효하지 않은 refresh token: $refreshToken")
                    JwtStatus.INVALID
                }

                else -> {
                    log.error("Unknown exception. ", e)
                    JwtStatus.INVALID
                }
            }
        }
    }
}