package com.devh.cafe.api.menu.controller.request

import com.devh.cafe.api.common.message.BLANK
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank

data class MenuCreateRequest(
    val categoryId: Long,
    @field:NotBlank(message = "menuName: $BLANK")
    val menuName: String,
    @field:Min(1)
    val menuPrice: Long,
)
