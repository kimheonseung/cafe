package com.devh.cafe.api.menu.controller.request

data class CategoryGetRequest(
    var parentId: Long? = null,
    var page: Int,
    var size: Int,
)
