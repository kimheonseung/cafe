package com.devh.cafe.api.common.paging

data class Paging(
    val page: Int,
    val total: Long,
    val first: Boolean,
    val last: Boolean,
    val next: Boolean,
    val prev: Boolean,
)

const val DEFAULT_SIZE: Int = 10
