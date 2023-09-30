package com.devh.cafe.utility.jwt.data

import com.devh.cafe.utility.jwt.constant.JwtStatus

data class JwtData(
    val accessToken: String,
    val refreshToken: String,
    val status: JwtStatus = JwtStatus.VALID
)
