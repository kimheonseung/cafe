package com.devh.cafe.api.member.service

import com.devh.cafe.api.member.controller.request.MemberCreateRequest
import com.devh.cafe.api.member.controller.response.MemberData
import com.devh.cafe.api.member.repository.MemberRepository
import com.devh.cafe.infrastructure.database.entity.Member
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class MemberServiceImpl(
    @Autowired
    val memberRepository: MemberRepository,
) : MemberService {
    override fun create(memberCreateRequest: MemberCreateRequest): MemberData {
        val member = Member(
            username = memberCreateRequest.username,
            password = memberCreateRequest.password,
            name = memberCreateRequest.name,
            nickname = memberCreateRequest.nickName,
            email = memberCreateRequest.email
        )

        val savedMember = memberRepository.save(member)

        return MemberData(
            id = savedMember.id,
            username = savedMember.username,
            name = savedMember.name,
            nickName = savedMember.nickname,
            email = savedMember.email
        )
    }
}