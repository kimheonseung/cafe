package com.devh.cafe.api.menu.repository

import com.devh.cafe.api.menu.repository.configuration.RepositoryTest
import com.devh.cafe.infrastructure.database.entity.Menu
import com.devh.cafe.infrastructure.database.fixture.fixtureCategoryBeverage
import com.devh.cafe.infrastructure.database.fixture.fixtureCategoryCaffeine
import com.devh.cafe.infrastructure.database.fixture.fixtureCategoryCoffee
import com.devh.cafe.infrastructure.database.fixture.fixtureCategoryDecaffeine
import com.devh.cafe.infrastructure.database.fixture.fixtureCategoryFlatccino
import com.devh.cafe.infrastructure.database.fixture.fixtureMenuAmericano
import com.devh.cafe.infrastructure.database.fixture.newMenu
import org.junit.jupiter.api.Assertions.assertAll
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable

@RepositoryTest
class MenuRepositoryTest(
    @Autowired
    val menuRepository: MenuRepository,
) {

    @Test
    fun 메뉴_이름이_주어질_때_저장에_성공한다() {
        // given
        val givenMenuPrice = 1000L
        val givenCategory = fixtureCategoryBeverage
        val givenMenuName = newMenu(category = givenCategory, price = givenMenuPrice).name
        // when
        val savedMenu = menuRepository.save(
            Menu(name = givenMenuName, price = givenMenuPrice, category = givenCategory)
        )
        println(savedMenu)
        // then
        assertAll(
            { assertNotNull(savedMenu) },
            { assertEquals(givenMenuName, savedMenu.name) },
            { assertEquals(givenMenuPrice, savedMenu.price) },
            { assertEquals(givenCategory, savedMenu.category) },
        )
    }

    @Test
    fun 메뉴_이름이_주어질_때_조회에_성공한다() {
        // given
        val givenMenuName = fixtureMenuAmericano.name
        // when
        val foundMenu = menuRepository.findByName(givenMenuName).get()
        println(foundMenu)
        // then
        assertAll(
            { assertNotNull(foundMenu) },
            { assertEquals(givenMenuName, foundMenu.name) },
        )
    }

    @Test
    fun 카테고리_id가_주어질_때_관련_메뉴의_페이지_조회에_성공한다() {
        // given
        val givenCategoryId = fixtureCategoryCaffeine.id!!
        val limit = 2
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

    @Test
    fun 여러_카테고리_id가_주어질_때_관련_메뉴의_페이지_조회에_성공한다() {
        // given
        val givenCategoryIds = mutableListOf(fixtureCategoryBeverage.id!!,
                                             fixtureCategoryCoffee.id!!,
                                             fixtureCategoryFlatccino.id!!,
                                             fixtureCategoryCaffeine.id!!,
                                             fixtureCategoryDecaffeine.id!!)
        val limit = 4
        val pageNum = 1
        val pageable: Pageable = PageRequest.of(pageNum - 1, limit)
        // when
        val page: Page<Menu> =
                menuRepository.findPageByCategories(categoryIds = givenCategoryIds, pageable = pageable)
        page.content.forEach(System.out::println)
        // then
        assertAll(
                { assertNotNull(page.content) },
                { assertEquals(limit, page.content.size) },
        )
    }
}
