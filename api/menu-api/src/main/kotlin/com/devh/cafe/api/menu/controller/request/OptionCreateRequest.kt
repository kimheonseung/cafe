package com.devh.cafe.api.menu.controller.request

import com.devh.cafe.infrastructure.database.entity.enums.OptionType

data class OptionCreateRequest(
    val type: OptionType,
    val typeId: Long,
    val name: String,
    val displayOrder: Int,
    val subOptions: SubOptionCreateRequest
)
