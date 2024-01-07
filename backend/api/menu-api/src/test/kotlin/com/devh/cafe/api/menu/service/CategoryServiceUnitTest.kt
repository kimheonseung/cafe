package com.devh.cafe.api.menu.service

import com.devh.cafe.api.menu.controller.request.CategoryCreateRequest
import com.devh.cafe.api.menu.controller.request.CategoryDeleteRequest
import com.devh.cafe.api.menu.controller.request.CategoryGetRequest
import com.devh.cafe.api.menu.controller.request.CategoryUpdateRequest
import com.devh.cafe.api.menu.exception.CategoryException
import com.devh.cafe.api.menu.exception.MSG_CATEGORY_ALREADY_EXISTS
import com.devh.cafe.api.menu.exception.MSG_CATEGORY_NOT_EXISTS
import com.devh.cafe.api.menu.repository.CategoryRepository
import com.devh.cafe.infrastructure.database.entity.Category
import com.devh.cafe.infrastructure.database.fixture.fixtureCategoryCaffeine
import com.devh.cafe.infrastructure.database.fixture.fixtureCategoryCaffeineRecursiveParentCategories
import org.junit.jupiter.api.Assertions.assertAll
import org.junit.jupiter.api.Assertions.assertDoesNotThrow
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.doNothing
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import java.util.Optional

@ExtendWith(MockitoExtension::class)
class CategoryServiceUnitTest {
    @Mock
    lateinit var categoryRepository: CategoryRepository

    @InjectMocks
    lateinit var categoryService: CategoryServiceImpl

    @Test
    fun 카테고리_이름이_주어질_때_저장에_성공한다() {
        // given
        val givenId = 1L
        val givenName = "카테고리1"
        val givenCategoryCreateRequest = CategoryCreateRequest(name = givenName)
        `when`(
            categoryRepository.findByName(givenName)
        ).thenReturn(
            Optional.empty()
        )

        `when`(
            categoryRepository.save(Category(name = givenName, parent = null))
        ).thenAnswer {
            val category = it.getArgument(0) as Category
            Category(id = givenId, name = category.name)
        }
        // when
        val categoryData = categoryService.create(givenCategoryCreateRequest)
        // then
        assertAll(
            { assertEquals(givenId, categoryData.id) },
            { assertEquals(givenName, categoryData.name) },
        )
    }

    @Test
    fun 카테고리_이름과_부모_카테고리_id가_주어질_때_저장에_성공한다() {
        // given
        val givenParentId = 1L
        val givenParentName = "부모 카테고리"
        val givenId = 2L
        val givenName = "카테고리"
        val givenCategoryCreateRequest = CategoryCreateRequest(name = givenName, parentId = givenParentId)
        `when`(
            categoryRepository.findByName(givenName)
        ).thenReturn(
            Optional.empty()
        )

        `when`(
            categoryRepository.findById(givenParentId)
        ).thenReturn(
            Optional.of(Category(id = givenParentId, name = givenParentName))
        )

        `when`(
            categoryRepository.save(Category(name = givenName,
                                             parent = Category(id = givenParentId, name = givenParentName)))
        ).thenAnswer {
            val category = it.getArgument(0) as Category
            Category(id = givenId, name = category.name, parent = category.parent)
        }
        // when
        val categoryData = categoryService.create(givenCategoryCreateRequest)
        // then
        assertAll(
            { assertEquals(givenId, categoryData.id) },
            { assertEquals(givenName, categoryData.name) },
            { assertEquals(givenParentId, categoryData.parent!!.id) },
            { assertEquals(givenParentName, categoryData.parent!!.name) },
        )
    }

