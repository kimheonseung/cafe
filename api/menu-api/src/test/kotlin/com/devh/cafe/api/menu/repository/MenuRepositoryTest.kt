package com.devh.cafe.api.menu.repository

import com.devh.cafe.api.menu.repository.configuration.RepositoryTest
import com.devh.cafe.infrastructure.database.entity.Category
import com.devh.cafe.infrastructure.database.entity.Menu
import org.junit.jupiter.api.Assertions.assertAll
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable

@RepositoryTest
class MenuRepositoryTest(
    @Autowired
    val categoryRepository: CategoryRepository,
    @Autowired
    val menuRepository: MenuRepository,
) {
    var defaultCategory: Category? = null

    @BeforeAll
    fun 기본_카테고리_1개에_해당하는_기본_메뉴_10개를_세팅한다() {
        val defaultCategoryName = "기본 카테고리"
        defaultCategory = categoryRepository.save(Category(name = defaultCategoryName))
        menuRepository.saveAll(
            mutableListOf(
                Menu(name = "기본 메뉴1", price = 1000L, category = defaultCategory!!),
                Menu(name = "기본 메뉴2", price = 1000L, category = defaultCategory!!),
                Menu(name = "기본 메뉴3", price = 1000L, category = defaultCategory!!),
                Menu(name = "기본 메뉴4", price = 1000L, category = defaultCategory!!),
                Menu(name = "기본 메뉴5", price = 1000L, category = defaultCategory!!),
                Menu(name = "기본 메뉴6", price = 1000L, category = defaultCategory!!),
                Menu(name = "기본 메뉴7", price = 1000L, category = defaultCategory!!),
                Menu(name = "기본 메뉴8", price = 1000L, category = defaultCategory!!),
                Menu(name = "기본 메뉴9", price = 1000L, category = defaultCategory!!),
                Menu(name = "기본 메뉴10", price = 1000L, category = defaultCategory!!),
                Menu(name = "기본 메뉴11", price = 1000L, category = defaultCategory!!),
                Menu(name = "기본 메뉴12", price = 1000L, category = defaultCategory!!),
                Menu(name = "기본 메뉴13", price = 1000L, category = defaultCategory!!),
                Menu(name = "기본 메뉴14", price = 1000L, category = defaultCategory!!),
                Menu(name = "기본 메뉴15", price = 1000L, category = defaultCategory!!),
            )
        )
    }

    @Test
    fun 메뉴_이름이_주어질_때_저장에_성공한다() {
        // given
        val givenMenuName = "메뉴1"
        val givenMenuPrice = 3000L
        // when
        val savedMenu = menuRepository.save(
            Menu(name = givenMenuName, price = givenMenuPrice, category = defaultCategory!!)
        )
        println(savedMenu)
        // then
        assertAll(
            { assertNotNull(savedMenu) },
            { assertEquals(givenMenuName, savedMenu.name) },
            { assertEquals(givenMenuPrice, savedMenu.price) },
            { assertEquals(defaultCategory, savedMenu.category) },
        )
    }

    @Test
    fun 메뉴_이름이_주어질_때_조회에_성공한다() {
        // given
        val givenMenuName = "메뉴1"
        val givenMenuPrice = 3000L
        val givenMenu = Menu(name = givenMenuName, price = givenMenuPrice, category = defaultCategory!!)
        menuRepository.save(givenMenu)
        // when
        val foundMenu = menuRepository.findByName(givenMenuName).get()
        println(foundMenu)
        // then
        assertAll(
            { assertNotNull(foundMenu) },
            { assertEquals(givenMenuName, foundMenu.name) },
            { assertEquals(givenMenuPrice, foundMenu.price) },
            { assertEquals(defaultCategory, foundMenu.category) },
        )
    }

    @Test
    fun 카테고리_id가_주어질_때_관련_메뉴의_페이지_조회에_성공한다() {
        // given
        val givenCategoryId = defaultCategory!!.id!!
        val limit = 10
        val pageNum = 1
        val pageable: Pageable = PageRequest.of(pageNum - 1, limit)
        // when
        val page: Page<Menu> =
            menuRepository.findPageByCategory(categoryId = givenCategoryId, pageable = pageable)
        page.content.forEach(System.out::println)
        // then
        assertAll(
            { assertNotNull(page.content) },
            { assertEquals(limit, page.content.size) },
        )
    }
}
