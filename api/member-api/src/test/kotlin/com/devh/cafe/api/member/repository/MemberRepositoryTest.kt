package com.devh.cafe.api.member.repository

import com.devh.cafe.infrastructure.database.entity.Member
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles

@SpringBootTest
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class MemberRepositoryTest(
    @Autowired
    val memberRepository: MemberRepository,
) {

    @BeforeAll
    fun 데이터_세팅() {
        val members = mutableListOf<Member>()

        for (i in 1..100) {
            members.add(
                Member(
                    username = "username_${i}",
                    password = "password_${i}",
                    name = "name_${i}",
                    nickname = "nickname_${i}",
                    email = "email_${i}",
                )
            )
        }

        memberRepository.saveAll(members)
    }

    @Test
    fun 조회_테스트_3개() {
        // given
        val givenLimit = 3
        // when
        val members = memberRepository.selectAllLimit(givenLimit)
        // then
        members.forEach { println("""
            id: ${it.id}, 
            username: ${it.username}, 
            password: ${it.password},
            name: ${it.name},
            nickname: ${it.nickname},
            email: ${it.email}
            ==================================""".trimIndent()) }
        Assertions.assertAll(
            { Assertions.assertTrue(members.isNotEmpty()) },
            { Assertions.assertEquals(givenLimit, members.size) },
        )
    }
}