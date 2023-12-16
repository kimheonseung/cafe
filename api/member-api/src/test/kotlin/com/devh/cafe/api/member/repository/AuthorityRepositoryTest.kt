package com.devh.cafe.api.member.repository

import com.devh.cafe.api.member.repository.configuration.RepositoryTest
import com.devh.cafe.infrastructure.database.entity.Authority
import com.devh.cafe.infrastructure.database.fixture.fixtureAuthorityNormal
import com.devh.cafe.infrastructure.database.fixture.newAuthority
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertAll
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

@RepositoryTest
class AuthorityRepositoryTest(
    @Autowired
    val authorityRepository: AuthorityRepository,
) {
    @Test
    fun 권한_이름이_주어질_때_저장에_성공한다() {
        // given
        val givenAuthorityName = newAuthority().name
        // when
        val savedAuthority = authorityRepository.save(Authority(name = givenAuthorityName))
        println(savedAuthority)
        // then
        assertAll(
            { assertNotNull(savedAuthority) },
            { assertEquals(givenAuthorityName, savedAuthority.name) },
        )
    }

    @Test
    fun 권한_이름이_주어질_때_조회에_성공한다() {
        // given
        val givenAuthorityName = fixtureAuthorityNormal.name
        // when
        val foundAuthority = authorityRepository.findByName(givenAuthorityName).get()
        // then
        assertAll(
            { assertNotNull(foundAuthority) },
            { assertEquals(givenAuthorityName, foundAuthority.name) },
        )
    }
}
