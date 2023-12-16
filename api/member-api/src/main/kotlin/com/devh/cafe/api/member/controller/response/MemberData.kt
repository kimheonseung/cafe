package com.devh.cafe.api.member.controller.response

data class MemberData(
    val id: Long? = null,
    val username: String,
    val name: String,
    val nickName: String,
    val email: String,
)