    @Test
    fun 이미_등록된_카테고리_이름으로_저장에_실패한다() {
        // given
        val givenName = "기본 카테고리1"
        val givenCategoryCreateRequest = CategoryCreateRequest(name = givenName)
        `when`(
            categoryRepository.findByName(givenName)
        ).thenReturn(
            Optional.of(Category(name = givenName))
        )
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
        val givenId = 1L
        val givenName = "기본 카테고리1"
        `when`(
            categoryRepository.findByName(givenName)
        ).thenReturn(
            Optional.of(Category(id = givenId, name = givenName))
        )
        // when
        val foundCategory = categoryService.getByName(givenName)
        // then
        assertAll(
            { assertNotNull(foundCategory) },
            { assertEquals(givenId, foundCategory.id) },
            { assertEquals(givenName, foundCategory.name) },
        )
    }

    @Test
    fun 존재하지_않는_카테고리_이름으로_조회에_실패한다() {
        // given
        val givenName = "기본 카테고리1"
        `when`(
            categoryRepository.findByName(givenName)
        ).thenReturn(
            Optional.empty()
        )
        // when
        val assertThrows = assertThrows(
            CategoryException::class.java
        ) { categoryService.getByName(givenName) }
        // then
        assertEquals(assertThrows.message, MSG_CATEGORY_NOT_EXISTS)
    }

    @Test
    fun 카테고리_id가_주어질_때_조회에_성공한다() {
        // given
        val givenId = 1L
        val givenName = "카테고리"
        `when`(
            categoryRepository.findById(givenId)
        ).thenReturn(
            Optional.of(Category(id = givenId, name = givenName))
        )
        // when
        val foundCategory = categoryService.getById(givenId)
        // then
        assertAll(
            { assertNotNull(foundCategory) },
            { assertEquals(givenId, foundCategory.id) },
            { assertEquals(givenName, foundCategory.name) },
        )
    }

    @Test
    fun 존재하지_않는_카테고리_id로_조회에_실패한다() {
        // given
        val givenId = 1L
        `when`(
            categoryRepository.findById(givenId)
        ).thenReturn(
            Optional.empty()
        )
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
        val givenId1 = 1L
        val givenName1 = "카테고리1"
        val givenId2 = 2L
        val givenName2 = "카테고리2"
        val givenIds = mutableListOf(givenId1, givenId2)
        `when`(
            categoryRepository.findAllById(givenIds)
        ).thenReturn(
            mutableListOf(
                Category(id = givenId1, name = givenName1),
                Category(id = givenId2, name = givenName2),
            )
        )
        // when
        val foundCategories = categoryService.getByIds(givenIds)
        // then
        assertAll(
            { assertNotNull(foundCategories) },
            { assertEquals(givenIds.size, foundCategories.size) },
        )
    }

    @Test
    fun 페이지_정보가_주어질_때_페이지_조회에_성공한다() {
        // given
        val givenPage = 1
        val givenSize = 10
        val categoryGetRequest = CategoryGetRequest(page = givenPage, size = givenSize)
        val categories = mutableListOf(
            Category(id = 1, name = "카테고리1"),
            Category(id = 2, name = "카테고리2"),
            Category(id = 3, name = "카테고리3"),
        )
        `when`(
            categoryRepository.findAll(PageRequest.of(categoryGetRequest.page - 1, categoryGetRequest.size))
        ).thenReturn(
            PageImpl(categories, Pageable.ofSize(givenSize), categories.size.toLong())
        )
        // when
        val page = categoryService.get(categoryGetRequest)
        // then
        assertEquals(categories.size, page.list.size)
    }

