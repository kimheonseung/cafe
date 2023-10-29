package com.devh.cafe.api.menu.repository

import com.devh.cafe.api.menu.repository.configuration.RepositoryTest
import com.devh.cafe.infrastructure.database.entity.Category
import org.junit.jupiter.api.Assertions.assertAll
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest

@RepositoryTest
class CategoryRepositoryTest(
    @Autowired
    val categoryRepository: CategoryRepository,
) {
    @Test
    fun 카테고리_이름이_주어질_때_저장에_성공한다() {
        // given
        val givenCategoryName = "카테고리1"
        // when
        val savedCategory = categoryRepository.save(Category(name = givenCategoryName))
        println(savedCategory)
        // then
        assertAll(
            { assertNotNull(savedCategory) },
            { assertEquals(givenCategoryName, savedCategory.name) },
        )
    }

    @Test
    fun 카테고리_이름이_주어질_때_조회에_성공한다() {
        // given
        val givenCategoryName = "카테고리1"
        val givenCategory = Category(name = givenCategoryName)
        categoryRepository.save(givenCategory)
        // when
        val foundCategory = categoryRepository.findByName(givenCategoryName).get()
        // then
        assertAll(
            { assertNotNull(foundCategory) },
            { assertEquals(givenCategoryName, foundCategory.name) },
        )
    }

    @Test
    fun 특정_카테고리_아이디_하위의_카테고리를_조회한다() {
        // given
        val mainCategoryName = "메인"
        val mainCategory = Category(name = mainCategoryName)
        val subCategoryName1 = "서브1"
        val subCategory1 = Category(name = subCategoryName1)
        val subCategoryName2 = "서브2"
        val subCategory2 = Category(name = subCategoryName2)
        val subSubCategoryName1 = "서브1-1"
        val subSubCategory1 = Category(name = subSubCategoryName1)
        val subSubCategoryName2 = "서브1-2"
        val subSubCategory2 = Category(name = subSubCategoryName2)
        val givenSubCategories = mutableSetOf(
            subCategoryName1,
            subCategoryName2,
        )
        categoryRepository.saveAll(mutableListOf(
            mainCategory,
            subCategory1,
            subSubCategory1,
            subSubCategory2,
            subCategory2
        ))

        mainCategory.addSubCategory(subCategory1)
        mainCategory.addSubCategory(subCategory2)
        subCategory1.addSubCategory(subSubCategory1)
        subCategory1.addSubCategory(subSubCategory2)

        // when
        val subCategoriesPage = categoryRepository.findAllByParent(parent = mainCategory, PageRequest.of(0, 10))
        val categoryNames = subCategoriesPage.map { it.name }.content
        println(categoryNames)
        // then
        assertAll(
            { assertTrue(givenSubCategories.size == categoryNames.size) },
            { assertTrue(givenSubCategories.containsAll(categoryNames)) },
            { assertTrue(categoryNames.containsAll(givenSubCategories)) },
        )
    }

    @Test
    fun 특정_카테고리_아이디_하위의_모든_카테고리명을_조회한다() {
        // given
        val mainCategoryName = "메인"
        val mainCategory = Category(name = mainCategoryName)
        val subCategoryName1 = "서브1"
        val subCategory1 = Category(name = subCategoryName1)
        val subCategoryName2 = "서브2"
        val subCategory2 = Category(name = subCategoryName2)
        val subSubCategoryName1 = "서브1-1"
        val subSubCategory1 = Category(name = subSubCategoryName1)
        val subSubCategoryName2 = "서브1-2"
        val subSubCategory2 = Category(name = subSubCategoryName2)
        val givenCategoryNames = mutableSetOf(
            mainCategoryName,
            subCategoryName1,
            subSubCategoryName1,
            subSubCategoryName2,
            subCategoryName2
        )
        categoryRepository.saveAll(mutableListOf(
            mainCategory,
            subCategory1,
            subSubCategory1,
            subSubCategory2,
            subCategory2
        ))

        mainCategory.addSubCategory(subCategory1)
        mainCategory.addSubCategory(subCategory2)
        subCategory1.addSubCategory(subSubCategory1)
        subCategory1.addSubCategory(subSubCategory2)

        // when
        val subCategories = categoryRepository.findSubCategoryNamesRecursiveById(id = 1)
        val categoryNames = subCategories.map { it.name }
        // then
        assertAll(
            { assertTrue(givenCategoryNames.size == categoryNames.size) },
            { assertTrue(givenCategoryNames.containsAll(categoryNames)) },
            { assertTrue(categoryNames.containsAll(givenCategoryNames)) },
        )
    }

    @Test
    fun 특정_카테고리명_하위의_모든_카테고리명을_조회한다() {
        // given
        val mainCategoryName = "메인"
        val mainCategory = Category(name = mainCategoryName)
        val subCategoryName1 = "서브1"
        val subCategory1 = Category(name = subCategoryName1)
        val subCategoryName2 = "서브2"
        val subCategory2 = Category(name = subCategoryName2)
        val subSubCategoryName1 = "서브1-1"
        val subSubCategory1 = Category(name = subSubCategoryName1)
        val subSubCategoryName2 = "서브1-2"
        val subSubCategory2 = Category(name = subSubCategoryName2)
        val givenCategoryNames = mutableSetOf(
            mainCategoryName,
            subCategoryName1,
            subSubCategoryName1,
            subSubCategoryName2,
            subCategoryName2
        )
        categoryRepository.saveAll(mutableListOf(
            mainCategory,
            subCategory1,
            subSubCategory1,
            subSubCategory2,
            subCategory2
        ))

        mainCategory.addSubCategory(subCategory1)
        mainCategory.addSubCategory(subCategory2)
        subCategory1.addSubCategory(subSubCategory1)
        subCategory1.addSubCategory(subSubCategory2)

        // when
        val subCategories = categoryRepository.findSubCategoryNamesRecursiveByName(name = mainCategoryName)
        val categoryNames = subCategories.map { it.name }
        // then
        assertAll(
            { assertTrue(givenCategoryNames.size == categoryNames.size) },
            { assertTrue(givenCategoryNames.containsAll(categoryNames)) },
            { assertTrue(categoryNames.containsAll(givenCategoryNames)) },
        )
    }
}
