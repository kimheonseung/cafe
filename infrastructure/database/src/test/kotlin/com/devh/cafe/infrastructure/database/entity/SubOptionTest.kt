package com.devh.cafe.infrastructure.database.entity

import com.devh.cafe.infrastructure.database.entity.enums.OptionType
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

class SubOptionTest : DescribeSpec({

    describe("메뉴와 메뉴 타입의 옵션이 주어질 때") {
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
        context("하위 옵션 1개를 생성한다.") {
            val givenName = "1샷 추가"
            val givenPrice = 500L
            val givenDisplayOrder = 1
            val subOption = SubOption(
                name = givenName,
                price = givenPrice,
                displayOrder = givenDisplayOrder,
                option = givenOption
            )

            subOption.name shouldBe givenName
            subOption.price shouldBe givenPrice
            subOption.displayOrder shouldBe givenDisplayOrder
            subOption.option shouldBe givenOption
            subOption.option.type shouldBe givenOption.type
        }
        context("하위 옵션 3개를 생성한다.") {
            givenOption.subOptions.clear()
            val givenName1 = "1샷 추가"
            val givenPrice1 = 500L
            val givenDisplayOrder1 = 1
            val givenName2 = "2샷 추가"
            val givenPrice2 = 1000L
            val givenDisplayOrder2 = 2
            val givenName3 = "1/2 샷"
            val givenPrice3 = 0L
            val givenDisplayOrder3 = 3
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

            subOption1.name shouldBe givenName1
            subOption1.price shouldBe givenPrice1
            subOption1.displayOrder shouldBe givenDisplayOrder1
            subOption1.option shouldBe givenOption
            subOption1.option.type shouldBe givenOption.type
            subOption2.name shouldBe givenName2
            subOption2.price shouldBe givenPrice2
            subOption2.displayOrder shouldBe givenDisplayOrder2
            subOption2.option shouldBe givenOption
            subOption2.option.type shouldBe givenOption.type
            subOption3.name shouldBe givenName3
            subOption3.price shouldBe givenPrice3
            subOption3.displayOrder shouldBe givenDisplayOrder3
            subOption3.option shouldBe givenOption
            subOption3.option.type shouldBe givenOption.type
        }
    }

    describe("메뉴와 2개의 메뉴 타입 옵션이 주어질 때") {
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
        context("각각 1개, 2개의 하위 옵션을 생성한다.") {
            val givenName1 = "1샷 추가"
            val givenPrice1 = 500L
            val givenDisplayOrder1 = 1
            val givenName2 = "2샷 추가"
            val givenPrice2 = 1000L
            val givenDisplayOrder2 = 2
            val givenName3 = "디카페인"
            val givenPrice3 = 500L
            val givenDisplayOrder3 = 1
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

            subOption1.name shouldBe givenName1
            subOption1.price shouldBe givenPrice1
            subOption1.displayOrder shouldBe givenDisplayOrder1
            subOption1.option shouldBe givenOption1
            subOption1.option.type shouldBe givenOption1.type
            subOption2.name shouldBe givenName2
            subOption2.price shouldBe givenPrice2
            subOption2.displayOrder shouldBe givenDisplayOrder2
            subOption2.option shouldBe givenOption1
            subOption2.option.type shouldBe givenOption1.type
            subOption3.name shouldBe givenName3
            subOption3.price shouldBe givenPrice3
            subOption3.displayOrder shouldBe givenDisplayOrder3
            subOption3.option shouldBe givenOption2
            subOption3.option.type shouldBe givenOption2.type
        }
    }

    describe("메뉴와 카테고리 타입의 옵션이 주어질 때") {
        val givenMenu = Menu(
            name = "아메리카노",
            price = 4100L,
            available = true,
            category = Category(name = "커피"),
        )
        val givenOption = Option(
            name = "아이스 옵션",
            displayOrder = 1,
            type = OptionType.CATEGORY,
            menu = givenMenu,
        )
        context("하위 옵션 1개를 생성한다.") {
            val givenName = "아이스"
            val givenPrice = 500L
            val givenDisplayOrder = 1
            val subOption = SubOption(
                name = givenName,
                price = givenPrice,
                displayOrder = givenDisplayOrder,
                option = givenOption
            )

            subOption.name shouldBe givenName
            subOption.price shouldBe givenPrice
            subOption.displayOrder shouldBe givenDisplayOrder
            subOption.option shouldBe givenOption
            subOption.option.type shouldBe givenOption.type
        }
        context("하위 옵션 3개를 생성한다.") {
            givenOption.subOptions.clear()
            val givenName1 = "얼음 많게"
            val givenPrice1 = 0L
            val givenDisplayOrder1 = 1
            val givenName2 = "얼음 보통"
            val givenPrice2 = 0L
            val givenDisplayOrder2 = 2
            val givenName3 = "얼음 많이"
            val givenPrice3 = 0L
            val givenDisplayOrder3 = 3
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

            subOption1.name shouldBe givenName1
            subOption1.price shouldBe givenPrice1
            subOption1.displayOrder shouldBe givenDisplayOrder1
            subOption1.option shouldBe givenOption
            subOption1.option.type shouldBe givenOption.type
            subOption2.name shouldBe givenName2
            subOption2.price shouldBe givenPrice2
            subOption2.displayOrder shouldBe givenDisplayOrder2
            subOption2.option shouldBe givenOption
            subOption2.option.type shouldBe givenOption.type
            subOption3.name shouldBe givenName3
            subOption3.price shouldBe givenPrice3
            subOption3.displayOrder shouldBe givenDisplayOrder3
            subOption3.option shouldBe givenOption
            subOption3.option.type shouldBe givenOption.type
        }
    }

    describe("메뉴와 2개의 카테고리 타입의 옵션이 주어질 때") {
        val givenMenu = Menu(
            name = "아메리카노",
            price = 4100L,
            available = true,
            category = Category(name = "커피"),
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
        context("각각 1개, 2개의 하위 옵션을 생성한다.") {
            val givenName1 = "얼음 보통"
            val givenPrice1 = 0L
            val givenDisplayOrder1 = 1
            val givenName2 = "얼음 많이"
            val givenPrice2 = 0L
            val givenDisplayOrder2 = 2
            val givenName3 = "사이즈업"
            val givenPrice3 = 500L
            val givenDisplayOrder3 = 1
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

            subOption1.name shouldBe givenName1
            subOption1.price shouldBe givenPrice1
            subOption1.displayOrder shouldBe givenDisplayOrder1
            subOption1.option shouldBe givenOption1
            subOption1.option.type shouldBe givenOption1.type
            subOption2.name shouldBe givenName2
            subOption2.price shouldBe givenPrice2
            subOption2.displayOrder shouldBe givenDisplayOrder2
            subOption2.option shouldBe givenOption1
            subOption2.option.type shouldBe givenOption1.type
            subOption3.name shouldBe givenName3
            subOption3.price shouldBe givenPrice3
            subOption3.displayOrder shouldBe givenDisplayOrder3
            subOption3.option shouldBe givenOption2
            subOption3.option.type shouldBe givenOption2.type
        }
    }

    describe("메뉴와 카테고리 타입의 옵션 1개, 메뉴 타입의 옵션 1개가 주어질 때") {
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

        context("각각 1개, 2개의 하위 옵션을 생성한다.") {
            val givenName1 = "얼음 보통"
            val givenPrice1 = 0L
            val givenDisplayOrder1 = 1
            val givenName2 = "얼음 많이"
            val givenPrice2 = 0L
            val givenDisplayOrder2 = 2
            val givenName3 = "1샷 추가"
            val givenPrice3 = 500L
            val givenDisplayOrder3 = 1
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

            subOption1.name shouldBe givenName1
            subOption1.price shouldBe givenPrice1
            subOption1.displayOrder shouldBe givenDisplayOrder1
            subOption1.option shouldBe givenOption1
            subOption1.option.type shouldBe givenOption1.type
            subOption2.name shouldBe givenName2
            subOption2.price shouldBe givenPrice2
            subOption2.displayOrder shouldBe givenDisplayOrder2
            subOption2.option shouldBe givenOption1
            subOption2.option.type shouldBe givenOption1.type
            subOption3.name shouldBe givenName3
            subOption3.price shouldBe givenPrice3
            subOption3.displayOrder shouldBe givenDisplayOrder3
            subOption3.option shouldBe givenOption2
            subOption3.option.type shouldBe givenOption2.type
        }
    }
})
