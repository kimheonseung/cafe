package com.devh.cafe.api.menu.repository

import com.devh.cafe.infrastructure.database.entity.Menu
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface MenuRepository : JpaRepository<Menu, Long>, MenuQuerydslRepository {
    fun findByName(name: String): Optional<Menu>
}
