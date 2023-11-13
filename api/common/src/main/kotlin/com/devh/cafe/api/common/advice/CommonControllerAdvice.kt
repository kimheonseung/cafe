package com.devh.cafe.api.common.advice

import com.devh.cafe.api.common.response.ErrorResponse
import com.devh.cafe.api.common.response.ErrorType
import jakarta.validation.ValidationException
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.HttpMediaTypeNotSupportedException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException

@RestControllerAdvice
class CommonControllerAdvice {
    private val log: Logger = LoggerFactory.getLogger(this.javaClass)!!

    @ExceptionHandler(HttpMediaTypeNotSupportedException::class, HttpMessageNotReadableException::class)
    fun handleRequestError(e: Exception): ErrorResponse {
        log.error("HttpMediaTypeNotSupportedException | HttpMessageNotReadableException: ", e)
        return ErrorResponse(
            type = ErrorType.REQUEST_ERROR,
            description = e.message!!,
        )
    }

    @ExceptionHandler(ValidationException::class)
    fun handleValidationException(e: ValidationException): ErrorResponse {
        log.error("ValidationException: ", e)
        return ErrorResponse(
            type = ErrorType.REQUEST_ERROR,
            description = e.message!!,
        )
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException::class)
    fun handleMethodArgumentTypeMismatchException(e: MethodArgumentTypeMismatchException): ErrorResponse {
        log.error("MethodArgumentTypeMismatchException: ", e)
        return ErrorResponse(
            type = ErrorType.REQUEST_ERROR,
            description = e.message!!,
        )
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleMethodArgumentNotValidException(e: MethodArgumentNotValidException): ErrorResponse {
        log.error("MethodArgumentNotValidException: ", e)
        val list = e.bindingResult.fieldErrors
        return ErrorResponse(
            type = ErrorType.REQUEST_ERROR,
            description = list[list.size - 1].defaultMessage!!,
        )
    }

    @ExceptionHandler(Exception::class)
    fun handleException(e: Exception): ErrorResponse {
        log.error("Exception: ", e)
        return ErrorResponse(
            type = ErrorType.UNKNOWN_ERROR,
            description = e.message!!,
        )
    }
}
