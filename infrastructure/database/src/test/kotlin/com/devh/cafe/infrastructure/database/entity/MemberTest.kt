package com.devh.cafe.infrastructure.database.entity

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.collections.shouldBeOneOf
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe

class MemberTest : DescribeSpec({

    describe("필수 회원 정보로 회원이 생성될 때") {
        val givenUsername = "회원아이디"
        val givenPassword = "비밀번호"
        val givenName = "이름"
        val givenNickname = "닉네임"
        val givenEmail = "메일@주소.com"
        val member = Member(
            username=givenUsername,
            password=givenPassword,
            name=givenName,
            nickname=givenNickname,
            email=givenEmail
        )
        context("해당 정보에 대한 회원 객체를 생성한다.") {
            member.username shouldBe givenUsername
            member.password shouldBe givenPassword
            member.name shouldBe givenName
            member.nickname shouldBe givenNickname
            member.email shouldBe givenEmail
            member.createDate shouldNotBe null
            member.updateDate shouldNotBe null
        }
        context("해당 정보에 대한 회원 객체의 초기 권한은 null이다.") {
            member.authority shouldBe null
        }
        context("기본 권한을 갱신한다.") {
            val authority = Authority(id=1, name="권한1")
            member.updateAuthority(authority)

            member.authority shouldNotBe null
            member.authority shouldBe authority
            member shouldBeOneOf authority.members
        }
        context("권한을 삭제한다.") {
            val authority = Authority(id=1, name="권한1")
            member.removeAuthority()

            member.authority shouldBe null
        }
    }
})
