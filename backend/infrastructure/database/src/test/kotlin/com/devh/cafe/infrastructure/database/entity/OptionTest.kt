package com.devh.cafe.infrastructure.database.entity

import com.devh.cafe.infrastructure.database.entity.enums.OptionType
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

class OptionTest : DescribeSpec({

    describe("메뉴가 주어질 때") {
        val givenMenu = Menu(
            name = "아메리카노",
            price = 4100L,
            available = true,
            category = Category(name = "커피"),
        )
        val givenType = OptionType.MENU
        context("해당 매뉴의 옵션을 1개 생성한다.") {
            val givenName = "샷 옵션"
            val givenDisplayOrder = 1
            val option = Option(
                name = givenName,
                displayOrder = givenDisplayOrder,
                type = OptionType.MENU,
                menu = givenMenu,
            )

            option.name shouldBe givenName
            option.displayOrder shouldBe givenDisplayOrder
            option.type shouldBe givenType
            option shouldBe givenMenu.options[0]
        }
        context("해당 매뉴의 옵션을 3개 생성한다.") {
            givenMenu.options.clear()
            val givenName1 = "샷 옵션"
            val givenDisplayOrder1 = 1
            val givenName2 = "블렌딩 옵션"
            val givenDisplayOrder2 = 2
            val givenName3 = "디카페인 옵션"
            val givenDisplayOrder3 = 3
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

            option1.name shouldBe givenName1
            option1.displayOrder shouldBe givenDisplayOrder1
            option1.type shouldBe givenType
            option1 shouldBe givenMenu.options[0]

            option2.name shouldBe givenName2
            option2.displayOrder shouldBe givenDisplayOrder2
            option2.type shouldBe givenType
            option2 shouldBe givenMenu.options[1]

            option3.name shouldBe givenName3
            option3.displayOrder shouldBe givenDisplayOrder3
            option3.type shouldBe givenType
            option3 shouldBe givenMenu.options[2]
        }
    }

    describe("카테고리가 주어질 때") {
        val givenCategory = Category(name = "음료")
        val givenType = OptionType.CATEGORY
        context("해당 카테고리의 옵션을 1개 생성한다.") {
            val givenName = "아이스 옵션"
            val givenDisplayOrder = 1
            val option = Option(
                name = givenName,
                displayOrder = givenDisplayOrder,
                type = givenType,
                category = givenCategory,
            )

            option.name shouldBe givenName
            option.displayOrder shouldBe givenDisplayOrder
            option.type shouldBe givenType
            option shouldBe givenCategory.options[0]
        }
        context("해당 카테고리의 옵션을 3개 생성한다.") {
            givenCategory.options.clear()
            val givenName1 = "아이스 옵션"
            val givenDisplayOrder1 = 1
            val givenName2 = "텀블러 옵션"
            val givenDisplayOrder2 = 2
            val givenName3 = "사이즈 옵션"
            val givenDisplayOrder3 = 3
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

            option1.name shouldBe givenName1
            option1.displayOrder shouldBe givenDisplayOrder1
            option1.type shouldBe givenType
            option1 shouldBe givenCategory.options[0]

            option2.name shouldBe givenName2
            option2.displayOrder shouldBe givenDisplayOrder2
            option2.type shouldBe givenType
            option2 shouldBe givenCategory.options[1]

            option3.name shouldBe givenName3
            option3.displayOrder shouldBe givenDisplayOrder3
            option3.type shouldBe givenType
            option3 shouldBe givenCategory.options[2]
        }
    }
})
