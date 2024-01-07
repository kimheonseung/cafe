package com.devh.cafe.infrastructure.database.entity

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.shouldBe

class CategoryTest : DescribeSpec({

    describe("카테고리명이 주어지고, 하위 카테고리가 없을 때") {
        val givenCategoryName = "카테고리1"
        context("해당 카테고리명에 대한 카테고리 객체를 생성한다.") {
            val category = Category(name = givenCategoryName)

            category.name shouldBe givenCategoryName
            category.parent shouldBe null
            category.subCategories.isEmpty() shouldBe true
        }
    }

    describe("카테고리명이 주어질고, 하위 카테고리의 깊이가 1일 때") {
        val givenCategoryName = "카테고리1"
        val givenCategory = Category(name = givenCategoryName)
        val givenSubCategoryName = "서브카테고리1"
        context("해당 카테고리명에 대한 카테고리 객체를 생성한다.") {
            val subCategory = Category(name = givenSubCategoryName)
            givenCategory.addSubCategory(subCategory)

            givenCategory.name shouldBe givenCategoryName
            givenCategory.subCategories shouldContain subCategory
        }
    }

    describe("카테고리명이 주어질고, 하위 카테고리가 2개 존재할 때") {
        val givenCategoryName = "카테고리1"
        val givenCategory = Category(name = givenCategoryName)
        val givenSubCategoryName1 = "서브카테고리1"
        val givenSubCategoryName2 = "서브카테고리2"
        val subCategory1 = Category(name = givenSubCategoryName1)
        val subCategory2 = Category(name = givenSubCategoryName2)
        givenCategory.addSubCategory(subCategory1)
        givenCategory.addSubCategory(subCategory2)
        context("하나의 하위 카테고리를 제거한다.") {
            givenCategory.removeSubCategory(subCategory1)

            givenCategory.name shouldBe givenCategoryName
            givenCategory.subCategories.size shouldBe 1
            givenCategory.subCategories shouldContain subCategory2
        }
    }

    describe("새로운 카테고리명이 주어질 때") {
        val givenOldName = "이름"
        val givenNewName = "새로운 이름"
        val givenCategory = Category(name = givenOldName)
        context("카테고리명을 변경한다.") {
            givenCategory.changeName(givenNewName)

            givenCategory.name shouldBe givenNewName
        }
    }
})
