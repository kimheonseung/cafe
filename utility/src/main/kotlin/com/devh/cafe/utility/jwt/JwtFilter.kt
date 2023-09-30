package com.devh.cafe.utility.jwt

import com.devh.cafe.utility.jwt.constant.JwtStatus
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.web.filter.OncePerRequestFilter

class JwtFilter(
    @Autowired
    private val jwtUtils: JwtUtils,

    @Value("{jwt.header}")
    private val header: String,

    @Value("{jwt.cookie.access}")
    private val accessTokenCookieKey: String,

    @Value("{jwt.cookie.refresh}")
    private val refreshTokenCookieKey: String,
) : OncePerRequestFilter() {
    private val log: Logger = LoggerFactory.getLogger(this.javaClass)!!

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        var accessTokenStatus = JwtStatus.INVALID
        var refreshTokenStatus = JwtStatus.INVALID

        request.cookies.forEach {
            val cookieKey = it.name
            if (cookieKey == accessTokenCookieKey) {
                accessTokenStatus = jwtUtils.validateAccessToken(it.value)
            }

            if (cookieKey == refreshTokenCookieKey) {
                refreshTokenStatus = jwtUtils.validateRefreshToken(it.value)
            }
        }

        log.debug("accessToken: {}, refreshToken: {}", accessTokenStatus, refreshTokenStatus)

        val pass = JwtStatus.VALID == accessTokenStatus && JwtStatus.VALID == refreshTokenStatus
        if (pass) {
            filterChain.doFilter(request, response)
        }

        val loginRequired = JwtStatus.VALID != accessTokenStatus && JwtStatus.VALID != refreshTokenStatus



    }
}
