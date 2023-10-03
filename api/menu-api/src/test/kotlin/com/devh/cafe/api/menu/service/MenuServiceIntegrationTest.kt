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
import com.devh.cafe.api.menu.repository.CategoryRepository
import com.devh.cafe.api.menu.repository.MenuRepository
import com.devh.cafe.api.menu.service.configuration.ServiceTest
import com.devh.cafe.infrastructure.database.entity.Category
import com.devh.cafe.infrastructure.database.entity.Menu
import org.junit.jupiter.api.Assertions.assertAll
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.springframework.beans.factory.annotation.Autowired

@ServiceTest
class MenuServiceIntegrationTest(
    @Autowired
    val categoryRepository: CategoryRepository,

    @Autowired
    val menuRepository: MenuRepository,

    @Autowired
    val menuService: MenuService,
) {

    var defaultCategory: Category? = null
    var defaultEmptyCategory: Category? = null
    var defaultMenu1: Menu? = null
    var defaultMenu2: Menu? = null

    @BeforeAll
    fun 기본_카테고리_2개와_관련된_기본_메뉴_2개를_세팅한다() {
        val defaultCategoryName = "기본 카테고리"
        val defaultEmptyCategoryName = "메뉴가 없는 기본 카테고리"
        val defaultMenuName1 = "기본 메뉴1"
        val defaultMenuPrice1 = 5000L
        val defaultMenuName2 = "기본 메뉴2"
        val defaultMenuPrice2 = 4000L
        defaultCategory = categoryRepository.save(Category(name = defaultCategoryName))
        defaultMenu1 = menuRepository.save(Menu(name = defaultMenuName1, price = defaultMenuPrice1, category = defaultCategory!!))
        defaultMenu2 = menuRepository.save(Menu(name = defaultMenuName2, price = defaultMenuPrice2, category = defaultCategory!!))
        defaultEmptyCategory = categoryRepository.save(Category(name = defaultEmptyCategoryName))
    }

    @Test
    fun 메뉴_저장_요청_객체_3개가_주어질_때_저장에_성공한다() {
        // given
        val givenMenuName1 = "메뉴1"
        val givenPrice1 = 1500L
        val givenMenuName2 = "메뉴2"
        val givenPrice2 = 2500L
        val givenMenuName3 = "메뉴3"
        val givenPrice3 = 3500L
        val givenCategoryId = defaultCategory!!.id!!
        val menuCreateRequest1 = MenuCreateRequest(categoryId = givenCategoryId, menuName = givenMenuName1, menuPrice = givenPrice1)
        val menuCreateRequest2 = MenuCreateRequest(categoryId = givenCategoryId, menuName = givenMenuName2, menuPrice = givenPrice2)
        val menuCreateRequest3 = MenuCreateRequest(categoryId = givenCategoryId, menuName = givenMenuName3, menuPrice = givenPrice3)
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
        val givenMenuName1 = "기본 메뉴1"
        val givenPrice1 = 1500L
        val givenMenuName2 = "메뉴2"
        val givenPrice2 = 2500L
        val givenMenuName3 = "메뉴3"
        val givenPrice3 = 3500L
        val givenCategoryId = defaultCategory!!.id!!
        val menuCreateRequest1 = MenuCreateRequest(categoryId = givenCategoryId, menuName = givenMenuName1, menuPrice = givenPrice1)
        val menuCreateRequest2 = MenuCreateRequest(categoryId = givenCategoryId, menuName = givenMenuName2, menuPrice = givenPrice2)
        val menuCreateRequest3 = MenuCreateRequest(categoryId = givenCategoryId, menuName = givenMenuName3, menuPrice = givenPrice3)
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
        val givenMenuGetRequest = MenuGetRequest(
            page = 1,
            size = 10,
            type = MenuGetType.BY_CATEGORY_ID,
            categoryId = defaultCategory!!.id,
        )
        // when
        val menuPageData = menuService.get(givenMenuGetRequest)
        // then
        assertAll(
            { assertEquals(2, menuPageData.list.size) },
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
        val givenMenuGetRequest = MenuGetRequest(
            page = 1,
            size = 10,
            type = MenuGetType.BY_CATEGORY_NAME,
            categoryName = defaultCategory!!.name,
        )
        // when
        val menuPageData = menuService.get(givenMenuGetRequest)
        // then
        assertAll(
            { assertEquals(2, menuPageData.list.size) },
        )
    }

    @Test
    fun 존재하지_않는_카테고리_이름이_주어질_때_관련된_메뉴_조회에_실패한다() {
        // given
        val givenUnknownCategoryName = "존재하지 않는 카테고리"
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
            categoryName = defaultEmptyCategory!!.name,
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
        val givenName = "새로운 이름"
        val givenPrice = 9999L
        val menuUpdateRequest = MenuUpdateRequest(
            id = defaultMenu1!!.id!!,
            name = givenName,
            price = givenPrice,
            available = defaultMenu1!!.available,
            categoryId = defaultCategory!!.id!!
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
        val givenUnknownCategoryId = -1L
        val givenName = "새로운 이름"
        val givenPrice = 9999L
        val menuUpdateRequest = MenuUpdateRequest(
            id = defaultMenu1!!.id!!,
            name = givenName,
            price = givenPrice,
            available = defaultMenu1!!.available,
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
        val givenPrice = 9999L
        val menuUpdateRequest = MenuUpdateRequest(
            id = defaultMenu1!!.id!!,
            name = defaultMenu2!!.name,
            price = givenPrice,
            available = defaultMenu1!!.available,
            categoryId = defaultCategory!!.id!!
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
                defaultMenu1!!.id!!.toLong(),
                defaultMenu2!!.id!!.toLong(),
            )
        )
        // when & then
        assertDoesNotThrow { menuService.delete(menuDeleteRequest) }
    }
}
