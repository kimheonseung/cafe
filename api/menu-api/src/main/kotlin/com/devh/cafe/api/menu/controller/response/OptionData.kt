package com.devh.cafe.api.menu.controller.response

import com.devh.cafe.api.common.paging.Paging

data class OptionData(
    val id: Long,
    val name: String,
    val order: Int,
    val type: String,
    val subOptions: MutableList<SubOptionData>,
)

data class SubOptionData(
    val id: Long,
    val name: String,
    val order: Int,
    val price: Long,
)

data class OptionPageData(
    val paging: Paging,
    val list: MutableList<OptionData>,
)
