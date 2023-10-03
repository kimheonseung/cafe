package com.devh.cafe.api.menu.service

import com.devh.cafe.api.menu.controller.request.CategoryCreateRequest
import com.devh.cafe.api.menu.controller.request.CategoryDeleteRequest
import com.devh.cafe.api.menu.controller.request.CategoryGetRequest
import com.devh.cafe.api.menu.controller.request.CategoryUpdateRequest
import com.devh.cafe.api.menu.exception.CategoryException
import com.devh.cafe.api.menu.exception.MSG_CATEGORY_ALREADY_EXISTS
import com.devh.cafe.api.menu.exception.MSG_CATEGORY_NOT_EXISTS
import com.devh.cafe.api.menu.repository.CategoryRepository
import com.devh.cafe.api.menu.service.configuration.ServiceTest
import com.devh.cafe.infrastructure.database.entity.Category
import org.junit.jupiter.api.Assertions.assertAll
import org.junit.jupiter.api.Assertions.assertDoesNotThrow
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

@ServiceTest
class CategoryServiceIntegrationTest(
    @Autowired
    val categoryRepository: CategoryRepository,
    @Autowired
    val categoryService: CategoryService,
) {
    var defaultCategory1: Category? = null
    var defaultCategory2: Category? = null
    var defaultCategory3: Category? = null

    @BeforeAll
    fun 기본_카테고리_3개를_세팅한다() {
        val defaultCategoryName1 = "기본 카테고리1"
        val defaultCategoryName2 = "기본 카테고리2"
        val defaultCategoryName3 = "기본 카테고리3"
        defaultCategory1 = categoryRepository.save(Category(name = defaultCategoryName1))
        defaultCategory2 = categoryRepository.save(Category(name = defaultCategoryName2))
        defaultCategory3 = categoryRepository.save(Category(name = defaultCategoryName3))
    }

    @Test
    fun 카테고리_이름이_주어질_때_저장에_성공한다() {
        // given
        val givenName = "카테고리1"
        val givenCategoryCreateRequest = CategoryCreateRequest(name = givenName)
        // when & then
        assertDoesNotThrow { categoryService.create(givenCategoryCreateRequest) }
    }

    @Test
    fun 카테고리_이름과_부모_카테고리_id가_주어질_때_저장에_성공한다() {
        // given
        val givenName = "카테고리"
        val givenCategoryCreateRequest = CategoryCreateRequest(name = givenName, parentId = defaultCategory1!!.id)
        // when
        val categoryData = categoryService.create(givenCategoryCreateRequest)
        // then
        assertAll(
            { assertEquals(givenName, categoryData.name) },
            { assertEquals(defaultCategory1!!.name, categoryData.parent!!.name) }
        )
    }

    @Test
    fun 이미_등록된_카테고리_이름으로_저장에_실패한다() {
        // given
        val givenName = "기본 카테고리1"
        val givenCategoryCreateRequest = CategoryCreateRequest(name = givenName)
        // when
        val assertThrows = assertThrows(
            CategoryException::class.java
        ) { categoryService.create(givenCategoryCreateRequest) }
        // then
        assertEquals(assertThrows.message, MSG_CATEGORY_ALREADY_EXISTS)
    }

    @Test
    fun 카테고리_이름이_주어질_때_조회에_성공한다() {
        // given
        val givenName = "기본 카테고리1"
        // when
        val foundCategory = categoryService.getByName(givenName)
        // then
        assertAll(
            { assertNotNull(foundCategory) },
            { assertEquals(givenName, foundCategory.name) },
        )
    }

    @Test
    fun 존재하지_않는_카테고리_이름으로_조회에_실패한다() {
        // given
        val givenName = "존재하지 않는 카테고리명"
        // when
        val assertThrows = assertThrows(
            CategoryException::class.java,
        ) { categoryService.getByName(givenName) }
        // then
        assertEquals(assertThrows.message, MSG_CATEGORY_NOT_EXISTS)
    }

    @Test
    fun 카테고리_id가_주어질_때_조회에_성공한다() {
        // given
        val givenId = defaultCategory1!!.id!!
        // when
        val foundCategory = categoryService.getById(givenId)
        // then
        assertAll(
            { assertNotNull(foundCategory) },
            { assertEquals(givenId, foundCategory.id) },
        )
    }

    @Test
    fun 존재하지_않는_카테고리_id로_조회에_실패한다() {
        // given
        val givenId = -1L
        // when
        val assertThrows = assertThrows(
            CategoryException::class.java
        ) { categoryService.getById(givenId) }
        // then
        assertEquals(assertThrows.message, MSG_CATEGORY_NOT_EXISTS)
    }

    @Test
    fun 여러개의_카테고리_id가_주어질_때_조회에_성공한다() {
        // given
        val givenId1 = defaultCategory1!!.id!!
        val givenId2 = defaultCategory3!!.id!!
        // when
        val foundCategories = categoryService.getByIds(mutableListOf(givenId1, givenId2))
        // then
        assertAll(
            { assertNotNull(foundCategories) },
            { assertEquals(2, foundCategories.size) },
        )
    }

    @Test
    fun 페이지_정보가_주어질_때_페이지_조회에_성공한다() {
        // given
        val givenPage = 1
        val givenSize = 10
        val categoryGetRequest = CategoryGetRequest(page = givenPage, size = givenSize)
        // when
        val page = categoryService.get(categoryGetRequest)
        // then
        assertEquals(3, page.list.size)
    }

    @Test
    fun 변경할_카테고리_이름이_주어질_때_변경에_성공한다() {
        // given
        val givenName = "새로운 이름"
        val categoryUpdateRequest = CategoryUpdateRequest(
            id = defaultCategory1!!.id!!,
            name = givenName
        )
        // when
        val categoryData = categoryService.update(categoryUpdateRequest)
        // then
        assertEquals(givenName, categoryData.name)
    }

    @Test
    fun 여러개의_카테고리_id가_주어질_때_삭제에_성공한다() {
        // given
        val categoryDeleteRequest = CategoryDeleteRequest(
            ids = mutableListOf(
                defaultCategory1!!.id!!.toLong(),
                defaultCategory2!!.id!!.toLong(),
                defaultCategory3!!.id!!.toLong(),
            )
        )
        // when & then
        assertDoesNotThrow { categoryService.delete(categoryDeleteRequest) }
    }
}
