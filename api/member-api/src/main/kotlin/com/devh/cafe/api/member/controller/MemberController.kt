package com.devh.cafe.api.member.controller

import com.devh.cafe.api.common.response.DataResponse
import com.devh.cafe.api.member.controller.request.MemberCreateRequest
import com.devh.cafe.api.member.controller.response.MemberData
import com.devh.cafe.api.member.service.MemberService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/member")
@Validated
class MemberController(
    @Autowired
    val memberService: MemberService,
) {
    @PutMapping
    fun put(
        @RequestBody memberCreateRequest: MemberCreateRequest
    ): DataResponse<MemberData> {
        val memberData = memberService.create(memberCreateRequest)
        return DataResponse(
            data = memberData
        )
    }
}
