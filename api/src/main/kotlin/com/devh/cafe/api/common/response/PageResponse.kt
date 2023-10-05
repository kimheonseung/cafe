package com.devh.cafe.api.common.response

data class PageResponse<T>(
    val pageInformation: PageInformation,
    val list: MutableList<T>,
) : CommonResponse()

data class PageInformation(
    val page: Int,
    val total: Long,
    val first: Boolean,
    val last: Boolean,
    val next: Boolean,
    val prev: Boolean,
)
