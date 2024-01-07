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
import com.devh.cafe.api.menu.repository.CategoryRepository
import com.devh.cafe.api.menu.repository.MenuRepository
import com.devh.cafe.api.menu.repository.OptionRepository
import com.devh.cafe.infrastructure.database.entity.enums.OptionType
import com.devh.cafe.infrastructure.database.fixture.fixtureCategoryCaffeineRecursiveParentCategories
import com.devh.cafe.infrastructure.database.fixture.fixtureCategoryCoffee
import com.devh.cafe.infrastructure.database.fixture.fixtureMenuAmericano
import com.devh.cafe.infrastructure.database.fixture.fixtureOptionShot
import com.devh.cafe.infrastructure.database.fixture.fixtureOptionsAmericano
import org.junit.jupiter.api.Assertions.assertAll
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension
import java.util.*

@ExtendWith(MockitoExtension::class)
class OptionServiceUnitTest {
    @Mock
    lateinit var categoryRepository: CategoryRepository

    @Mock
    lateinit var menuRepository: MenuRepository

    @Mock
    lateinit var optionRepository: OptionRepository

    @InjectMocks
    lateinit var optionService: OptionServiceImpl

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
        `when`(
                categoryRepository.findById(givenRequest.typeId)
        ).thenReturn(
                Optional.of(givenCategory)
        )
        `when`(
                optionRepository.findByName(givenRequest.name)
        ).thenReturn(
                Optional.empty()
        )
        // when & then
        assertDoesNotThrow { optionService.createOption(givenRequest) }
    }

    @Test
    fun 존재하지_않는_카테고리_타입의_새로운_옵션을_생성한다() {
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
        `when`(
                categoryRepository.findById(givenRequest.typeId)
        ).thenReturn(
                Optional.empty()
        )
        // when
        val assertThrows = assertThrows(CategoryException::class.java) { optionService.createOption(givenRequest) }
        // when & then
        assertEquals(assertThrows.message, MSG_CATEGORY_NOT_EXISTS)
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
        `when`(
                menuRepository.findById(givenRequest.typeId)
        ).thenReturn(
                Optional.of(givenMenu)
        )
        `when`(
                optionRepository.findByName(givenRequest.name)
        ).thenReturn(
                Optional.empty()
        )
        // when & then
        assertDoesNotThrow { optionService.createOption(givenRequest) }
    }

    @Test
    fun 존재하지_않는_메뉴_타입의_새로운_옵션을_생성한다() {
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
        `when`(
                menuRepository.findById(givenRequest.typeId)
        ).thenReturn(
                Optional.empty()
        )
        // when
        val assertThrows = assertThrows(MenuException::class.java) { optionService.createOption(givenRequest) }
        // when & then
        assertEquals(assertThrows.message, MSG_MENU_NOT_EXISTS)
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
        `when`(
                menuRepository.findById(givenRequest.typeId)
        ).thenReturn(
                Optional.of(givenMenu)
        )
        `when`(
                optionRepository.findByName(givenRequest.name)
        ).thenReturn(
                Optional.of(fixtureOptionShot)
        )
        // when
        val assertThrows = assertThrows(OptionException::class.java) { optionService.createOption(givenRequest) }
        // when & then
        assertEquals(assertThrows.message, MSG_OPTION_ALREADY_EXISTS)
    }

    @Test
    fun 특정_메뉴의_옵션을_조회한다() {
        // given
        val givenMenu = fixtureMenuAmericano
        val givenParentCategories = fixtureCategoryCaffeineRecursiveParentCategories
        val givenOptions = fixtureOptionsAmericano
        val givenOptionNames = givenOptions.map { it.name }
        val givenRequest = OptionGetRequest(menuId = givenMenu.id!!)
        `when`(
                menuRepository.findById(givenMenu.id!!)
        ).thenReturn(
                Optional.of(givenMenu)
        )
        `when`(
                categoryRepository.findParentCategoriesRecursiveById(givenMenu.category.id!!)
        ).thenReturn(
                givenParentCategories.toMutableSet()
        )
        `when`(
                optionRepository.findOptionsByCategoryIdInAndMenuId(
                        categoryIds = givenParentCategories.map { it.id!! }.toMutableList(),
                        menuId = givenMenu.id!!)
        ).thenReturn(
                fixtureOptionsAmericano
        )
        // when
        val expected = optionService.getByMenuId(optionGetRequest = givenRequest)
        val expectedOptionNames = expected.list.map { it.name }
        // then
        assertAll(
                { assertEquals(givenOptions.size, expected.list.size) },
                { assertTrue(givenOptionNames.containsAll(expectedOptionNames)) },
                { assertTrue(expectedOptionNames.containsAll(givenOptionNames)) },
        )
    }

    @Test
    fun 존재하지_않는_메뉴의_옵션을_조회한다() {
        // given
        val givenMenu = fixtureMenuAmericano
        val givenParentCategories = fixtureCategoryCaffeineRecursiveParentCategories
        val givenOptions = fixtureOptionsAmericano
        val givenOptionNames = givenOptions.map { it.name }
        val givenRequest = OptionGetRequest(menuId = givenMenu.id!!)
        `when`(
                menuRepository.findById(givenMenu.id!!)
        ).thenReturn(
                Optional.empty()
        )
        // when
        val assertThrows = assertThrows(MenuException::class.java) { optionService.getByMenuId(optionGetRequest = givenRequest) }
        // then
        assertEquals(assertThrows.message, MSG_MENU_NOT_EXISTS)
    }
}