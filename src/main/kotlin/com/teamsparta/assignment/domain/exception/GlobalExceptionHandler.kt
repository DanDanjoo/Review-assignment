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

    @ExceptionHandler(PasswordLengthException::class)
    fun handlePasswordLengthException(ex: PasswordLengthException): ResponseEntity<ErrorResponse> {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorResponse(message = ex.message))
    }

    @ExceptionHandler(PasswordContainsNicknameException::class)
    fun handlePasswordContainsNicknameException(ex: PasswordContainsNicknameException): ResponseEntity<ErrorResponse> {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorResponse(message = ex.message))
    }

    @ExceptionHandler(LoginValidationException::class)
    fun handleLoginValidationException(ex: LoginValidationException): ResponseEntity<ErrorResponse> {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ErrorResponse(message = ex.message))
    }

    @ExceptionHandler(ModelNotFoundException::class)
    fun handleModelNotFoundException(ex: ModelNotFoundException): ResponseEntity<ErrorResponse> {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorResponse(message = ex.message))
    }

    @ExceptionHandler(InvalidTitleException::class)
    fun handleInvalidTitleException(ex: InvalidTitleException): ResponseEntity<ErrorResponse> {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorResponse(message = ex.message))
    }

    @ExceptionHandler(InvalidContentException::class)
    fun handleInvalidContentException(ex: InvalidContentException): ResponseEntity<ErrorResponse> {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorResponse(message = ex.message))
    }

}