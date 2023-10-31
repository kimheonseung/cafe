package com.devh.cafe.api.menu.service

import com.devh.cafe.api.menu.controller.request.MenuCreateRequest
import com.devh.cafe.api.menu.controller.request.MenuDeleteRequest
import com.devh.cafe.api.menu.controller.request.MenuGetRequest
import com.devh.cafe.api.menu.controller.request.MenuGetType
import com.devh.cafe.api.menu.controller.request.MenuUpdateRequest
import com.devh.cafe.api.menu.exception.CategoryException
import com.devh.cafe.api.menu.exception.MSG_CATEGORY_NOT_EXISTS
import com.devh.cafe.api.menu.exception.MSG_MENU_ALREADY_EXISTS
import com.devh.cafe.api.menu.exception.MenuException
import com.devh.cafe.api.menu.service.configuration.ServiceTest
import com.devh.cafe.infrastructure.database.fixture.fixtureCategoryAlcohol
import com.devh.cafe.infrastructure.database.fixture.fixtureCategoryCaffeine
import com.devh.cafe.infrastructure.database.fixture.fixtureCategoryCoffee
import com.devh.cafe.infrastructure.database.fixture.fixtureMenu
import com.devh.cafe.infrastructure.database.fixture.fixtureMenuAmericano
import com.devh.cafe.infrastructure.database.fixture.fixtureMenuLatte
import com.devh.cafe.infrastructure.database.fixture.newCategory
import com.devh.cafe.infrastructure.database.fixture.newMenu
import org.junit.jupiter.api.Assertions.assertAll
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.springframework.beans.factory.annotation.Autowired