    @Test
    fun 특정_카테고리_아이디_하위의_카테고리를_조회한다() {
        // given
        val mainCategoryName = "메인"
        val mainCategory = Category(id = 1, name = mainCategoryName)
        val subCategoryName1 = "서브1"
        val subCategory1 = Category(id = 2, name = subCategoryName1)
        val subCategoryName2 = "서브2"
        val subCategory2 = Category(id = 3, name = subCategoryName2)
        val subSubCategoryName1 = "서브1-1"
        val subSubCategory1 = Category(id = 4, name = subSubCategoryName1)
        val subSubCategoryName2 = "서브1-2"
        val subSubCategory2 = Category(id = 5, name = subSubCategoryName2)
        val expectedSubCategoryNames = mutableListOf(subCategoryName1, subCategoryName2)

        mainCategory.addSubCategory(subCategory1)
        mainCategory.addSubCategory(subCategory2)
        subCategory1.addSubCategory(subSubCategory1)
        subCategory1.addSubCategory(subSubCategory2)
        `when`(
            categoryRepository.findById(mainCategory.id!!)
        ).thenReturn(
            Optional.of(mainCategory)
        )
        `when`(
            categoryRepository.findAllByParent(mainCategory, PageRequest.of(0, 10))
        ).thenReturn(
            PageImpl(mutableListOf(subCategory1,subCategory2), Pageable.ofSize(10), 2)
        )
        // when
        val subCategoriesPageData = categoryService.getSubCategoriesByParentId(CategoryGetRequest(parentId = 1, page = 1, size = 10))
        val subCategoryNames = subCategoriesPageData.list.map { it.name }.toMutableList()
        // then
        assertAll(
            { assertEquals(expectedSubCategoryNames.size, subCategoryNames.size) },
            { assertTrue(expectedSubCategoryNames.containsAll(subCategoryNames)) },
            { assertTrue(subCategoryNames.containsAll(expectedSubCategoryNames)) },
        )
    }

    @Test
    fun 특정_카테고리명_하위의_모든_카테고리를_재귀_조회한다() {
        // given
        val mainCategoryName = "메인"
        val mainCategory = Category(id = 1, name = mainCategoryName)
        val subCategoryName1 = "서브1"
        val subCategory1 = Category(id = 2, name = subCategoryName1)
        val subCategoryName2 = "서브2"
        val subCategory2 = Category(id = 3, name = subCategoryName2)
        val subSubCategoryName1 = "서브1-1"
        val subSubCategory1 = Category(id = 4, name = subSubCategoryName1)
        val subSubCategoryName2 = "서브1-2"
        val subSubCategory2 = Category(id = 5, name = subSubCategoryName2)
        val givenCategoryNames = mutableSetOf(
            mainCategoryName,
            subCategoryName1,
            subSubCategoryName1,
            subSubCategoryName2,
            subCategoryName2
        )
        mainCategory.addSubCategory(subCategory1)
        mainCategory.addSubCategory(subCategory2)
        subCategory1.addSubCategory(subSubCategory1)
        subCategory1.addSubCategory(subSubCategory2)
        `when`(
            categoryRepository.findSubCategoriesRecursiveByName(mainCategoryName)
        ).thenReturn(
            mutableSetOf(
                mainCategory,
                subCategory1,
                subSubCategory1,
                subSubCategory2,
                subCategory2,
            )
        )
        // when
        val subCategories = categoryService.getSubCategoryNamesRecursiveByName(name = mainCategoryName)
        val subCategoryNames = subCategories.map { it.name }.toMutableList()
        // then
        assertAll(
            { assertEquals(givenCategoryNames.size, subCategoryNames.size) },
            { assertTrue(givenCategoryNames.containsAll(subCategoryNames)) },
            { assertTrue(subCategoryNames.containsAll(givenCategoryNames)) },
        )
    }

    @Test
    fun 특정_카테고리_아이디_하위의_모든_카테고리를_재귀_조회한다() {
        // given
        val mainCategoryName = "메인"
        val mainCategory = Category(id = 1, name = mainCategoryName)
        val subCategoryName1 = "서브1"
        val subCategory1 = Category(id = 2, name = subCategoryName1)
        val subCategoryName2 = "서브2"
        val subCategory2 = Category(id = 3, name = subCategoryName2)
        val subSubCategoryName1 = "서브1-1"
        val subSubCategory1 = Category(id = 4, name = subSubCategoryName1)
        val subSubCategoryName2 = "서브1-2"
        val subSubCategory2 = Category(id = 5, name = subSubCategoryName2)
        val givenCategoryNames = mutableSetOf(
            mainCategoryName,
            subCategoryName1,
            subSubCategoryName1,
            subSubCategoryName2,
            subCategoryName2
        )
        mainCategory.addSubCategory(subCategory1)
        mainCategory.addSubCategory(subCategory2)
        subCategory1.addSubCategory(subSubCategory1)
        subCategory1.addSubCategory(subSubCategory2)
        `when`(
            categoryRepository.findSubCategoriesRecursiveById(mainCategory.id!!)
        ).thenReturn(
            mutableSetOf(
                mainCategory,
                subCategory1,
                subSubCategory1,
                subSubCategory2,
                subCategory2,
            )
        )
        // when
        val subCategories = categoryService.getSubCategoryNamesRecursiveById(id = mainCategory.id!!)
        val subCategoryNames = subCategories.map { it.name }.toMutableList()
        // then
        assertAll(
            { assertEquals(givenCategoryNames.size, subCategoryNames.size) },
            { assertTrue(givenCategoryNames.containsAll(subCategoryNames)) },
            { assertTrue(subCategoryNames.containsAll(givenCategoryNames)) },
        )
    }

