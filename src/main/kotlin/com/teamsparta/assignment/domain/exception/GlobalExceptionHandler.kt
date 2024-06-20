package com.teamsparta.assignment.domain.exception

import com.teamsparta.assignment.domain.exception.dto.ErrorResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice


@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(NicknameDuplicateException::class)
    fun handleNicknameDuplicateException(ex: NicknameDuplicateException): ResponseEntity<ErrorResponse> {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ErrorResponse(message = ex.message))
    }

    @ExceptionHandler(NicknameInvalidException::class)
    fun handleNicknameInvalidException(ex: NicknameInvalidException): ResponseEntity<ErrorResponse> {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorResponse(message = ex.message))
    }

    @ExceptionHandler(PasswordMismatchException::class)
    fun handlePasswordMismatchException(ex: PasswordMismatchException): ResponseEntity<ErrorResponse> {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorResponse(message = ex.message))
    }

}