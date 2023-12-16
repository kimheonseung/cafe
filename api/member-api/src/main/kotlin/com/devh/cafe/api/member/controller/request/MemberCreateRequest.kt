package com.devh.cafe.api.member.controller.request

data class MemberCreateRequest(
    val username: String,
    val password: String,
    val name: String,
    val nickName: String,
    val email: String,
)
