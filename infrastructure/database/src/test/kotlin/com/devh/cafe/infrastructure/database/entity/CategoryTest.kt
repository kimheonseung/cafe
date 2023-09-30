package com.devh.cafe.infrastructure.database.entity

import org.junit.jupiter.api.Assertions.assertAll
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class CategoryTest {

    @Test
    fun 깊이가_0인_카테고리_생성() {
        // given
        val givenCategoryName = "카테고리1"
        // when
        val category = Category(name = givenCategoryName)
        // then
        assertAll(
            { assertEquals(givenCategoryName, category.name) },
            { assertNull(category.parent) },
            { assertTrue(category.subCategories.isEmpty()) },
        )
    }

    @Test
    fun 깊이가_1인_카테고리_생성() {
        // given
        val givenCategoryName = "카테고리1"
        val givenSubCategoryName = "서브카테고리1"
        val givenCategory = Category(name = givenCategoryName)
        // when
        val subCategory = Category(name = givenSubCategoryName)
        givenCategory.addSubCategory(subCategory)
        // then
        assertAll(
            { assertEquals(givenCategoryName, givenCategory.name) },
            { assertEquals(subCategory, givenCategory.subCategories[0]) },
        )
    }

    @Test
    fun 깊이가_1인_하위_카테고리_제거() {
        // given
        val givenCategoryName = "카테고리1"
        val givenSubCategoryName1 = "서브카테고리1"
        val givenSubCategoryName2 = "서브카테고리2"
        val givenCategory = Category(name = givenCategoryName)
        val subCategory1 = Category(name = givenSubCategoryName1)
        val subCategory2 = Category(name = givenSubCategoryName2)
        givenCategory.addSubCategory(subCategory1)
        givenCategory.addSubCategory(subCategory2)
        // when
        givenCategory.removeSubCategory(subCategory1)
        // then
        assertAll(
            { assertEquals(givenCategoryName, givenCategory.name) },
            { assertEquals(1, givenCategory.subCategories.size) },
            { assertEquals(subCategory2, givenCategory.subCategories[0]) },
        )
    }

    @Test
    fun 카테고리명_변경() {
        // given
        val givenOldName = "이름"
        val givenNewName = "새로운 이름"
        val category = Category(name = givenOldName)
        // when
        category.changeName(givenNewName)
        // then
        assertEquals(givenNewName, category.name)
    }
}
