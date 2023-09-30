package com.devh.cafe.api.member.repository.querydsl

import com.devh.cafe.infrastructure.database.entity.Member

interface MemberQuerydslRepository {
    fun selectAllLimit(limit: Int): MutableList<Member>
}