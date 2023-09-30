package com.devh.cafe.api.member.repository

import com.devh.cafe.api.member.repository.querydsl.MemberQuerydslRepository
import com.devh.cafe.infrastructure.database.entity.Member
import org.springframework.data.jpa.repository.JpaRepository

interface MemberRepository : JpaRepository<Member, Long>, MemberQuerydslRepository {
    fun findByUsername(username: String): Member?
}