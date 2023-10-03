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
import com.devh.cafe.api.menu.exception.categoryDoesNotExists
import com.devh.cafe.api.menu.repository.CategoryRepository
import com.devh.cafe.api.menu.repository.MenuRepository
import com.devh.cafe.infrastructure.database.entity.Category
import com.devh.cafe.infrastructure.database.entity.Menu
import org.junit.jupiter.api.Assertions.assertAll
import org.junit.jupiter.api.Assertions.assertDoesNotThrow
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.anyList
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import java.util.Optional

@ExtendWith(MockitoExtension::class)
class MenuServiceUnitTest {
    @Mock
    lateinit var categoryRepository: CategoryRepository

    @Mock
    lateinit var menuRepository: MenuRepository

    @InjectMocks
    lateinit var menuService: MenuServiceImpl

    private val givenCategory = Category(id = 1L, name = "카테고리")

    @Test
    fun 메뉴_저장_요청_객체_3개가_주어질_때_저장에_성공한다() {
        // given
        val givenMenuName1 = "메뉴1"
        val givenPrice1 = 1500L
        val givenMenuName2 = "메뉴2"
        val givenPrice2 = 2500L
        val givenMenuName3 = "메뉴3"
        val givenPrice3 = 3500L
        val menuCreateRequests = mutableListOf(
            MenuCreateRequest(categoryId = givenCategory.id!!, menuName = givenMenuName1, menuPrice = givenPrice1),
            MenuCreateRequest(categoryId = givenCategory.id!!, menuName = givenMenuName2, menuPrice = givenPrice2),
            MenuCreateRequest(categoryId = givenCategory.id!!, menuName = givenMenuName3, menuPrice = givenPrice3),
        )
        `when`(
            categoryRepository.findById(givenCategory.id!!)
        ).thenReturn(
            Optional.of(givenCategory)
        )

        `when`(
            menuRepository.findByName(givenMenuName1)
        ).thenReturn(
            Optional.empty()
        )
        `when`(
            menuRepository.findByName(givenMenuName2)
        ).thenReturn(
            Optional.empty()
        )
        `when`(
            menuRepository.findByName(givenMenuName3)
        ).thenReturn(
            Optional.empty()
        )

        `when`(
            menuRepository.saveAll(anyList())
        ).thenAnswer {
            val list = it.getArgument(0) as MutableList<Menu>
            list.mapIndexed { index, menu ->
                run {
                    Menu(
                        id = index.toLong(),
                        name = menu.name,
                        price = menu.price,
                        available = menu.available,
                        category = givenCategory
                    )
                }
            }.toMutableList()
        }
        // when
        val menuData = menuService.create(menuCreateRequests)
        // then
        assertAll(
            { assertEquals(menuCreateRequests.size, menuData.size) },
        )
    }

    @Test
    fun 이미_존재하는_메뉴_1개가_포함된_메뉴_저장_요청_객체_3개가_주어질_때_저장에_실패한다() {
        // given
        val givenMenuName1 = "메뉴1"
        val givenPrice1 = 1500L
        val givenMenuId2 = 2L
        val givenMenuName2 = "중복된 메뉴"
        val givenPrice2 = 2500L
        val givenMenuName3 = "메뉴3"
        val givenPrice3 = 3500L
        val menuCreateRequests = mutableListOf(
            MenuCreateRequest(categoryId = givenCategory.id!!, menuName = givenMenuName1, menuPrice = givenPrice1),
            MenuCreateRequest(categoryId = givenCategory.id!!, menuName = givenMenuName2, menuPrice = givenPrice2),
            MenuCreateRequest(categoryId = givenCategory.id!!, menuName = givenMenuName3, menuPrice = givenPrice3),
        )
        `when`(
            categoryRepository.findById(givenCategory.id!!)
        ).thenReturn(
            Optional.of(givenCategory)
        )

        `when`(
            menuRepository.findByName(givenMenuName1)
        ).thenReturn(
            Optional.empty()
        )
        `when`(
            menuRepository.findByName(givenMenuName2)
        ).thenReturn(
            Optional.of(Menu(id = givenMenuId2, name = givenMenuName2, price = givenPrice2, category = givenCategory))
        )
        // when
        val assertThrows = assertThrows(MenuException::class.java) { menuService.create(menuCreateRequests) }
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
            categoryId = givenCategory.id
        )
        val list = mutableListOf(
            Menu(id = 1, name = "메뉴1", price = 1500L, category = givenCategory),
            Menu(id = 2, name = "메뉴2", price = 2500L, category = givenCategory),
            Menu(id = 3, name = "메뉴3", price = 3500L, category = givenCategory),
        )
        `when`(
            categoryRepository.findById(givenCategory.id!!)
        ).thenReturn(
            Optional.of(givenCategory)
        )

