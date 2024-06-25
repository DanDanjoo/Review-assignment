package com.teamsparta.assignment.domain.exception

import com.teamsparta.assignment.domain.exception.dto.ErrorResponse
import jakarta.persistence.EntityNotFoundException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice


@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(NicknameValidationException.NicknameDuplicateException::class)
    fun handleNicknameDuplicateException(ex: NicknameValidationException.NicknameDuplicateException
    ): ResponseEntity<ErrorResponse> {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ErrorResponse(message = ex.message))
    }

    @ExceptionHandler(NicknameValidationException.NicknameInvalidException::class)
    fun handleNicknameInvalidException(ex: NicknameValidationException.NicknameInvalidException
    ): ResponseEntity<ErrorResponse> {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorResponse(message = ex.message))
    }

    @ExceptionHandler(PasswordValidationException.PasswordMismatchException::class)
    fun handlePasswordMismatchException(ex: PasswordValidationException.PasswordMismatchException
    ): ResponseEntity<ErrorResponse> {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorResponse(message = ex.message))
    }

    @ExceptionHandler(PasswordValidationException.PasswordLengthException::class)
    fun handlePasswordLengthException(ex: PasswordValidationException.PasswordLengthException
    ): ResponseEntity<ErrorResponse> {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorResponse(message = ex.message))
    }

    @ExceptionHandler(PasswordValidationException.PasswordContainsNicknameException::class)
    fun handlePasswordContainsNicknameException(ex: PasswordValidationException.PasswordContainsNicknameException
    ): ResponseEntity<ErrorResponse> {
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

    @ExceptionHandler(PostInvariantException.InvalidTitleException::class)
    fun handleInvalidTitleException(ex: PostInvariantException.InvalidTitleException): ResponseEntity<ErrorResponse> {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorResponse(message = ex.message))
    }

    @ExceptionHandler(PostInvariantException.InvalidContentException::class)
    fun handleInvalidContentException(ex: PostInvariantException.InvalidContentException): ResponseEntity<ErrorResponse> {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorResponse(message = ex.message))
    }

}