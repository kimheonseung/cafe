package com.devh.cafe.api.common.response

data class ErrorResponse(
    val type: ErrorType,
    val description: String = "",
    var message: String = "",
) : CommonResponse() {
    init {
        message = type.message
        status = type.number
    }
}

enum class ErrorType(
    val number: Int,
    val message: String,
) {
    UNKNOWN_ERROR(800, "알 수 없는 에러입니다."),
    REQUEST_ERROR(801, "잘못된 요청입니다."),
}
