package com.devh.cafe.api.menu.controller.advice

import com.devh.cafe.api.common.advice.CommonControllerAdvice
import com.devh.cafe.api.common.response.ErrorResponse
import com.devh.cafe.api.common.response.ErrorType
import com.devh.cafe.api.menu.exception.CategoryException
import com.devh.cafe.api.menu.exception.MenuException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ControllerAdvice : CommonControllerAdvice() {
    @ExceptionHandler(MenuException::class)
    fun handleMenuException(e: MenuException): ErrorResponse {
        return ErrorResponse(
            type = ErrorType.MENU_ERROR,
            description = e.message,
        )
    }

    @ExceptionHandler(CategoryException::class)
    fun handleCategoryException(e: CategoryException): ErrorResponse {
        return ErrorResponse(
            type = ErrorType.CATEGORY_ERROR,
            description = e.message,
        )
    }
}
