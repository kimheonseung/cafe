package com.devh.cafe.api.menu.controller.request

data class SubOptionCreateRequest(
    val optionId: Long? = null,
    val values: MutableList<SubOptionValue>,
)

data class SubOptionValue(
    val name: String,
    val price: Long,
    val displayOrder: Int,
)
