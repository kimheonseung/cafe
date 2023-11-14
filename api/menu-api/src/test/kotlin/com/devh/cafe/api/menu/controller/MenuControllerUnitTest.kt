package com.devh.cafe.api.menu.controller

import com.devh.cafe.api.common.paging.Paging
import com.devh.cafe.api.common.response.Message
import com.devh.cafe.api.menu.controller.request.MenuCreateRequest
import com.devh.cafe.api.menu.controller.request.MenuDeleteRequest
import com.devh.cafe.api.menu.controller.request.MenuGetRequest
import com.devh.cafe.api.menu.controller.request.MenuGetType
import com.devh.cafe.api.menu.controller.request.MenuUpdateRequest
import com.devh.cafe.api.menu.controller.response.CategorySimpleData
import com.devh.cafe.api.menu.controller.response.MenuData
import com.devh.cafe.api.menu.controller.response.MenuPageData
import com.devh.cafe.api.menu.exception.CategoryException
import com.devh.cafe.api.menu.exception.MSG_CATEGORY_NOT_EXISTS
import com.devh.cafe.api.menu.exception.MSG_MENU_ALREADY_EXISTS
import com.devh.cafe.api.menu.exception.MenuException
import com.devh.cafe.api.menu.exception.categoryDoesNotExists
import com.devh.cafe.api.menu.exception.menuAlreadyExists
import com.devh.cafe.api.menu.service.MenuServiceImpl
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class)
class MenuControllerUnitTest {
    @InjectMocks
    lateinit var menuController: MenuController

    @Mock
    lateinit var menuService: MenuServiceImpl

    @Test
    fun 메뉴_저장_요청_객체_1개가_주어질_때_저장에_성공한다() {
        // given
        val givenCategoryId = 1L
        val givenCategoryName = "카테고리"
        val givenId = 1L
        val givenName = "메뉴"
        val givenPrice = 1000L
        val givenAvailable = true
        val menuCreateRequest = MenuCreateRequest(
            categoryId = givenCategoryId,
            menuName = givenName,
            menuPrice = givenPrice
        )
        `when`(
            menuService.create(mutableListOf(menuCreateRequest))
        ).thenReturn(
            mutableListOf(
                MenuData(
                    id = givenId,
                    name = givenName,
                    price = givenPrice,
                    category = CategorySimpleData(id = givenCategoryId, name = givenCategoryName),
                    available = givenAvailable
                )
            )
        )
        // when
        val messageResponse = menuController.putMenu(mutableListOf(menuCreateRequest))
        // then
        assertAll(
            { assertEquals(200, messageResponse.status) },
            { assertEquals(Message.SUCCESS, messageResponse.message) },
        )
    }

    @Test
    fun 이미_존재하는_메뉴_1개가_포함된_메뉴_저장_요청이_주어질_때_저장에_실패한다() {
        // given
        val givenCategoryId = 1L
        val givenName = "이미 존재하는 메뉴"
        val givenPrice = 1000L
        val menuCreateRequest = MenuCreateRequest(
            categoryId = givenCategoryId,
            menuName = givenName,
            menuPrice = givenPrice
        )
        `when`(
            menuService.create(mutableListOf(menuCreateRequest))
        ).thenThrow(
            menuAlreadyExists()
        )
        // when & then
        val assertThrows = assertThrows(
            MenuException::class.java
        ) { menuController.putMenu(mutableListOf(menuCreateRequest)) }
        assertEquals(MSG_MENU_ALREADY_EXISTS, assertThrows.message)
    }

    @Test
    fun 카테고리_id가_주어질_때_관련된_메뉴_조회에_성공한다() {
        // given
        val givenCategoryId = 1L
        val givenCategoryName = "카테고리"
        val givenCategory = CategorySimpleData(id = givenCategoryId, name = givenCategoryName)
        val givenPage = 1
        val givenSize = 10
        val givenMenuPageData = MenuPageData(
            paging = Paging(
                page = givenPage,
                total = 3L,
                first = true,
                last = false,
                next = true,
                prev = false
            ),
            list = mutableListOf(
                MenuData(
                    id = 1L,
                    name = "메뉴1",
                    price = 1000L,
                    category = givenCategory,
                    available = true),
                MenuData(
                    id = 2L,
                    name = "카테고리2",
                    price = 2000L,
                    category = givenCategory,
                    available = true),
                MenuData(
                    id = 3L,
                    name = "카테고리3",
                    price = 3000L,
                    category = givenCategory,
                    available = true),
            )
        )
        `when`(
            menuService.get(
                MenuGetRequest(
                    page = givenPage,
                    size = givenSize,
                    type = MenuGetType.BY_CATEGORY_ID,
                    categoryId = givenCategoryId
                )
            )
        ).thenReturn(
            givenMenuPageData
        )
        // when
        val pageResponse = menuController.getMenu(categoryId = givenCategoryId, page = givenPage, size = givenSize)
        // then
        assertAll(
            { assertEquals(givenMenuPageData.paging.page, pageResponse.paging.page) },
            { assertEquals(givenMenuPageData.list.size, pageResponse.list.size) },
        )
    }

    @Test
    fun 존재하지_않는_카테고리_id가_주어질_때_관련된_메뉴_조회에_실패한다() {
        // given
        val givenCategoryId = 1L
        val givenPage = 1
        val givenSize = 10
        `when`(
            menuService.get(
                MenuGetRequest(
                    page = givenPage,
                    size = givenSize,
                    type = MenuGetType.BY_CATEGORY_ID,
                    categoryId = givenCategoryId
                )
            )
        ).thenThrow(
            categoryDoesNotExists()
        )
        // when & then
        val assertThrows = assertThrows(
            CategoryException::class.java
        ) { menuController.getMenu(categoryId = givenCategoryId, page = givenPage, size = givenSize) }
        assertEquals(MSG_CATEGORY_NOT_EXISTS, assertThrows.message)
    }

