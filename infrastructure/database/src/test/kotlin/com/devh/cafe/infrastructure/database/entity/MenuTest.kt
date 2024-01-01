package com.devh.cafe.infrastructure.database.entity

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.collections.shouldBeOneOf
import io.kotest.matchers.shouldBe

class MenuTest : DescribeSpec({

    describe("메뉴의 기본 정보로 메뉴가 생성될 때") {
        val givenName = "아메리카노"
        val givenPrice = 4100L
        val givenAvailable = true
        val givenCategory = Category(name = "카테고리")
        val menu = Menu(
            name = givenName,
            price = givenPrice,
            available = givenAvailable,
            category = givenCategory,
        )
        context("해당 정보에 대한 메뉴 객체를 생성한다.") {
            menu.name shouldBe givenName
            menu.price shouldBe givenPrice
            menu.available shouldBe givenAvailable
            menu.category shouldBe givenCategory
        }
        context("메뉴의 이름을 변경한다.") {
            val finalName = "변경된아메리카노"
            menu.changeName(finalName)

            menu.name shouldBe finalName
        }
        context("메뉴의 가격을 변경한다.") {
            val finalPrice = 4500L
            menu.changePrice(finalPrice)

            menu.price shouldBe finalPrice
        }
        context("메뉴의 할인가를 반영한다.") {
            val givenDiscountRate = 10
            val currentPrice = menu.price
            menu.adjustDiscount(givenDiscountRate)

            menu.price shouldBe currentPrice * (1 - (givenDiscountRate/100))
        }
        context("메뉴의 상태를 품절로 변경한다.") {
            menu.changeToSoldOut()

            menu.available shouldBe false
        }
        context("메뉴의 상태를 판매중으로 변경한다.") {
            menu.changeToOnSale()

            menu.available shouldBe true
        }
        context("메뉴의 카테고리를 변경한다.") {
            val finalCategory = Category(name = "변경카테고리")
            menu.changeCategory(finalCategory)

            menu.category shouldBe finalCategory
            givenCategory.menu.isEmpty() shouldBe true
            menu shouldBeOneOf finalCategory.menu
        }
    }
})
