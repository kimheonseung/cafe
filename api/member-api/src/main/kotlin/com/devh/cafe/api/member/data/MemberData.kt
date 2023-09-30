package com.devh.cafe.api.member.data

data class MemberData(
    val id: Long,
    val username: String,
    var password: String,
    val name: String,
    var nickname: String,
    val authorities: Set<AuthorityData>,
) {

}