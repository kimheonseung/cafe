package com.devh.cafe.api.menu.repository

import com.devh.cafe.api.menu.repository.configuration.RepositoryTest
import com.devh.cafe.infrastructure.database.entity.Category
import org.junit.jupiter.api.Assertions.assertAll
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

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
}
