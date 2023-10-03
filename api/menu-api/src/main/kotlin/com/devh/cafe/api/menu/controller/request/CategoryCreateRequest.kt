package com.devh.cafe.api.menu.controller.request

import com.devh.cafe.api.common.message.BLANK
import jakarta.validation.constraints.NotBlank

data class CategoryCreateRequest(
    val parentId: Long? = null,
    @field:NotBlank(message = "name: $BLANK")
    val name: String,
)
