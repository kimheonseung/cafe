package com.devh.cafe.api.member.repository

import com.devh.cafe.api.member.repository.configuration.RepositoryTest
import com.devh.cafe.infrastructure.database.entity.Member
import com.devh.cafe.infrastructure.database.fixture.newMember
import org.junit.jupiter.api.Assertions.assertAll
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

@RepositoryTest
class MemberRepositoryTest(
    @Autowired
    val memberRepository: MemberRepository,
) {
    @Test
    fun 사용자_필수_정보가_주어질_때_저장에_성공한다() {
        // given
        val givenMember = newMember()
        // when
        val savedMember = memberRepository.save(
            Member(
                username = givenMember.username,
                password = givenMember.password,
                name = givenMember.name,
                nickname = givenMember.nickname,
                email = givenMember.email)
        )
        // then
        assertAll(
            { assertNotNull(savedMember) },
            { assertEquals(givenMember.username, savedMember.username) },
            { assertEquals(givenMember.password, savedMember.password) },
            { assertEquals(givenMember.name, savedMember.name) },
            { assertEquals(givenMember.nickname, savedMember.nickname) },
            { assertEquals(givenMember.email, savedMember.email) },
        )
    }
}