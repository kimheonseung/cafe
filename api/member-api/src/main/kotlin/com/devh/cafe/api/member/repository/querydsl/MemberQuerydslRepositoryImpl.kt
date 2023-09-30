package com.devh.cafe.api.member.repository.querydsl

import com.devh.cafe.infrastructure.database.entity.Member
import com.devh.cafe.infrastructure.database.entity.QMember
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Repository

@Repository
class MemberQuerydslRepositoryImpl(
    private val jpaQueryFactory: JPAQueryFactory,
) : MemberQuerydslRepository {
    override fun selectAllLimit(limit: Int): MutableList<Member> {
        val qMember = QMember.member
        return jpaQueryFactory
            .selectFrom(qMember)
            .orderBy(qMember.id.desc())
            .limit(limit.toLong())
            .fetch()
    }

}