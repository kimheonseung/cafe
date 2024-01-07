package com.devh.cafe.infrastructure.database.entity

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe


class AuthorityTest : DescribeSpec({
    describe("권한명이 주어질 때") {
        val givenName = "권한1"
        context("해당 권한명에 대한 권한객체를 생성한다.") {
            val authority = Authority(name=givenName)
            authority.name shouldBe givenName
        }
    }

    describe("권한명과 설명이 주어질 때") {
        val givenName = "권한1"
        val givenDescription = "설명"
        context("해당 권한명과 설명에 대한 권한객체를 생성한다.") {
            val authority = Authority(
                name=givenName,
                description=givenDescription
            )
            authority.name shouldBe givenName
            authority.description shouldBe givenDescription
        }
    }
})