    @Test
    fun 특정_카테고리_아이디_상위의_모든_카테고리명을_조회한다() {
        // given
        val givenChildCategoryId = fixtureCategoryCaffeine.id!!
        `when`(
            categoryRepository.findParentCategoriesRecursiveById(givenChildCategoryId)
        ).thenReturn(
            fixtureCategoryCaffeineRecursiveParentCategories.toMutableSet()
        )
        val givenRecursiveParentCategoryNames = fixtureCategoryCaffeineRecursiveParentCategories.map { it.name }
        // when
        val parentCategories = categoryService.getParentCategoryNamesRecursiveById(id = givenChildCategoryId)
        val categoryNames = parentCategories.map { it.name }
        // then
        assertAll(
            { assertTrue(givenRecursiveParentCategoryNames.size == categoryNames.size) },
            { assertTrue(givenRecursiveParentCategoryNames.containsAll(categoryNames)) },
            { assertTrue(categoryNames.containsAll(givenRecursiveParentCategoryNames)) },
        )
    }

    @Test
    fun 특정_카테고리명_상위의_모든_카테고리명을_조회한다() {
        // given
        val givenChildCategoryName = fixtureCategoryCaffeine.name!!
        `when`(
            categoryRepository.findParentCategoriesRecursiveByName(givenChildCategoryName)
        ).thenReturn(
            fixtureCategoryCaffeineRecursiveParentCategories.toMutableSet()
        )
        val givenRecursiveParentCategoryNames = fixtureCategoryCaffeineRecursiveParentCategories.map { it.name }
        // when
        val parentCategories = categoryService.getParentCategoryNamesRecursiveByName(name = givenChildCategoryName)
        val categoryNames = parentCategories.map { it.name }
        // then
        assertAll(
            { assertTrue(givenRecursiveParentCategoryNames.size == categoryNames.size) },
            { assertTrue(givenRecursiveParentCategoryNames.containsAll(categoryNames)) },
            { assertTrue(categoryNames.containsAll(givenRecursiveParentCategoryNames)) },
        )
    }

    @Test
    fun 변경할_카테고리_이름이_주어질_때_변경에_성공한다() {
        // given
        val givenId = 1L
        val givenName = "새로운 이름"
        val categoryUpdateRequest = CategoryUpdateRequest(
            id = givenId,
            name = givenName
        )
        `when`(
            categoryRepository.findById(givenId)
        ).thenReturn(
            Optional.of(Category(id = givenId, name = "변경될 이름"))
        )
        // when
        val categoryData = categoryService.update(categoryUpdateRequest)
        // then
        assertEquals(givenName, categoryData.name)
    }

    @Test
    fun 여러개의_카테고리_id가_주어질_때_삭제에_성공한다() {
        // given
        val givenIds = mutableListOf(1L, 2L, 3L)
        val categoryDeleteRequest = CategoryDeleteRequest(ids = givenIds)
        doNothing().`when`(categoryRepository).deleteAllById(givenIds)
        // when & then
        assertDoesNotThrow { categoryService.delete(categoryDeleteRequest) }
    }
}
