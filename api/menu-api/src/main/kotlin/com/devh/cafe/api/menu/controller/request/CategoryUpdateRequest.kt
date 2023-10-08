package com.devh.cafe.api.menu.controller.request

import com.devh.cafe.api.common.message.BLANK
import jakarta.validation.constraints.NotBlank

data class CategoryUpdateRequest(
    val id: Long,
    @field:NotBlank(message = "name: $BLANK")
    val name: String,
)
