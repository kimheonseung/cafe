package com.devh.cafe.api.menu.service

import com.devh.cafe.api.menu.controller.request.OptionCreateRequest
import com.devh.cafe.api.menu.controller.request.OptionGetRequest
import com.devh.cafe.api.menu.controller.response.OptionPageData

interface OptionService {
    fun createOption(optionCreateRequest: OptionCreateRequest)
    fun getByMenuId(optionGetRequest: OptionGetRequest): OptionPageData
}
