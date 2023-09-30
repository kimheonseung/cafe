package com.devh.cafe.infrastructure.database.entity

import org.junit.jupiter.api.Assertions.assertAll
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class MenuTest {

    @Test
    fun 메뉴_생성() {
        // given
        val givenName = "아메리카노"
        val givenPrice = 4100L
        val givenAvailable = true
        val givenCategory = Category(name = "카테고리")
        // when
        val menu = Menu(
            name = givenName,
            price = givenPrice,
            available = givenAvailable,
            category = givenCategory,
        )
        // then
        assertAll(
            { assertEquals(givenName, menu.name) },
            { assertEquals(givenPrice, menu.price) },
            { assertEquals(givenAvailable, menu.available) },
            { assertEquals(givenCategory, menu.category) },
        )
    }

    @Test
    fun 메뉴_이름_변경() {
        // given
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
        val finalName = "변경된아메리카노"
        // when
        menu.changeName(finalName)
        // then
        assertAll(
            { assertEquals(finalName, menu.name) },
            { assertEquals(givenPrice, menu.price) },
            { assertEquals(givenAvailable, menu.available) },
            { assertEquals(givenCategory, menu.category) },
        )
    }

    @Test
    fun 메뉴_가격_변경() {
        // given
        val givenName = "아메리카노"
        val givenPrice = 4100L
        val givenAvailable = true
        val givenCategory = Category(name = "카테고리")
        val menu = Menu(
            name = givenName,
            price = givenPrice,
            available = givenAvailable,
            category = givenCategory
        )
        val finalPrice = 4500L
        // when
        menu.changePrice(finalPrice)
        // then
        assertAll(
            { assertEquals(givenName, menu.name) },
            { assertEquals(finalPrice, menu.price) },
            { assertEquals(givenAvailable, menu.available) },
            { assertEquals(givenCategory, menu.category) },
        )
    }

    @Test
    fun 메뉴_가격_할인가_적용() {
        // given
        val givenName = "아메리카노"
        val givenPrice = 5000L
        val givenAvailable = true
        val givenCategory = Category(name = "카테고리")
        val givenDiscountRate = 10
        val menu = Menu(
            name = givenName,
            price = givenPrice,
            available = givenAvailable,
            category = givenCategory
        )
        // when
        menu.adjustDiscount(givenDiscountRate)
        // then
        assertAll(
            { assertEquals(givenName, menu.name) },
            { assertEquals(givenPrice * (1 - givenDiscountRate), menu.price) },
            { assertEquals(givenAvailable, menu.available) },
            { assertEquals(givenCategory, menu.category) },
        )
    }

    @Test
    fun 메뉴_상태_품절로_변경() {
        // given
        val givenName = "아메리카노"
        val givenPrice = 4100L
        val givenAvailable = true
        val givenCategory = Category(name = "카테고리")
        val menu = Menu(
            name = givenName,
            price = givenPrice,
            available = givenAvailable,
            category = givenCategory
        )
        // when
        menu.changeToSoldOut()
        // then
        assertAll(
            { assertEquals(givenName, menu.name) },
            { assertEquals(givenPrice, menu.price) },
            { assertFalse(menu.available) },
            { assertEquals(givenCategory, menu.category) },
        )
    }

    @Test
    fun 메뉴_상태_판매중으로_변경() {
        // given
        val givenName = "아메리카노"
        val givenPrice = 4100L
        val givenAvailable = false
        val givenCategory = Category(name = "카테고리")
        val menu = Menu(
            name = givenName,
            price = givenPrice,
            available = givenAvailable,
            category = givenCategory
        )
        // when
        menu.changeToOnSale()
        // then
        assertAll(
            { assertEquals(givenName, menu.name) },
            { assertEquals(givenPrice, menu.price) },
            { assertTrue(menu.available) },
            { assertEquals(givenCategory, menu.category) },
        )
    }

    @Test
    fun 메뉴_카테고리_변경() {
        // given
        val givenName = "아메리카노"
        val givenPrice = 4100L
        val givenAvailable = true
        val givenCategory1 = Category(name = "카테고리")
        val givenCategory2 = Category(name = "변경카테고리")
        val givenMenu = Menu(
            name = givenName,
            price = givenPrice,
            available = givenAvailable,
            category = givenCategory1
        )
        // when
        givenMenu.changeCategory(givenCategory2)
        // then
        assertAll(
            { assertEquals(givenName, givenMenu.name) },
            { assertEquals(givenPrice, givenMenu.price) },
            { assertTrue(givenMenu.available) },
            { assertEquals(0, givenCategory1.menu.size) },
            { assertEquals(givenMenu, givenCategory2.menu[0]) },
            { assertEquals(givenCategory2, givenMenu.category) },
        )
    }
}
