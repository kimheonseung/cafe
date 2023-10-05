package com.devh.cafe.api.common.advice

import com.devh.cafe.api.common.response.ErrorType
import com.devh.cafe.api.common.response.ErrorResponse
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.HttpMediaTypeNotSupportedException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class CommonControllerAdvice {
    @ExceptionHandler(HttpMediaTypeNotSupportedException::class, HttpMessageNotReadableException::class)
    fun handleRequestError(e: Exception): ErrorResponse {
        return ErrorResponse(
            type = ErrorType.REQUEST_ERROR,
            description = e.message!!,
        )
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleMethodArgumentNotValidException(e: MethodArgumentNotValidException): ErrorResponse {
        val list = e.bindingResult.fieldErrors
        return ErrorResponse(
            type = ErrorType.REQUEST_ERROR,
            description = list[list.size - 1].defaultMessage!!,
        )
    }

    @ExceptionHandler(Exception::class)
    fun handleException(e: Exception): ErrorResponse {
        return ErrorResponse(
            type = ErrorType.UNKNOWN_ERROR,
            description = e.message!!,
        )
    }
}
