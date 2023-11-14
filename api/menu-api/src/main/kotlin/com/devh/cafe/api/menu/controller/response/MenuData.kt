package com.devh.cafe.api.menu.controller.response

import com.devh.cafe.api.common.paging.Paging

data class MenuData(
    val id: Long,
    val name: String,
    val price: Long,
    val category: CategorySimpleData,
    val available: Boolean,
)

data class MenuPageData(
    val paging: Paging,
    val list: MutableList<MenuData>,
)
