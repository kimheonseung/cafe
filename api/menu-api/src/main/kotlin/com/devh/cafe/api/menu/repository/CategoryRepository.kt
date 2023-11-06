package com.devh.cafe.api.menu.repository

import com.devh.cafe.infrastructure.database.entity.Category
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface CategoryRepository : JpaRepository<Category, Long> {
    fun findByName(name: String): Optional<Category>
    fun findAllByNameIn(names: MutableList<String>): MutableList<Category>
    fun findAllByParent(parent: Category, pageable: Pageable): Page<Category>
    @Query(
        nativeQuery = true,
        value = """
            with recursive cte (id, name, parent) as (
            select id, name, parent from category where id = :id
            union all
            select child.id, child.name, child.parent 
            from category child 
            inner join cte parent on child.parent = parent.id
            )
            select id, name, parent from cte
        """
    )
    fun findSubCategoriesRecursiveById(@Param(value = "id") id: Long): MutableSet<Category>
    @Query(
        nativeQuery = true,
        value = """
            with recursive cte (id, name, parent) as (
            select id, name, parent from category where name = :name
            union all
            select child.id, child.name, child.parent 
            from category child 
            inner join cte parent on child.parent = parent.id
            )
            select id, name, parent from cte
        """
    )
    fun findSubCategoriesRecursiveByName(@Param(value = "name") name: String): MutableSet<Category>
}
