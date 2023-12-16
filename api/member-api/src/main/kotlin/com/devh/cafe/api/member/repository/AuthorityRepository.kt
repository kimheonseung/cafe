package com.devh.cafe.api.member.repository

import com.devh.cafe.infrastructure.database.entity.Authority
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface AuthorityRepository : JpaRepository<Authority, Long> {
    fun findByName(name: String): Optional<Authority>
}
