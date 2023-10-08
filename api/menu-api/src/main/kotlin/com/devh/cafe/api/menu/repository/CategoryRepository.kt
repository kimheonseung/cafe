package com.devh.cafe.api.menu.repository

import com.devh.cafe.infrastructure.database.entity.Category
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface CategoryRepository : JpaRepository<Category, Long> {
    fun findByName(name: String): Optional<Category>
    fun findAllByNameIn(names: MutableList<String>): MutableList<Category>
}
