package com.devh.cafe.api.common.response

import com.devh.cafe.api.common.paging.Paging

data class PageResponse<T>(
    val paging: Paging,
    val list: MutableList<T>,
) : CommonResponse()
