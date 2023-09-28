package com.devh.cafe.infrastructure.database.entity

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test


class AuthorityTest {
    @Test
    fun 이름만_설정하여_권한_객체_생성() {
        // given
        val givenName = "권한1"
        // when
        val authority = Authority(name=givenName)
        // then
        Assertions.assertEquals(authority.name, givenName)
    }

    @Test
    fun 이름과_설명을_설정하여_권한_객체_생성() {
        // given
        val givenName = "권한1"
        val givenDescription = "설명"
        // when
        val authority = Authority(
            name=givenName,
            description=givenDescription
        )
        // then
        Assertions.assertAll(
            { Assertions.assertEquals(authority.name, givenName) },
            { Assertions.assertEquals(authority.description, givenDescription) },
        )
    }
}