    @Test
    fun 카테고리_이름이_주어질_때_관련된_메뉴_조회에_성공한다() {
        // given
        val givenCategoryId = 1L
        val givenCategoryName = "카테고리"
        val givenCategory = CategorySimpleData(id = givenCategoryId, name = givenCategoryName)
        val givenPage = 1
        val givenSize = 10
        val givenMenuPageData = MenuPageData(
            paging = Paging(
                page = givenPage,
                total = 3L,
                first = true,
                last = false,
                next = true,
                prev = false
            ),
            list = mutableListOf(
                MenuData(
                    id = 1L,
                    name = "메뉴1",
                    price = 1000L,
                    category = givenCategory,
                    available = true),
                MenuData(
                    id = 2L,
                    name = "카테고리2",
                    price = 2000L,
                    category = givenCategory,
                    available = true),
                MenuData(
                    id = 3L,
                    name = "카테고리3",
                    price = 3000L,
                    category = givenCategory,
                    available = true),
            )
        )
        `when`(
            menuService.get(
                MenuGetRequest(
                    page = givenPage,
                    size = givenSize,
                    type = MenuGetType.BY_CATEGORY_NAME,
                    categoryName = givenCategoryName
                )
            )
        ).thenReturn(
            givenMenuPageData
        )
        // when
        val pageResponse = menuController.getMenu(categoryName = givenCategoryName, page = givenPage, size = givenSize)
        // then
        assertAll(
            { assertEquals(givenMenuPageData.paging.page, pageResponse.paging.page) },
            { assertEquals(givenMenuPageData.list.size, pageResponse.list.size) },
        )
    }

    @Test
    fun 존재하지_않는_카테고리_이름이_주어질_때_관련된_메뉴_조회에_실패한다() {
        // given
        val givenCategoryName = "존재하지 않는 카테고리"
        val givenPage = 1
        val givenSize = 10
        `when`(
            menuService.get(
                MenuGetRequest(
                    page = givenPage,
                    size = givenSize,
                    type = MenuGetType.BY_CATEGORY_NAME,
                    categoryName = givenCategoryName
                )
            )
        ).thenThrow(
            categoryDoesNotExists()
        )
        // when & then
        val assertThrows = assertThrows(
            CategoryException::class.java
        ) { menuController.getMenu(categoryName = givenCategoryName, page = givenPage, size = givenSize) }
        assertEquals(MSG_CATEGORY_NOT_EXISTS, assertThrows.message)
    }

    @Test
    fun 메뉴의_최신_정보가_주어질_때_변경에_성공한다() {
        // given
        val givenCategoryId = 1L
        val givenCategoryName = "카테고리"
        val givenId = 1L
        val givenName = "수정이름"
        val givenPrice = 1000L
        val givenAvailable = true
        val menuUpdateRequest = MenuUpdateRequest(
            id = givenId,
            name = givenName,
            price = givenPrice,
            available = givenAvailable,
            categoryId = givenCategoryId,
        )
        `when`(
            menuService.update(menuUpdateRequest)
        ).thenReturn(
            MenuData(
                id = givenId,
                name = givenName,
                price = givenPrice,
                category = CategorySimpleData(id = givenCategoryId, name = givenCategoryName),
                available = givenAvailable),
        )
        // when
        val messageResponse = menuController.updateMenu(menuUpdateRequest)
        // then
        assertAll(
            { assertEquals(200, messageResponse.status) },
            { assertEquals(Message.SUCCESS, messageResponse.message) },
        )
    }

    @Test
    fun 메뉴의_최신_정보가_존재하지_않는_카테고리_id를_포함할_때_변경에_실패한다() {
        // given
        val givenCategoryId = 1L
        val givenId = 1L
        val givenName = "수정이름"
        val givenPrice = 1000L
        val givenAvailable = true
        val menuUpdateRequest = MenuUpdateRequest(
            id = givenId,
            name = givenName,
            price = givenPrice,
            available = givenAvailable,
            categoryId = givenCategoryId,
        )
        `when`(
            menuService.update(menuUpdateRequest)
        ).thenThrow(
            categoryDoesNotExists()
        )
        // when & then
        val assertThrows = assertThrows(
            CategoryException::class.java
        ) { menuController.updateMenu(menuUpdateRequest) }
        assertEquals(MSG_CATEGORY_NOT_EXISTS, assertThrows.message)
    }

    @Test
    fun 메뉴의_최신_정보가_이미_존재하는_메뉴_이름을_포함할_때_변경에_실패한다() {
        // given
        val givenCategoryId = 1L
        val givenId = 1L
        val givenName = "수정이름"
        val givenPrice = 1000L
        val givenAvailable = true
        val menuUpdateRequest = MenuUpdateRequest(
            id = givenId,
            name = givenName,
            price = givenPrice,
            available = givenAvailable,
            categoryId = givenCategoryId,
        )
        `when`(
            menuService.update(menuUpdateRequest)
        ).thenThrow(
            menuAlreadyExists()
        )
        // when & then
        val assertThrows = assertThrows(
            MenuException::class.java
        ) { menuController.updateMenu(menuUpdateRequest) }
        assertEquals(MSG_MENU_ALREADY_EXISTS, assertThrows.message)
    }

    @Test
    fun 여러개의_메뉴_id가_주어질_때_삭제에_성공한다() {
        // given
        val givenIds = mutableListOf(1L, 2L, 3L)
        val givenRequest = MenuDeleteRequest(givenIds)
        `when`(menuService.delete(givenRequest)).thenAnswer {  }
        // when & then
        menuController.deleteMenu(givenIds)
    }
}
