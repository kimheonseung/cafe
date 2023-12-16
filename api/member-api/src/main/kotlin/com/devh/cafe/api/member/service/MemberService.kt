package com.devh.cafe.api.member.service

import com.devh.cafe.api.member.controller.request.MemberCreateRequest
import com.devh.cafe.api.member.controller.response.MemberData

interface MemberService {
    fun create(memberCreateRequest: MemberCreateRequest): MemberData
}