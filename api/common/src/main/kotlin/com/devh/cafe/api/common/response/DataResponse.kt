package com.devh.cafe.api.common.response

import com.devh.cafe.api.common.paging.Paging

class DataResponse<T>(
    val data: T,
) : CommonResponse()
