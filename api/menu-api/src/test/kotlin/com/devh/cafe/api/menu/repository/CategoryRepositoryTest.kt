package com.devh.cafe.api.menu.repository

import com.devh.cafe.api.menu.repository.configuration.RepositoryTest
import com.devh.cafe.infrastructure.database.entity.Category
import com.devh.cafe.infrastructure.database.fixture.fixtureCategoryBeverage
import com.devh.cafe.infrastructure.database.fixture.fixtureCategoryBeverageRecursiveSubCategories
import com.devh.cafe.infrastructure.database.fixture.fixtureCategoryBeverageSubCategories
import com.devh.cafe.infrastructure.database.fixture.newCategory
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
        val givenCategoryName = newCategory().name
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
        val givenCategoryName = fixtureCategoryBeverage.name
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
        val givenMainCategoryName = fixtureCategoryBeverage.name
        val givenSubCategoryNames = fixtureCategoryBeverageSubCategories.map { it.name }
        // when
        val mainCategory = categoryRepository.findByName(givenMainCategoryName).get()
        val subCategoriesPage = categoryRepository.findAllByParent(parent = mainCategory, PageRequest.of(0, 10))
        val categoryNames = subCategoriesPage.map { it.name }.content
        println(categoryNames)
        // then
        assertAll(
            { assertTrue(givenSubCategoryNames.size == categoryNames.size) },
            { assertTrue(givenSubCategoryNames.containsAll(categoryNames)) },
            { assertTrue(categoryNames.containsAll(givenSubCategoryNames)) },
        )
    }

    @Test
    fun 특정_카테고리_아이디_하위의_모든_카테고리명을_조회한다() {
        // given
        val givenMainCategoryId = fixtureCategoryBeverage.id!!
        val givenRecursiveSubCategoryNames = fixtureCategoryBeverageRecursiveSubCategories.map { it.name }
        // when
        val subCategories = categoryRepository.findSubCategoryNamesRecursiveById(id = givenMainCategoryId)
        val categoryNames = subCategories.map { it.name }
        // then
        assertAll(
            { assertTrue(givenRecursiveSubCategoryNames.size == categoryNames.size) },
            { assertTrue(givenRecursiveSubCategoryNames.containsAll(categoryNames)) },
            { assertTrue(categoryNames.containsAll(givenRecursiveSubCategoryNames)) },
        )
    }

    @Test
    fun 특정_카테고리명_하위의_모든_카테고리명을_조회한다() {
        // given
        val givenMainCategoryName = fixtureCategoryBeverage.name
        val givenRecursiveSubCategoryNames = fixtureCategoryBeverageRecursiveSubCategories.map { it.name }
        // when
        val mainCategory = categoryRepository.findByName(givenMainCategoryName).get()
        val subCategories = categoryRepository.findSubCategoryNamesRecursiveByName(name = mainCategory.name)
        val categoryNames = subCategories.map { it.name }
        // then
        assertAll(
            { assertTrue(givenRecursiveSubCategoryNames.size == categoryNames.size) },
            { assertTrue(givenRecursiveSubCategoryNames.containsAll(categoryNames)) },
            { assertTrue(categoryNames.containsAll(givenRecursiveSubCategoryNames)) },
        )
    }
}
