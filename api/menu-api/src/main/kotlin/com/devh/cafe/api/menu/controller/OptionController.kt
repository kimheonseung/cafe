package com.devh.cafe.api.menu.controller

import com.devh.cafe.api.common.response.PageResponse
import com.devh.cafe.api.menu.controller.request.OptionGetRequest
import com.devh.cafe.api.menu.controller.response.OptionData
import com.devh.cafe.api.menu.service.OptionService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/option")
@Validated
class OptionController(
    @Autowired
    val optionService: OptionService,
) {
    @GetMapping("/menu/id/{id}")
    fun getByMenuId(
            @PathVariable("id") id: Long
    ): PageResponse<OptionData> {
        val optionPageData = optionService.getByMenuId(OptionGetRequest(menuId = id))
        return PageResponse(
                paging = optionPageData.paging,
                list = optionPageData.list,
        )
    }
}
