package com.devh.cafe.infrastructure.database.entity

import com.devh.cafe.infrastructure.database.entity.enums.OptionType
import org.junit.jupiter.api.Assertions.assertAll
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class SubOptionTest {

    @Test
    fun 타입이_메뉴인_옵션의_하위_옵션을_1개_생성한다() {
        // given
        val givenMenu = Menu(
            name = "아메리카노",
            price = 4100L,
            available = true,
            category = Category(name = "커피"),
        )
        val givenOption = Option(
            name = "샷 옵션",
            displayOrder = 1,
            type = OptionType.MENU,
            menu = givenMenu,
        )
        val givenName = "1샷 추가"
        val givenPrice = 500L
        val givenDisplayOrder = 1
        // when
        val subOption = SubOption(
            name = givenName,
            price = givenPrice,
            displayOrder = givenDisplayOrder,
            option = givenOption
        )
        println(subOption)
        // then
        assertAll(
            { assertEquals(givenName, subOption.name) },
            { assertEquals(givenPrice, subOption.price) },
            { assertEquals(givenDisplayOrder, subOption.displayOrder) },
            { assertEquals(givenOption, subOption.option) },
            { assertEquals(givenOption.type, subOption.option.type) },
        )
    }

    @Test
    fun 타입이_메뉴인_옵션의_하위_옵션을_3개_생성한다() {
        // given
        val givenMenu = Menu(
            name = "아메리카노",
            price = 4100L,
            available = true,
            category = Category(name = "커피"),
        )
        val givenOption = Option(
            name = "샷 옵션",
            displayOrder = 1,
            type = OptionType.MENU,
            menu = givenMenu,
        )

        val givenName1 = "1샷 추가"
        val givenPrice1 = 500L
        val givenDisplayOrder1 = 1

        val givenName2 = "2샷 추가"
        val givenPrice2 = 1000L
        val givenDisplayOrder2 = 2

        val givenName3 = "1/2 샷"
        val givenPrice3 = 0L
        val givenDisplayOrder3 = 3

        // when
        val subOption1 = SubOption(
            name = givenName1,
            price = givenPrice1,
            displayOrder = givenDisplayOrder1,
            option = givenOption
        )
        val subOption2 = SubOption(
            name = givenName2,
            price = givenPrice2,
            displayOrder = givenDisplayOrder2,
            option = givenOption
        )
        val subOption3 = SubOption(
            name = givenName3,
            price = givenPrice3,
            displayOrder = givenDisplayOrder3,
            option = givenOption
        )
        println(subOption1)
        println(subOption2)
        println(subOption3)
        // then
        assertAll(
            { assertEquals(givenName1, subOption1.name) },
            { assertEquals(givenPrice1, subOption1.price) },
            { assertEquals(givenDisplayOrder1, subOption1.displayOrder) },
            { assertEquals(givenOption, subOption1.option) },
            { assertEquals(givenOption.type, subOption1.option.type) },

            { assertEquals(givenName2, subOption2.name) },
            { assertEquals(givenPrice2, subOption2.price) },
            { assertEquals(givenDisplayOrder2, subOption2.displayOrder) },
            { assertEquals(givenOption, subOption2.option) },
            { assertEquals(givenOption.type, subOption2.option.type) },

            { assertEquals(givenName3, subOption3.name) },
            { assertEquals(givenPrice3, subOption3.price) },
            { assertEquals(givenDisplayOrder3, subOption3.displayOrder) },
            { assertEquals(givenOption, subOption3.option) },
            { assertEquals(givenOption.type, subOption3.option.type) },
        )
    }

    @Test
    fun 타입이_메뉴인_2개의_옵션에_각각_1개_2개의_하위_옵션을_생성한다() {
        // given
        val givenMenu = Menu(
            name = "아메리카노",
            price = 4100L,
            available = true,
            category = Category(name = "커피"),
        )
        val givenOption1 = Option(
            name = "샷 옵션",
            displayOrder = 1,
            type = OptionType.MENU,
            menu = givenMenu,
        )
        val givenOption2 = Option(
            name = "디카페인 옵션",
            displayOrder = 2,
            type = OptionType.MENU,
            menu = givenMenu,
        )

        val givenName1 = "1샷 추가"
        val givenPrice1 = 500L
        val givenDisplayOrder1 = 1

        val givenName2 = "2샷 추가"
        val givenPrice2 = 1000L
        val givenDisplayOrder2 = 2

        val givenName3 = "디카페인"
        val givenPrice3 = 500L
        val givenDisplayOrder3 = 1

        // when
        val subOption1 = SubOption(
            name = givenName1,
            price = givenPrice1,
            displayOrder = givenDisplayOrder1,
            option = givenOption1
        )
        val subOption2 = SubOption(
            name = givenName2,
            price = givenPrice2,
            displayOrder = givenDisplayOrder2,
            option = givenOption1
        )
        val subOption3 = SubOption(
            name = givenName3,
            price = givenPrice3,
            displayOrder = givenDisplayOrder3,
            option = givenOption2
        )
        println(subOption1)
        println(subOption2)
        println(subOption3)
        // then
        assertAll(
            { assertEquals(givenName1, subOption1.name) },
            { assertEquals(givenPrice1, subOption1.price) },
            { assertEquals(givenDisplayOrder1, subOption1.displayOrder) },
            { assertEquals(givenOption1, subOption1.option) },
            { assertEquals(givenOption1.type, subOption1.option.type) },

            { assertEquals(givenName2, subOption2.name) },
            { assertEquals(givenPrice2, subOption2.price) },
            { assertEquals(givenDisplayOrder2, subOption2.displayOrder) },
            { assertEquals(givenOption1, subOption2.option) },
            { assertEquals(givenOption1.type, subOption2.option.type) },

            { assertEquals(givenName3, subOption3.name) },
            { assertEquals(givenPrice3, subOption3.price) },
            { assertEquals(givenDisplayOrder3, subOption3.displayOrder) },
            { assertEquals(givenOption2, subOption3.option) },
            { assertEquals(givenOption2.type, subOption3.option.type) },
        )
    }

    @Test
    fun 타입이_카테고리인_옵션의_하위_옵션을_1개_생성한다() {
        // given
        val givenMenu = Menu(
            name = "아메리카노",
            price = 4100L,
            available = true,
            category = Category(name = "음료"),
        )
        val givenOption = Option(
            name = "아이스 옵션",
            displayOrder = 1,
            type = OptionType.CATEGORY,
            menu = givenMenu,
        )
        val givenName = "아이스"
        val givenPrice = 500L
        val givenDisplayOrder = 1
        // when
        val subOption = SubOption(
            name = givenName,
            price = givenPrice,
            displayOrder = givenDisplayOrder,
            option = givenOption
        )
        println(subOption)
        // then
        assertAll(
            { assertEquals(givenName, subOption.name) },
            { assertEquals(givenPrice, subOption.price) },
            { assertEquals(givenDisplayOrder, subOption.displayOrder) },
            { assertEquals(givenOption, subOption.option) },
            { assertEquals(givenOption.type, subOption.option.type) },
        )
    }

    @Test
    fun 타입이_카테고리인_옵션의_하위_옵션을_3개_생성한다() {
        // given
        val givenMenu = Menu(
            name = "아메리카노",
            price = 4100L,
            available = true,
            category = Category(name = "음료"),
        )
        val givenOption = Option(
            name = "얼음 옵션",
            displayOrder = 1,
            type = OptionType.CATEGORY,
            menu = givenMenu,
        )

        val givenName1 = "얼음 많게"
        val givenPrice1 = 0L
        val givenDisplayOrder1 = 1

        val givenName2 = "얼음 보통"
        val givenPrice2 = 0L
        val givenDisplayOrder2 = 2

        val givenName3 = "얼음 많이"
        val givenPrice3 = 0L
        val givenDisplayOrder3 = 3

        // when
        val subOption1 = SubOption(
            name = givenName1,
            price = givenPrice1,
            displayOrder = givenDisplayOrder1,
            option = givenOption
        )
        val subOption2 = SubOption(
            name = givenName2,
            price = givenPrice2,
            displayOrder = givenDisplayOrder2,
            option = givenOption
        )
        val subOption3 = SubOption(
            name = givenName3,
            price = givenPrice3,
            displayOrder = givenDisplayOrder3,
            option = givenOption
        )
        println(subOption1)
        println(subOption2)
        println(subOption3)
        // then
        assertAll(
            { assertEquals(givenName1, subOption1.name) },
            { assertEquals(givenPrice1, subOption1.price) },
            { assertEquals(givenDisplayOrder1, subOption1.displayOrder) },
            { assertEquals(givenOption, subOption1.option) },
            { assertEquals(givenOption.type, subOption1.option.type) },

            { assertEquals(givenName2, subOption2.name) },
            { assertEquals(givenPrice2, subOption2.price) },
            { assertEquals(givenDisplayOrder2, subOption2.displayOrder) },
            { assertEquals(givenOption, subOption2.option) },
            { assertEquals(givenOption.type, subOption2.option.type) },

            { assertEquals(givenName3, subOption3.name) },
            { assertEquals(givenPrice3, subOption3.price) },
            { assertEquals(givenDisplayOrder3, subOption3.displayOrder) },
            { assertEquals(givenOption, subOption3.option) },
            { assertEquals(givenOption.type, subOption3.option.type) },
        )
    }

    @Test
    fun 타입이_카테고리인_2개의_옵션에_각각_1개_2개의_하위_옵션을_생성한다() {
        // given
        val givenMenu = Menu(
            name = "아메리카노",
            price = 4100L,
            available = true,
            category = Category(name = "음료"),
        )
        val givenOption1 = Option(
            name = "얼음 옵션",
            displayOrder = 1,
            type = OptionType.CATEGORY,
            menu = givenMenu,
        )
        val givenOption2 = Option(
            name = "사이즈 옵션",
            displayOrder = 2,
            type = OptionType.CATEGORY,
            menu = givenMenu,
        )

        val givenName1 = "얼음 보통"
        val givenPrice1 = 0L
        val givenDisplayOrder1 = 1

        val givenName2 = "얼음 많이"
        val givenPrice2 = 0L
        val givenDisplayOrder2 = 2

        val givenName3 = "사이즈업"
        val givenPrice3 = 500L
        val givenDisplayOrder3 = 1

        // when
        val subOption1 = SubOption(
            name = givenName1,
            price = givenPrice1,
            displayOrder = givenDisplayOrder1,
            option = givenOption1
        )
        val subOption2 = SubOption(
            name = givenName2,
            price = givenPrice2,
            displayOrder = givenDisplayOrder2,
            option = givenOption1
        )
        val subOption3 = SubOption(
            name = givenName3,
            price = givenPrice3,
            displayOrder = givenDisplayOrder3,
            option = givenOption2
        )
        println(subOption1)
        println(subOption2)
        println(subOption3)
        // then
        assertAll(
            { assertEquals(givenName1, subOption1.name) },
            { assertEquals(givenPrice1, subOption1.price) },
            { assertEquals(givenDisplayOrder1, subOption1.displayOrder) },
            { assertEquals(givenOption1, subOption1.option) },
            { assertEquals(givenOption1.type, subOption1.option.type) },

            { assertEquals(givenName2, subOption2.name) },
            { assertEquals(givenPrice2, subOption2.price) },
            { assertEquals(givenDisplayOrder2, subOption2.displayOrder) },
            { assertEquals(givenOption1, subOption2.option) },
            { assertEquals(givenOption1.type, subOption2.option.type) },

            { assertEquals(givenName3, subOption3.name) },
            { assertEquals(givenPrice3, subOption3.price) },
            { assertEquals(givenDisplayOrder3, subOption3.displayOrder) },
            { assertEquals(givenOption2, subOption3.option) },
            { assertEquals(givenOption2.type, subOption3.option.type) },
        )
    }

    @Test
    fun 타입이_메뉴와_카테고리_각각인_옵션에_각각_1개_2개의_하위_옵션을_생성한다() {
        // given
        val givenMenu = Menu(
            name = "아메리카노",
            price = 4100L,
            available = true,
            category = Category(name = "음료"),
        )
        val givenOption1 = Option(
            name = "얼음 옵션",
            displayOrder = 1,
            type = OptionType.CATEGORY,
            menu = givenMenu,
        )
        val givenOption2 = Option(
            name = "샷 옵션",
            displayOrder = 2,
            type = OptionType.MENU,
            menu = givenMenu,
        )

        val givenName1 = "얼음 보통"
        val givenPrice1 = 0L
        val givenDisplayOrder1 = 1

        val givenName2 = "얼음 많이"
        val givenPrice2 = 0L
        val givenDisplayOrder2 = 2

        val givenName3 = "1샷 추가"
        val givenPrice3 = 500L
        val givenDisplayOrder3 = 1

        // when
        val subOption1 = SubOption(
            name = givenName1,
            price = givenPrice1,
            displayOrder = givenDisplayOrder1,
            option = givenOption1
        )
        val subOption2 = SubOption(
            name = givenName2,
            price = givenPrice2,
            displayOrder = givenDisplayOrder2,
            option = givenOption1
        )
        val subOption3 = SubOption(
            name = givenName3,
            price = givenPrice3,
            displayOrder = givenDisplayOrder3,
            option = givenOption2
        )
        println(subOption1)
        println(subOption2)
        println(subOption3)
        // then
        assertAll(
            { assertEquals(givenName1, subOption1.name) },
            { assertEquals(givenPrice1, subOption1.price) },
            { assertEquals(givenDisplayOrder1, subOption1.displayOrder) },
            { assertEquals(givenOption1, subOption1.option) },
            { assertEquals(givenOption1.type, subOption1.option.type) },

            { assertEquals(givenName2, subOption2.name) },
            { assertEquals(givenPrice2, subOption2.price) },
            { assertEquals(givenDisplayOrder2, subOption2.displayOrder) },
            { assertEquals(givenOption1, subOption2.option) },
            { assertEquals(givenOption1.type, subOption2.option.type) },

            { assertEquals(givenName3, subOption3.name) },
            { assertEquals(givenPrice3, subOption3.price) },
            { assertEquals(givenDisplayOrder3, subOption3.displayOrder) },
            { assertEquals(givenOption2, subOption3.option) },
            { assertEquals(givenOption2.type, subOption3.option.type) },
        )
    }
}