@ServiceTest
class MenuServiceIntegrationTest(
    @Autowired
    val menuService: MenuService,
) {

    @Test
    fun 메뉴_저장_요청_객체_3개가_주어질_때_저장에_성공한다() {
        // given
        val givenCategory = fixtureCategoryCoffee
        val givenMenuPrice1 = 1500L
        val givenMenuName1 = newMenu(category = givenCategory, price = givenMenuPrice1).name
        val givenMenuPrice2 = 2500L
        val givenMenuName2 = newMenu(category = givenCategory, price = givenMenuPrice2).name
        val givenMenuPrice3 = 3500L
        val givenMenuName3 = newMenu(category = givenCategory, price = givenMenuPrice3).name
        val menuCreateRequest1 = MenuCreateRequest(categoryId = givenCategory.id!!, menuName = givenMenuName1, menuPrice = givenMenuPrice1)
        val menuCreateRequest2 = MenuCreateRequest(categoryId = givenCategory.id!!, menuName = givenMenuName2, menuPrice = givenMenuPrice2)
        val menuCreateRequest3 = MenuCreateRequest(categoryId = givenCategory.id!!, menuName = givenMenuName3, menuPrice = givenMenuPrice3)
        // when & then
        assertDoesNotThrow {
            menuService.create(
                mutableListOf(
                    menuCreateRequest1,
                    menuCreateRequest2,
                    menuCreateRequest3
                )
            )
        }
    }

    @Test
    fun 이미_존재하는_메뉴_1개가_포함된_메뉴_저장_요청_객체_3개가_주어질_때_저장에_실패한다() {
        // given
        val givenCategory = fixtureCategoryCoffee
        val givenMenuName1 = fixtureMenuAmericano.name
        val givenMenuPrice1 = fixtureMenuAmericano.price
        val givenMenuPrice2 = 2500L
        val givenMenuName2 = newMenu(category = givenCategory, price = givenMenuPrice2).name
        val givenMenuPrice3 = 3500L
        val givenMenuName3 = newMenu(category = givenCategory, price = givenMenuPrice3).name
        val menuCreateRequest1 = MenuCreateRequest(categoryId = givenCategory.id!!, menuName = givenMenuName1, menuPrice = givenMenuPrice1)
        val menuCreateRequest2 = MenuCreateRequest(categoryId = givenCategory.id!!, menuName = givenMenuName2, menuPrice = givenMenuPrice2)
        val menuCreateRequest3 = MenuCreateRequest(categoryId = givenCategory.id!!, menuName = givenMenuName3, menuPrice = givenMenuPrice3)
        // when
        val assertThrows = assertThrows(
            MenuException::class.java,
        ) {
            menuService.create(
                mutableListOf(
                    menuCreateRequest1,
                    menuCreateRequest2,
                    menuCreateRequest3
                )
            )
        }
        // then
        assertEquals(assertThrows.message, MSG_MENU_ALREADY_EXISTS)
    }

    @Test
    fun 카테고리_id가_주어질_때_관련된_메뉴_조회에_성공한다() {
        // given
        val givenCategory = fixtureCategoryCaffeine
        val givenMenu = fixtureMenu(category = givenCategory)
        val givenMenuGetRequest = MenuGetRequest(
            page = 1,
            size = 10,
            type = MenuGetType.BY_CATEGORY_ID,
            categoryId = givenCategory.id,
        )
        // when
        val menuPageData = menuService.get(givenMenuGetRequest)
        // then
        assertAll(
            { assertEquals(givenMenu.size, menuPageData.list.size) },
        )
    }

    @Test
    fun 존재하지_않는_카테고리_id가_주어질_때_관련된_메뉴_조회에_실패한다() {
        // given
        val givenUnknownCategoryId = -1L
        val givenMenuGetRequest = MenuGetRequest(
            page = 1,
            size = 10,
            type = MenuGetType.BY_CATEGORY_ID,
            categoryId = givenUnknownCategoryId,
        )
        // when & then
        val assertThrows = assertThrows(
            CategoryException::class.java,
        ) {
            menuService.get(givenMenuGetRequest)
        }
        assertEquals(MSG_CATEGORY_NOT_EXISTS, assertThrows.message)
    }

    @Test
    fun 카테고리_이름이_주어질_때_관련된_메뉴_조회에_성공한다() {
        // given
        val givenCategory = fixtureCategoryCoffee
        val givenMenu = fixtureMenu(category = fixtureCategoryCoffee)
        val givenMenuGetRequest = MenuGetRequest(
            page = 1,
            size = 10,
            type = MenuGetType.BY_CATEGORY_NAME,
            categoryName = fixtureCategoryCoffee.name,
        )
        // when
        val menuPageData = menuService.get(givenMenuGetRequest)
        // then
        assertAll(
            { assertEquals(givenMenu.size, menuPageData.list.size) },
        )
    }

    @Test
    fun 존재하지_않는_카테고리_이름이_주어질_때_관련된_메뉴_조회에_실패한다() {
        // given
        val givenUnknownCategoryName = newCategory().name
        val givenMenuGetRequest = MenuGetRequest(
            page = 1,
            size = 10,
            type = MenuGetType.BY_CATEGORY_NAME,
            categoryName = givenUnknownCategoryName,
        )
        // when & then
        val assertThrows = assertThrows(
            CategoryException::class.java,
        ) {
            menuService.get(givenMenuGetRequest)
        }
        assertEquals(MSG_CATEGORY_NOT_EXISTS, assertThrows.message)
    }

    @Test
    fun 가진_메뉴가_없는_카테고리_이름이_주어질_때_관련된_메뉴_조회에_성공한다() {
        // given
        val givenMenuGetRequest = MenuGetRequest(
            page = 1,
            size = 10,
            type = MenuGetType.BY_CATEGORY_NAME,
            categoryName = fixtureCategoryAlcohol.name,
        )
        // when
        val menuPageData = menuService.get(givenMenuGetRequest)
        // then
        assertAll(
            { assertEquals(0, menuPageData.list.size) },
        )
    }

    @Test
    fun 메뉴의_최신_정보가_주어질_때_변경에_성공한다() {
        // given
        val givenCategory = fixtureCategoryCoffee
        val givenMenu = fixtureMenuAmericano
        val givenPrice = 9999L
        val givenName = newMenu(category = givenCategory, price = givenPrice).name
        val menuUpdateRequest = MenuUpdateRequest(
            id = givenMenu.id!!,
            name = givenName,
            price = givenPrice,
            available = givenMenu.available,
            categoryId = givenCategory.id!!
        )
        // when
        val menuData = menuService.update(menuUpdateRequest)
        // then
        assertAll(
            { assertEquals(givenName, menuData.name) },
            { assertEquals(givenPrice, menuData.price) },
        )
    }

    @Test
    fun 메뉴의_최신_정보가_존재하지_않는_카테고리_id를_포함할_때_변경에_실패한다() {
        // given
        val givenCategory = fixtureCategoryCoffee
        val givenMenu = fixtureMenuAmericano
        val givenUnknownCategoryId = -1L
        val givenPrice = 9999L
        val givenName = newMenu(category = givenCategory, price = givenPrice).name
        val menuUpdateRequest = MenuUpdateRequest(
            id = givenMenu.id!!,
            name = givenName,
            price = givenPrice,
            available = givenMenu.available,
            categoryId = givenUnknownCategoryId
        )
        // when & then
        val assertThrows = assertThrows(
            CategoryException::class.java,
        ) {
            menuService.update(menuUpdateRequest)
        }
        assertEquals(MSG_CATEGORY_NOT_EXISTS, assertThrows.message)
    }

    @Test
    fun 메뉴의_최신_정보가_이미_존재하는_메뉴_이름을_포함할_때_변경에_실패한다() {
        // given
        val givenCategory = fixtureCategoryCaffeine
        val givenMenu1 = fixtureMenuAmericano
        val givenMenu2 = fixtureMenuLatte
        val givenPrice = 9999L
        val menuUpdateRequest = MenuUpdateRequest(
            id = givenMenu1.id!!,
            name = givenMenu2.name,
            price = givenPrice,
            available = givenMenu1.available,
            categoryId = givenCategory.id!!
        )
        // when & then
        val assertThrows = assertThrows(
            MenuException::class.java,
        ) {
            menuService.update(menuUpdateRequest)
        }
        assertEquals(MSG_MENU_ALREADY_EXISTS, assertThrows.message)
    }

    @Test
    fun 여러개의_메뉴_id가_주어질_때_삭제에_성공한다() {
        // given
        val menuDeleteRequest = MenuDeleteRequest(
            ids = mutableListOf(
                fixtureMenuAmericano.id!!,
                fixtureMenuLatte.id!!,
            )
        )
        // when & then
        assertDoesNotThrow { menuService.delete(menuDeleteRequest) }
    }
}
