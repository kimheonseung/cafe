package com.devh.cafe.api.member.repository

import com.devh.cafe.infrastructure.database.entity.Authority
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface AuthorityRepository : JpaRepository<Authority, Long>