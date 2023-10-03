package com.devh.cafe.api.menu.controller.request

data class MenuGetRequest(
    var page: Int,
    var size: Int,
    val type: MenuGetType,
    val categoryId: Long? = null,
    val categoryName: String? = null,
)

enum class MenuGetType {
    BY_CATEGORY_ID, BY_CATEGORY_NAME
}
