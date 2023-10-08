package com.devh.cafe.api.menu.controller

import com.devh.cafe.api.common.paging.Paging
import com.devh.cafe.api.common.response.Message
import com.devh.cafe.api.menu.controller.request.CategoryCreateRequest
import com.devh.cafe.api.menu.controller.request.CategoryDeleteRequest
import com.devh.cafe.api.menu.controller.request.CategoryGetRequest
import com.devh.cafe.api.menu.controller.request.CategoryUpdateRequest
import com.devh.cafe.api.menu.controller.response.CategoryData
import com.devh.cafe.api.menu.controller.response.CategoryPageData
import com.devh.cafe.api.menu.exception.CategoryException
import com.devh.cafe.api.menu.exception.MSG_CATEGORY_ALREADY_EXISTS
import com.devh.cafe.api.menu.exception.categoryAlreadyExists
import com.devh.cafe.api.menu.service.CategoryServiceImpl
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
class CategoryControllerUnitTest {
    @InjectMocks
    lateinit var categoryController: CategoryController

    @Mock
    lateinit var categoryService: CategoryServiceImpl

    @Test
    fun 카테고리_이름이_주어질_때_저장에_성공한다() {
        // given
        val givenId = 1L
        val givenName = "카테고리"
        val categoryCreateRequest = CategoryCreateRequest(name = givenName)
        `when`(
            categoryService.create(categoryCreateRequest)
        ).thenReturn(
            CategoryData(id = givenId, name = givenName, parent = null, subCategories = mutableListOf())
        )
        // when
        val messageResponse = categoryController.putCategory(categoryCreateRequest)
        // then
        assertAll(
            { assertEquals(200, messageResponse.status) },
            { assertEquals(Message.SUCCESS, messageResponse.message) },
        )
    }

    @Test
    fun 이미_등록된_카테고리_이름으로_저장에_실패한다() {
        // given
        val givenName = "이미 등록된 카테고리"
        val categoryCreateRequest = CategoryCreateRequest(name = givenName)
        `when`(
            categoryService.create(categoryCreateRequest)
        ).thenThrow(
            categoryAlreadyExists()
        )
        // when & then
        val assertThrows = assertThrows(
            CategoryException::class.java
        ) { categoryController.putCategory(categoryCreateRequest) }
        assertEquals(MSG_CATEGORY_ALREADY_EXISTS, assertThrows.message)
    }

    @Test
    fun 페이지_정보가_주어질_때_페이지_조회에_성공한다() {
        // given
        val givenPage = 1
        val givenSize = 10
        val givenCategoryPageData = CategoryPageData(
            paging = Paging(
                page = givenPage,
                total = 3L,
                first = true,
                last = false,
                next = true,
                prev = false
            ),
            list = mutableListOf(
                CategoryData(id = 1L, name = "카테고리1", subCategories = mutableListOf()),
                CategoryData(id = 2L, name = "카테고리2", subCategories = mutableListOf()),
                CategoryData(id = 3L, name = "카테고리3", subCategories = mutableListOf()),
            )
        )
        `when`(
            categoryService.get(CategoryGetRequest(page = givenPage, size = givenSize))
        ).thenReturn(
            givenCategoryPageData
        )
        // when
        val pageResponse = categoryController.getCategory(page = givenPage, size = givenSize)
        // then
        assertAll(
            { assertEquals(givenCategoryPageData.paging.page, pageResponse.paging.page) },
            { assertEquals(givenCategoryPageData.list.size, pageResponse.list.size) },
        )
    }

    @Test
    fun 변경할_카테고리_이름이_주어질_때_변경에_성공한다() {
        // given
        val givenId = 1L
        val givenName = "수정이름"
        val categoryUpdateRequest = CategoryUpdateRequest(id = givenId, name = givenName)
        `when`(
            categoryService.update(categoryUpdateRequest)
        ).thenReturn(
            CategoryData(id = givenId, name = givenName, subCategories = mutableListOf())
        )
        // when
        val messageResponse = categoryController.updateCategory(categoryUpdateRequest)
        // then
        assertAll(
            { assertEquals(200, messageResponse.status) },
            { assertEquals(Message.SUCCESS, messageResponse.message) },
        )
    }

    @Test
    fun 한개_이상의_카테고리_id가_주어질_때_삭제에_성공한다() {
        // given
        val givenIds = mutableListOf(1L, 2L, 3L)
        val givenRequest = CategoryDeleteRequest(givenIds)
        `when`(categoryService.delete(givenRequest)).thenAnswer {  }
        // when & then
        categoryController.deleteCategory(givenIds)
    }
}
