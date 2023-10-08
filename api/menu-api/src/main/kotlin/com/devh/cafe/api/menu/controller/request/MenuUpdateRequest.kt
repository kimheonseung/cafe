package com.devh.cafe.api.menu.controller.request

import com.devh.cafe.api.common.message.BLANK
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

data class MenuUpdateRequest(
    @field:NotNull(message = "id: $BLANK")
    val id: Long,
    @field:NotBlank(message = "name: $BLANK")
    val name: String,
    @field:Min(1)
    val price: Long,
    @field:NotNull(message = "categoryId: $BLANK")
    val categoryId: Long,
    @field:NotNull(message = "available: $BLANK")
    val available: Boolean,
)
