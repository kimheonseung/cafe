package com.devh.cafe.api.member.repository

import com.devh.cafe.infrastructure.database.entity.Authority
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
class AuthorityRepositoryTest(
    @Autowired
    val authorityRepository: AuthorityRepository,
) {
    @BeforeAll
    fun 데이터_세팅() {
        authorityRepository.saveAll(
            listOf(
                Authority(name="A01"),
                Authority(name="A02"),
                Authority(name="A03"),
            )
        )
    }

    @Test
    fun 권한_조회() {
        // when
        val authorities = authorityRepository.findAll()
        // then
        authorities.forEach { println(it.name) }
        Assertions.assertEquals(3, authorities.size)
    }
}