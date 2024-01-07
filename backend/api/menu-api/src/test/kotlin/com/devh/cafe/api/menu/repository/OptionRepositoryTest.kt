package com.devh.cafe.api.menu.repository

import com.devh.cafe.api.menu.repository.configuration.RepositoryTest
import com.devh.cafe.infrastructure.database.fixture.fixtureCategoryCaffeineRecursiveParentCategories
import com.devh.cafe.infrastructure.database.fixture.fixtureMenuAmericano
import com.devh.cafe.infrastructure.database.fixture.fixtureOptionsAmericano
import org.junit.jupiter.api.Assertions.assertAll
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

@RepositoryTest
class OptionRepositoryTest(
    @Autowired
    val optionRepository: OptionRepository
) {
    @Test
    fun 특정_메뉴의_옵션값을_조회한다() {
        // given
        val givenMenu = fixtureMenuAmericano
        val givenCategories = fixtureCategoryCaffeineRecursiveParentCategories
        val givenOptions = fixtureOptionsAmericano
        // when
        val expected =
            optionRepository.findOptionsByCategoryIdInAndMenuId(
                    categoryIds = givenCategories.map { it.id!! }.toMutableList(),
                    menuId = givenMenu.id!!)
        // then
        assertAll(
            { assertEquals(givenOptions.size, expected.size) },
            { assertTrue(givenOptions.containsAll(expected)) },
            { assertTrue(expected.containsAll(givenOptions)) },
        )
    }
}