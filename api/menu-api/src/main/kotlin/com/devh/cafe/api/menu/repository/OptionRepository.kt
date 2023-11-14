package com.devh.cafe.api.menu.repository

import com.devh.cafe.infrastructure.database.entity.Option
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface OptionRepository : JpaRepository<Option, Long>, OptionQuerydslRepository {
    fun findByName(name: String): Optional<Option>
}
