package com.devh.cafe.api.menu.service

import com.devh.cafe.api.menu.controller.request.OptionCreateRequest
import com.devh.cafe.api.menu.controller.request.OptionGetRequest
import com.devh.cafe.api.menu.controller.request.SubOptionCreateRequest
import com.devh.cafe.api.menu.controller.request.SubOptionValue
import com.devh.cafe.api.menu.exception.CategoryException
import com.devh.cafe.api.menu.exception.MSG_CATEGORY_NOT_EXISTS
import com.devh.cafe.api.menu.exception.MSG_MENU_NOT_EXISTS
import com.devh.cafe.api.menu.exception.MSG_OPTION_ALREADY_EXISTS
import com.devh.cafe.api.menu.exception.MenuException
import com.devh.cafe.api.menu.exception.OptionException
import com.devh.cafe.api.menu.service.configuration.ServiceTest
import com.devh.cafe.infrastructure.database.entity.enums.OptionType
import com.devh.cafe.infrastructure.database.fixture.fixtureCategoryCoffee
import com.devh.cafe.infrastructure.database.fixture.fixtureMenuAmericano
import com.devh.cafe.infrastructure.database.fixture.fixtureOptionShot
import com.devh.cafe.infrastructure.database.fixture.fixtureOptionsAmericano
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.springframework.beans.factory.annotation.Autowired

@ServiceTest
class OptionServiceIntegrationTest(
    @Autowired
    val optionService: OptionService,
) {
    @Test
    fun 카테고리_타입의_새로운_옵션을_생성한다() {
        // given
        val givenOptionType = OptionType.CATEGORY
        val givenCategory = fixtureCategoryCoffee
        val givenTypeId = givenCategory.id!!
        val givenName = "새로운 옵션"
        val givenDisplayOrder = 1
        val givenRequest = OptionCreateRequest(
                type = givenOptionType,
                typeId = givenTypeId,
                name = givenName,
                displayOrder = givenDisplayOrder,
                subOptions = SubOptionCreateRequest(
                        values = mutableListOf(
                                SubOptionValue(name = "하위 옵션", price = 1000, displayOrder = 1)
                        )
                )
        )
        // when & then
        assertDoesNotThrow { optionService.createOption(givenRequest) }
    }

    @Test
    fun 존재하지_않는_카테고리_타입의_새로운_옵션을_생성한다() {
        // given
        val givenOptionType = OptionType.CATEGORY
        val givenName = "새로운 옵션"
        val givenDisplayOrder = 1
        val givenRequest = OptionCreateRequest(
                type = givenOptionType,
                typeId = -1,
                name = givenName,
                displayOrder = givenDisplayOrder,
                subOptions = SubOptionCreateRequest(
                        values = mutableListOf(
                                SubOptionValue(name = "하위 옵션", price = 1000, displayOrder = 1)
                        )
                )
        )
        // when
        val assertThrows = Assertions.assertThrows(CategoryException::class.java) { optionService.createOption(givenRequest) }
        // when & then
        Assertions.assertEquals(assertThrows.message, MSG_CATEGORY_NOT_EXISTS)
    }

    @Test
    fun 메뉴_타입의_새로운_옵션을_생성한다() {
        // given
        val givenOptionType = OptionType.MENU
        val givenMenu = fixtureMenuAmericano
        val givenTypeId = givenMenu.id!!
        val givenName = "새로운 옵션"
        val givenDisplayOrder = 1
        val givenRequest = OptionCreateRequest(
                type = givenOptionType,
                typeId = givenTypeId,
                name = givenName,
                displayOrder = givenDisplayOrder,
                subOptions = SubOptionCreateRequest(
                        values = mutableListOf(
                                SubOptionValue(name = "하위 옵션", price = 1000, displayOrder = 1)
                        )
                )
        )
        // when & then
        assertDoesNotThrow { optionService.createOption(givenRequest) }
    }

    @Test
    fun 존재하지_않는_메뉴_타입의_새로운_옵션을_생성한다() {
        // given
        val givenOptionType = OptionType.MENU
        val givenName = "새로운 옵션"
        val givenDisplayOrder = 1
        val givenRequest = OptionCreateRequest(
                type = givenOptionType,
                typeId = -1,
                name = givenName,
                displayOrder = givenDisplayOrder,
                subOptions = SubOptionCreateRequest(
                        values = mutableListOf(
                                SubOptionValue(name = "하위 옵션", price = 1000, displayOrder = 1)
                        )
                )
        )
        // when
        val assertThrows = Assertions.assertThrows(MenuException::class.java) { optionService.createOption(givenRequest) }
        // when & then
        Assertions.assertEquals(assertThrows.message, MSG_MENU_NOT_EXISTS)
    }

    @Test
    fun 존재하는_옵션명으로_생성한다() {
        // given
        val givenOptionType = OptionType.MENU
        val givenMenu = fixtureMenuAmericano
        val givenTypeId = givenMenu.id!!
        val givenName = fixtureOptionShot.name
        val givenDisplayOrder = 1
        val givenRequest = OptionCreateRequest(
                type = givenOptionType,
                typeId = givenTypeId,
                name = givenName,
                displayOrder = givenDisplayOrder,
                subOptions = SubOptionCreateRequest(
                        values = mutableListOf(
                                SubOptionValue(name = "하위 옵션", price = 1000, displayOrder = 1)
                        )
                )
        )
        // when
        val assertThrows = Assertions.assertThrows(OptionException::class.java) { optionService.createOption(givenRequest) }
        // when & then
        Assertions.assertEquals(assertThrows.message, MSG_OPTION_ALREADY_EXISTS)
    }

    @Test
    fun 특정_메뉴의_옵션을_조회한다() {
        // given
        val givenMenu = fixtureMenuAmericano
        val givenRequest = OptionGetRequest(menuId = givenMenu.id!!)
        val givenOptions = fixtureOptionsAmericano
        val givenOptionNames = fixtureOptionsAmericano.map { it.name }
        // when
        val expected = optionService.getByMenuId(optionGetRequest = givenRequest)
        val expectedOptionNames = expected.list.map { it.name }
        // then
        Assertions.assertAll(
                { Assertions.assertEquals(givenOptions.size, expected.list.size) },
                { Assertions.assertTrue(givenOptionNames.containsAll(expectedOptionNames)) },
                { Assertions.assertTrue(expectedOptionNames.containsAll(givenOptionNames)) },
        )
    }

    @Test
    fun 존재하지_않는_메뉴의_옵션을_조회한다() {
        // given
        val givenRequest = OptionGetRequest(menuId = -1)
        // when
        val assertThrows = Assertions.assertThrows(MenuException::class.java) { optionService.getByMenuId(optionGetRequest = givenRequest) }
        // then
        Assertions.assertEquals(assertThrows.message, MSG_MENU_NOT_EXISTS)
    }
}
