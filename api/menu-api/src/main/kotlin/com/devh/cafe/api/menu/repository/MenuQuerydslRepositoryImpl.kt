package com.devh.cafe.api.menu.repository

import com.devh.cafe.infrastructure.database.entity.Menu
import com.devh.cafe.infrastructure.database.entity.QCategory
import com.devh.cafe.infrastructure.database.entity.QMenu
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Repository

@Repository
class MenuQuerydslRepositoryImpl(
    @Autowired
    private val jpaQueryFactory: JPAQueryFactory,
) : MenuQuerydslRepository {
    override fun findPageByCategory(categoryId: Long, pageable: Pageable): Page<Menu> {
        val qMenu = QMenu.menu
        val qCategory = QCategory.category
        val content = jpaQueryFactory
            .select(qMenu)
            .from(qMenu)
            .join(qCategory)
            .on(qMenu.category.id.eq(qCategory.id))
            .where(qCategory.id.eq(categoryId))
            .limit(pageable.pageSize.toLong())
            .offset(pageable.offset)
            .orderBy(qMenu.id.asc())
            .fetch()
        val count = jpaQueryFactory
            .select(qMenu.count())
            .from(qMenu)
            .where(qMenu.category.id.eq(categoryId))
            .fetchOne() ?: 0
        return PageImpl(content, pageable, count)
    }
}
