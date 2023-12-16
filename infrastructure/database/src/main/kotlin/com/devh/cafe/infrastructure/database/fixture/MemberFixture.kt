package com.devh.cafe.infrastructure.database.fixture

import com.devh.cafe.infrastructure.database.entity.Member
import kotlin.math.min

val fixtureMemberKim = Member(id = 9001, username = "kim", password = "kim", name = "Kim", nickname = "mr.kim", email = "kim@kim.com")

fun newMember(): Member {
    return Member(
        username = "username-${Math.random()}".let { it.substring(min(it.length, 10)) },
        password = "password-${Math.random()}",
        name = "name-${Math.random()}".let { it.substring(min(it.length, 40)) },
        nickname = "nickname-${Math.random()}".let { it.substring(min(it.length, 40)) },
        email = "email-${Math.random()}"
    )
}
