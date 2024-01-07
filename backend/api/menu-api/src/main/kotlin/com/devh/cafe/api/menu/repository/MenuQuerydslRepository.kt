package com.devh.cafe.api.menu.repository

import com.devh.cafe.infrastructure.database.entity.Menu
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface MenuQuerydslRepository {
    fun findPageByCategory(categoryId: Long, pageable: Pageable): Page<Menu>
    fun findPageByCategories(categoryIds: MutableList<Long>, pageable: Pageable): Page<Menu>
}
