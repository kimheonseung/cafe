package com.devh.cafe.api.member.repository

import com.devh.cafe.infrastructure.database.entity.Member
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface MemberRepository : JpaRepository<Member, Long>
