package com.devh.cafe.infrastructure.database.entity

import com.devh.cafe.infrastructure.database.entity.enums.OptionType
import org.junit.jupiter.api.Assertions.assertAll
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class OptionTest {

    @Test
    fun 타입이_메뉴인_옵션을_1개_생성한다() {
        // given
        val givenMenu = Menu(
            name = "아메리카노",
            price = 4100L,
            available = true,
            category = Category(name = "커피"),
        )
        val givenName = "샷 옵션"
        val givenDisplayOrder = 1
        val givenType = OptionType.MENU
        // when
        val option = Option(
            name = givenName,
            displayOrder = givenDisplayOrder,
            type = OptionType.MENU,
            menu = givenMenu,
        )
        println(option)
        // then
        assertAll(
            { assertEquals(givenName, option.name) },
            { assertEquals(givenDisplayOrder, option.displayOrder) },
            { assertEquals(givenType, option.type) },
            { assertEquals(givenMenu.options[0], option) },
        )
    }

    @Test
    fun 타입이_메뉴인_옵션을_3개_생성한다() {
        // given
        val givenMenu = Menu(
            name = "아메리카노",
            price = 4100L,
            available = true,
            category = Category(name = "커피"),
        )
        val givenType = OptionType.MENU

        val givenName1 = "샷 옵션"
        val givenDisplayOrder1 = 1

        val givenName2 = "블렌딩 옵션"
        val givenDisplayOrder2 = 2

        val givenName3 = "디카페인 옵션"
        val givenDisplayOrder3 = 3

        // when
        val option1 = Option(
            name = givenName1,
            displayOrder = givenDisplayOrder1,
            type = givenType,
            menu = givenMenu,
        )
        val option2 = Option(
            name = givenName2,
            displayOrder = givenDisplayOrder2,
            type = givenType,
            menu = givenMenu,
        )
        val option3 = Option(
            name = givenName3,
            displayOrder = givenDisplayOrder3,
            type = givenType,
            menu = givenMenu,
        )
        println(option1)
        println(option2)
        println(option3)
        // then
        assertAll(
            { assertEquals(givenName1, option1.name) },
            { assertEquals(givenDisplayOrder1, option1.displayOrder) },
            { assertEquals(givenType, option1.type) },
            { assertEquals(givenMenu.options[0], option1) },

            { assertEquals(givenName2, option2.name) },
            { assertEquals(givenDisplayOrder2, option2.displayOrder) },
            { assertEquals(givenType, option2.type) },
            { assertEquals(givenMenu.options[1], option2) },

            { assertEquals(givenName3, option3.name) },
            { assertEquals(givenDisplayOrder3, option3.displayOrder) },
            { assertEquals(givenType, option3.type) },
            { assertEquals(givenMenu.options[2], option3) },
        )
    }

    @Test
    fun 타입이_카테고리인_옵션을_1개_생성한다() {
        // given
        val givenCategory = Category(name = "음료")
        val givenName = "아이스 옵션"
        val givenDisplayOrder = 1
        val givenType = OptionType.CATEGORY
        // when
        val option = Option(
            name = givenName,
            displayOrder = givenDisplayOrder,
            type = givenType,
            category = givenCategory,
        )
        println(option)
        // then
        assertAll(
            { assertEquals(givenName, option.name) },
            { assertEquals(givenDisplayOrder, option.displayOrder) },
            { assertEquals(givenType, option.type) },
            { assertEquals(givenCategory.options[0], option) },
        )
    }

    @Test
    fun 타입이_카테고리인_옵션을_3개_생성한다() {
        // given
        val givenCategory = Category(name = "음료")
        val givenType = OptionType.CATEGORY

        val givenName1 = "아이스 옵션"
        val givenDisplayOrder1 = 1

        val givenName2 = "텀블러 옵션"
        val givenDisplayOrder2 = 2

        val givenName3 = "사이즈 옵션"
        val givenDisplayOrder3 = 3
        // when
        val option1 = Option(
            name = givenName1,
            displayOrder = givenDisplayOrder1,
            type = givenType,
            category = givenCategory,
        )
        val option2 = Option(
            name = givenName2,
            displayOrder = givenDisplayOrder2,
            type = givenType,
            category = givenCategory,
        )
        val option3 = Option(
            name = givenName3,
            displayOrder = givenDisplayOrder3,
            type = givenType,
            category = givenCategory,
        )
        println(option1)
        println(option2)
        println(option3)
        // then
        assertAll(
            { assertEquals(givenName1, option1.name) },
            { assertEquals(givenDisplayOrder1, option1.displayOrder) },
            { assertEquals(givenType, option1.type) },
            { assertEquals(givenCategory.options[0], option1) },

            { assertEquals(givenName2, option2.name) },
            { assertEquals(givenDisplayOrder2, option2.displayOrder) },
            { assertEquals(givenType, option2.type) },
            { assertEquals(givenCategory.options[1], option2) },

            { assertEquals(givenName3, option3.name) },
            { assertEquals(givenDisplayOrder3, option3.displayOrder) },
            { assertEquals(givenType, option3.type) },
            { assertEquals(givenCategory.options[2], option3) },
        )
    }
}