        `when`(
            menuRepository.findPageByCategory(
                givenCategory.id!!,
                PageRequest.of(givenMenuGetRequest.page - 1, givenMenuGetRequest.size))
        ).thenReturn(
            PageImpl(list, Pageable.ofSize(givenMenuGetRequest.size), list.size.toLong())
        )
        // when
        val menuPageData = menuService.get(givenMenuGetRequest)
        // then
        assertAll(
            { assertEquals(givenMenuGetRequest.page, menuPageData.paging.page) },
            { assertEquals(list.size, menuPageData.list.size) },
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
        `when`(
            categoryRepository.findById(givenUnknownCategoryId)
        ).thenThrow(
            categoryDoesNotExists()
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
            categoryName = givenCategory.name
        )
        val list = mutableListOf(
            Menu(id = 1, name = "메뉴1", price = 1500L, category = givenCategory),
            Menu(id = 2, name = "메뉴2", price = 2500L, category = givenCategory),
            Menu(id = 3, name = "메뉴3", price = 3500L, category = givenCategory),
        )
        `when`(
            categoryRepository.findByName(givenCategory.name)
        ).thenReturn(
            Optional.of(givenCategory)
        )

        `when`(
            menuRepository.findPageByCategory(
                givenCategory.id!!,
                PageRequest.of(givenMenuGetRequest.page - 1, givenMenuGetRequest.size))
        ).thenReturn(
            PageImpl(list, Pageable.ofSize(givenMenuGetRequest.size), list.size.toLong())
        )
        // when
        val menuPageData = menuService.get(givenMenuGetRequest)
        // then
        assertAll(
            { assertEquals(givenMenuGetRequest.page, menuPageData.paging.page) },
            { assertEquals(list.size, menuPageData.list.size) },
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
        `when`(
            categoryRepository.findByName(givenUnknownCategoryName)
        ).thenThrow(
            categoryDoesNotExists()
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
            categoryName = givenCategory.name
        )
        val list = emptyList<Menu>()
        `when`(
            categoryRepository.findByName(givenCategory.name)
        ).thenReturn(
            Optional.of(givenCategory)
        )

        `when`(
            menuRepository.findPageByCategory(
                givenCategory.id!!,
                PageRequest.of(givenMenuGetRequest.page - 1, givenMenuGetRequest.size))
        ).thenReturn(
            PageImpl(list, Pageable.ofSize(givenMenuGetRequest.size), list.size.toLong())
        )
        // when
        val menuPageData = menuService.get(givenMenuGetRequest)
        // then
        assertAll(
            { assertEquals(givenMenuGetRequest.page, menuPageData.paging.page) },
            { assertEquals(list.size, menuPageData.list.size) },
        )
    }

    @Test
    fun 메뉴의_최신_정보가_주어질_때_변경에_성공한다() {
        // given
        val givenId = 1L
        val givenName = "새로운 이름"
        val givenPrice = 9999L
        val givenAvailable = false
        val givenNewCategory = Category(id = 777L, name = "새로운 카테고리")
        val menuUpdateRequest = MenuUpdateRequest(
            id = givenId,
            name = givenName,
            price = givenPrice,
            available = givenAvailable,
            categoryId = givenNewCategory.id!!.toLong()
        )
        `when`(
            menuRepository.findById(givenId)
        ).thenReturn(
            Optional.of(
                Menu(
                    id = givenId,
                    name = "기존 이름",
                    price = 1500L,
                    available = true,
                    category = givenCategory
                ))
        )

        `when`(
            categoryRepository.findById(givenNewCategory.id!!)
        ).thenReturn(
            Optional.of(givenNewCategory)
        )
        // when
        val menuData = menuService.update(menuUpdateRequest)
        // then
        assertAll(
            { assertEquals(givenName, menuData.name) },
            { assertEquals(givenPrice, menuData.price) },
            { assertEquals(givenAvailable, menuData.available) },
            { assertEquals(givenNewCategory.id, menuData.categoryId) },
            { assertEquals(givenNewCategory.name, menuData.categoryName) },
        )
    }

    @Test
    fun 메뉴의_최신_정보가_존재하지_않는_카테고리_id를_포함할_때_변경에_실패한다() {
        // given
        val givenUnknownCategoryId = -1L
        val givenId = 1L
        val givenName = "새로운 이름"
        val givenPrice = 9999L
        val givenAvailable = true
        val menuUpdateRequest = MenuUpdateRequest(
            id = givenId,
            name = givenName,
            price = givenPrice,
            available = givenAvailable,
            categoryId = givenUnknownCategoryId
        )
        `when`(
            menuRepository.findById(givenId)
        ).thenReturn(
            Optional.of(
                Menu(
                    id = givenId,
                    name = givenName,
                    price = givenPrice,
                    available = givenAvailable,
                    category = Category(id = 777L, "기존 카테고리")
                )
            )
        )
        `when`(
            categoryRepository.findById(givenUnknownCategoryId)
        ).thenThrow(
            categoryDoesNotExists()
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
        val givenCategoryId = 1L
        val givenCategoryName = "카테고리"
        val givenDuplicateMenuId = 2L
        val givenDuplicateName = "이미 존재하는 메뉴 이름"
        val givenId = 1L
        val givenName = "기존 메뉴 이름"
        val givenPrice = 9999L
        val givenAvailable = true
        val menuUpdateRequest = MenuUpdateRequest(
            id = givenId,
            name = givenDuplicateName,
            price = givenPrice,
            available = givenAvailable,
            categoryId = givenCategoryId
        )
        `when`(
            menuRepository.findById(givenId)
        ).thenReturn(
            Optional.of(
                Menu(
                    id = givenId,
                    name = givenName,
                    price = givenPrice,
                    available = givenAvailable,
                    category = Category(id = 777L, "기존 카테고리")
                )
            )
        )
        `when`(
            categoryRepository.findById(givenCategoryId)
        ).thenReturn(
            Optional.of(
                Category(
                    id = givenCategoryId,
                    name = givenCategoryName
                )
            )
        )
        `when`(
            menuRepository.findByName(givenDuplicateName)
        ).thenReturn(
            Optional.of(
                Menu(
                    id = givenDuplicateMenuId,
                    name = givenDuplicateName,
                    price = 5000L,
                    available = true,
                    category = Category(
                        id = givenCategoryId,
                        name = givenCategoryName
                    )
                )
            )
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
        val givenIds = mutableListOf(1L, 2L, 3L)
        val menuDeleteRequest = MenuDeleteRequest(ids = givenIds)
        Mockito.doNothing().`when`(menuRepository).deleteAllById(givenIds)
        // when & then
        assertDoesNotThrow { menuService.delete(menuDeleteRequest) }
    }
}
