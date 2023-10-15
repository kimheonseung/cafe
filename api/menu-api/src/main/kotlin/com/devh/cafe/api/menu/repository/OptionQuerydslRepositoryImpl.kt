package com.devh.cafe.api.menu.repository

import com.devh.cafe.infrastructure.database.entity.Option
import com.devh.cafe.infrastructure.database.entity.QOption
import com.devh.cafe.infrastructure.database.entity.QSubOption
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.beans.factory.annotation.Autowired

class OptionQuerydslRepositoryImpl(
    @Autowired
    private val jpaQueryFactory: JPAQueryFactory,
) : OptionQuerydslRepository {
    override fun findOptionsByCategoryIdInAndMenuId(categoryIds: MutableList<Long>, menuId: Long): MutableList<Option> {
        val qOption = QOption.option
        val qSubOption = QSubOption.subOption
        return jpaQueryFactory
            .select(qOption)
            .from(qOption)
            .join(qSubOption)
            .on(qOption.id.eq(qSubOption.option.id))
            .where(qOption.category.id.`in`(categoryIds)
                .or(qOption.menu.id.eq(menuId)))
            .orderBy(qOption.displayOrder.asc(), qSubOption.displayOrder.asc())
            .fetch()
    }
}