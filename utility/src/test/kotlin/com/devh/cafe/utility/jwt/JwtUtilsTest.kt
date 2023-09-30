package com.devh.cafe.utility.jwt

import com.devh.cafe.utility.jwt.constant.JwtStatus
import com.devh.cafe.utility.jwt.data.JwtData
import io.jsonwebtoken.JwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.util.*

class JwtUtilsTest {

    private val issuer = "issuer"
    private val secret = "secret"

    private val jwtUtils: JwtUtils = JwtUtils(
        header = "header",
        secret = this.secret,
        issuer = this.issuer,
        expireAccess = "60",
        expireRefresh = "180",
    )

    private fun validTokenFixture(subject: String, now: Date): String {
        return Jwts.builder()
            .setIssuer(this.issuer)
            .setSubject(subject)
            .setIssuedAt(now)
            .setExpiration(Date(now.time + (30_000L)))
            .signWith(SignatureAlgorithm.HS256, this.secret.toByteArray(Charsets.UTF_8))
            .compact()
    }

    private fun expiredTokenFixture(subject: String, now: Date): String {
        return Jwts.builder()
            .setIssuer(this.issuer)
            .setSubject(subject)
            .setIssuedAt(now)
            .setExpiration(Date(now.time - (30_000L)))
            .signWith(SignatureAlgorithm.HS256, this.secret.toByteArray(Charsets.UTF_8))
            .compact()
    }

    @Test
    fun 토큰_생성_테스트() {
        // given
        val givenSubject = "subject"
        val givenCustomClaims = mutableMapOf(Pair("authorities", setOf("a01", "a02")))
        // when
        val jwtData = jwtUtils.generate(subject = givenSubject, customClaims = givenCustomClaims)
        val claims = jwtUtils.parseClaims(jwtData.accessToken)
        // then
        println(claims)
        println(givenCustomClaims.toString())
        println(claims.getValue(jwtUtils.customClaimsKey).toString())
        Assertions.assertAll(
            { Assertions.assertNotNull(jwtData) },
            { Assertions.assertNotNull(claims) },
            { Assertions.assertEquals(givenSubject, claims.subject) },
            { Assertions.assertTrue(givenCustomClaims.toString() == claims.getValue(jwtUtils.customClaimsKey).toString()) },
        )
    }

    @Test
    fun 유효한_access_token_및_refresh_token() {
        // eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c
        // given
        val givenSubject = "subject"
        val givenCustomClaims = mutableMapOf(Pair("authorities", setOf("a01", "a02")))
        val jwtData = jwtUtils.generate(subject = givenSubject, customClaims = givenCustomClaims)
        // when
        val accessTokenStatus = jwtUtils.validateAccessToken(jwtData.accessToken)
        val refreshTokenStatus = jwtUtils.validateAccessToken(jwtData.refreshToken)
        // then
        Assertions.assertAll(
            { Assertions.assertEquals(JwtStatus.VALID, accessTokenStatus) },
            { Assertions.assertEquals(JwtStatus.VALID, refreshTokenStatus) },
        )
    }

    @Test
    fun 토큰_갱신() {
        // given
        val now = Date()
        val givenSubject = "subject"
        val givenInvalidAccessToken = expiredTokenFixture(givenSubject, now)
        val givenValidRefreshToken = validTokenFixture(givenSubject, now)
        val jwtData = JwtData(accessToken = givenInvalidAccessToken, refreshToken = givenValidRefreshToken, JwtStatus.VALID)
        // when
        val refreshedJwtData = jwtUtils.refresh(jwtData)
        // then
        Assertions.assertAll(
            { Assertions.assertEquals(JwtStatus.VALID, refreshedJwtData.status) },
            { Assertions.assertNotEquals(givenInvalidAccessToken, refreshedJwtData.accessToken) },
            { Assertions.assertNotEquals(givenValidRefreshToken, refreshedJwtData.refreshToken) },
        )
    }

    @Test
    fun 토큰_갱신_예외_access_token_유효_refresh_token_유효() {
        // given
        val givenSubject = "subject"
        val now = Date()
        val givenValidAccessToken = validTokenFixture(givenSubject, now)
        val givenValidRefreshToken = validTokenFixture(givenSubject, now)
        val jwtData = JwtData(accessToken = givenValidAccessToken, refreshToken = givenValidRefreshToken)
        // when & then
        val assertThrows = Assertions.assertThrows(
            JwtException::class.java
        ) { jwtUtils.refresh(jwtData) }
        Assertions.assertEquals("갱신할 필요가 없는 토큰입니다.", assertThrows.message)
    }

    @Test
    fun 토큰_갱신_예외_access_token_유효_refresh_token_유효하지_않음() {
        // given
        val givenSubject = "subject"
        val now = Date()
        val givenValidAccessToken = validTokenFixture(givenSubject, now)
        val givenValidRefreshToken = expiredTokenFixture(givenSubject, now)
        val jwtData = JwtData(accessToken = givenValidAccessToken, refreshToken = givenValidRefreshToken)
        // when & then
        val assertThrows = Assertions.assertThrows(
            JwtException::class.java
        ) { jwtUtils.refresh(jwtData) }
        Assertions.assertEquals("갱신할 필요가 없는 토큰입니다.", assertThrows.message)
    }

    @Test
    fun 토큰_갱신_예외_access_token_유효하지_않음_refresh_token_유효하지_않음() {
        // given
        val givenSubject = "subject"
        val now = Date()
        val givenValidAccessToken = expiredTokenFixture(givenSubject, now)
        val givenValidRefreshToken = expiredTokenFixture(givenSubject, now)
        val jwtData = JwtData(accessToken = givenValidAccessToken, refreshToken = givenValidRefreshToken)
        // when & then
        val assertThrows = Assertions.assertThrows(
            JwtException::class.java
        ) { jwtUtils.refresh(jwtData) }
        Assertions.assertEquals("다시 로그인이 필요합니다.", assertThrows.message)
    }
}