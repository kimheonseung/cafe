package com.devh.cafe.infrastructure.database.entity

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class MemberTest {
    @Test
    fun 필수_정보로_회원_객체_생성() {
        // given
        val givenUsername = "회원아이디"
        val givenPassword = "비밀번호"
        val givenName = "이름"
        val givenNickname = "닉네임"
        val givenEmail = "메일@주소.com"
        // when
        val member = Member(
            username=givenUsername,
            password=givenPassword,
            name=givenName,
            nickname=givenNickname,
            email=givenEmail
        )
        // then
        Assertions.assertAll(
            { Assertions.assertEquals(member.username, givenUsername) },
            { Assertions.assertEquals(member.password, givenPassword) },
            { Assertions.assertEquals(member.name, givenName) },
            { Assertions.assertEquals(member.nickname, givenNickname) },
            { Assertions.assertEquals(member.email, givenEmail) },
            { Assertions.assertNotNull(member.createDate) },
            { Assertions.assertNotNull(member.updateDate) },
        )
    }
}