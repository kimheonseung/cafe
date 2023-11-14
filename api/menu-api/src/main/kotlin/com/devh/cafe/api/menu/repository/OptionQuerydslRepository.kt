package com.devh.cafe.api.menu.repository

import com.devh.cafe.infrastructure.database.entity.Option

interface OptionQuerydslRepository {
    fun findOptionsByCategoryIdInAndMenuId(categoryIds: MutableList<Long>, menuId: Long): MutableList<Option>
}
